package net.sourceforge.mpango.jms;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import net.sourceforge.mpango.email.SendMail;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Send a message to a broker for consumption by message listener.
 * 
 * @author devdlee
 * 
 */
public class ActiveMQ {

	private static ActiveMQConnectionFactory connectionFactory;
	private static Connection connection;
	private static Session session;
	private static Destination destination;
	private static boolean transacted = false;

	public static void main(String[] args) throws Exception {
		setUp("TestQueue");
		TextMessage message = session.createTextMessage("This is a test....");
		createProducerAndSendAMessage(message);
		createConsumerAndReceiveAMessage();
	}
	
	public static void setUp(String queueName) throws JMSException {
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue(queueName);
	}

	public static void createProducerAndSendAMessage(Message message) throws JMSException {
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		if (message instanceof BytesMessage) {
			
		} else if (message instanceof BytesMessage) {
			
		} else if (message instanceof TextMessage) {
			System.out.println("Sending message: " + ((TextMessage) message).getText());
		} else if (message instanceof StreamMessage) {
			
		} else if (message instanceof MapMessage) {
			
		} else if (message instanceof ObjectMessage) {
			System.out.println("Sending message: " + ((ObjectMessage) message).getObject().getClass().getName());
		}
		
		producer.send(message);
	}
	
	public static void createConsumerAndReceiveAMessage() throws JMSException {
		MessageConsumer consumer = session.createConsumer(destination);
		MessageListenerImpl messageListenerImpl = new MessageListenerImpl();
		consumer.setMessageListener(messageListenerImpl);
	}

	private static class MessageListenerImpl implements MessageListener {

		public void onMessage(Message message) {
			
			if (message instanceof BytesMessage) {
				
			} else if (message instanceof BytesMessage) {
				
			} else if (message instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("Received message: " + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			} else if (message instanceof StreamMessage) {
				
			} else if (message instanceof MapMessage) {
				
			} else if (message instanceof ObjectMessage) {
				try {
					Object object = ((ObjectMessage) message).getObject();
					
					if (object instanceof SendMail) {
						SendMail sendMail = (SendMail)object;
						System.out.println("Received message: " + sendMail.getSubject());
						sendMail.send();
					}
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Session getSession() {
		return session;
	}
}
