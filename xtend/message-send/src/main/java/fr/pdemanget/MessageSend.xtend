package fr.pdemanget

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import java.nio.file.Files
import java.nio.file.Paths
import java.util.Properties
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

/**
 * Small CLI application to send and receive from/to RabbitMQ bus.
 * 
 */
@FinalFieldsConstructor
class MessageSend {

	Channel channel
	String queue
	String exchange

	final String file

	def static void main(String[] args) {
		val params = MainUtils.parseArgs(args)

		if (params.size < 2) {
			println("
Usage:
$ msend -file <file> --recv");
			System.exit(1);
		}
		if (params.get("file") != null) {
			val String file = params.get("file");
			new MessageSend(file).run();
		}
		if (params.get("recv") != null) {
			new MessageSend("").startRecv();
		}
	}

	def run() {
		connect()

		val path = Paths.get(file)
		println("Reading file "+path.toAbsolutePath.normalize)
		send(queue, Files.readAllBytes(path))
		// recv(queue)
		close()
	}

	def send(String queue, byte[] message) {
		channel.basicPublish(exchange, queue, null, message)
		println(" [x] Sent '" + new String(message, "UTF-8") + "'")
	}

	def close() {
		channel.close()
		channel.connection.close()
	}

	def connect() {
		val properties = new Properties()
		properties.load(class.getResourceAsStream("/conf.properties"));

		val factory = new ConnectionFactory()
		factory.username = properties.getProperty("user");
		factory.password = properties.getProperty("password");
		factory.host = properties.getProperty("host")
		factory.virtualHost = properties.getProperty("virtualHost")
		exchange = properties.getProperty("exchange")
		queue = properties.getProperty("queue");

		val connection = factory.newConnection()
		channel = connection.createChannel()
		channel.queueDeclare(queue, false, false, false, null)

		connection
	}

	def startRecv() {
		connect()
		recv(queue)
//		new Thread([
//			while (true) {
//				recv(queue)
//			}
//		]).start();
	}

	def recv(String queue) {
		channel.queueBind(queue,exchange,queue);
		val consumer = new DefaultConsumer(channel) {
			override handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
				byte[] body) {
				val message = new String(body, "UTF-8")
				println(" [x] Received '" + message + "'")
			}
		};
		channel.basicConsume(queue, true, consumer);
	}

}
