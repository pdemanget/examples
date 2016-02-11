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

@FinalFieldsConstructor
class MessageSend {

	Channel channel
	String queue
	
	final String file
	

	def static void main(String[] args) {
		if(args.size<1){
			println("
Usage:
$ msend <file>");
			System.exit(1);
		}
		val String file = args.get(0);
		new MessageSend( file ).run();
	}

	def run() {
		connect()
		
		send(queue, Files.readAllBytes(Paths.get(file) )  );
		//recv(queue)
		close()
	}
	
	def send(String queue, byte[] message) {
		channel.basicPublish("", queue, null, message)
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
		queue = properties.getProperty("queue");
		
		val connection = factory.newConnection()
		channel = connection.createChannel()
		channel.queueDeclare(queue, false, false, false, null)
		
		connection
	}

	def recv(String queue) {
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
