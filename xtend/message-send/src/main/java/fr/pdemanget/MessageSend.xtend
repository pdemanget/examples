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
import fr.pdemanget.javalang.MainUtils
import java.util.Map

/**
 * Small CLI application to send and receive from/to RabbitMQ bus.
 * example
 * msend --recv -file d:\opt\workspace\LineavisionInterfaces\src\test\resources\recettage\document.json
 * 
 */
@FinalFieldsConstructor
class MessageSend {

	Channel channel
	String queue
	String exchange

	final String file
	
	static Map<String, String> params

	def static void main(String[] args) {
		params = MainUtils.mainHelper(args,"msend","$ msend [-file <file>] [--recv]","")

		if (params.get("file") != null) {
			val String file = params.get("file");
			new MessageSend(file).run();
		}
		if (params.get("recv") != null) {
			new MessageSend("").startRecv();
		}
		if (params != null && params.get("file") == null && params.get("recv") == null){
			MainUtils.showUsage("msend","$ msend [-file <file>] [--recv]","")
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
		val properties = params

		val factory = new ConnectionFactory()
		factory.username = properties.get("user");
		factory.password = properties.get("password");
		factory.host = properties.get("host")
		factory.virtualHost = properties.get("virtualHost")
		exchange = properties.get("exchange")
		queue = properties.get("queue");

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
		println('''listening on queue "«queue»"''')
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
