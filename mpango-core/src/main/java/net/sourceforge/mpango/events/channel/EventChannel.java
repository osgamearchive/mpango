package net.sourceforge.mpango.events.channel;

import net.sourceforge.mpango.events.Event;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

import java.util.*;

/**
 * Class EventChannel encapsulates JMS topic session objects and event processing thread. 
 * @author sol2202
 *
 */
public class EventChannel{
	public static final EventChannel instance = new EventChannel();
	
	private static final String JMS_TOPIC_NAME = "java:comp/env/mpangoEvents";
    private static final String MQ_URL = "tcp://localhost:61616";

    private HashMap listeners = null;
    private TopicConnectionFactory connFactory = null;
    private Topic eventTopic = null;
    private TopicConnection topicConnection = null;
    private TopicSession topicSession = null;
    private TopicSubscriber topicSubscriber = null;
    private TopicPublisher topicPublisher = null;

     
    private EventChannel(){
    	
    }
    
    /**
     * Create JMS broker connection and start event dispatching thread
     */
    public void start(){
        try{
            System.out.println("Starting JMS Event Channel..");
            connFactory = new ActiveMQConnectionFactory(MQ_URL);
            topicConnection = connFactory.createTopicConnection();
            topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            eventTopic = topicSession.createTopic(JMS_TOPIC_NAME);
            topicSubscriber = topicSession.createSubscriber(eventTopic);
            topicPublisher = topicSession.createPublisher(eventTopic);
            
            Thread eventDispatchThread = new Thread(new EventDispatcher());
            eventDispatchThread.start();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Cleanup JMS facilities
     */
    public void stop(){

        System.out.println("Stopping JMS Event Channel..");

        if( topicSubscriber != null ){
            try{
                topicSubscriber.close();

            } catch (JMSException e){
                    e.printStackTrace();
              }
        }

        if( topicPublisher != null ){
            try{
                topicPublisher.close();
            } catch (JMSException e){
                    e.printStackTrace();
            }
        }

        if( topicSession != null ){
            try{
                topicSession.close();
            } catch (JMSException e){
                e.printStackTrace();
            }
        }

        if( topicConnection != null ){
            try{
                topicConnection.close();
            } catch (JMSException e){
                e.printStackTrace();
                }
            }

    }


    /**
     * Publish specified event to the channel
     * @param event
     */
    public synchronized void publish(final Event event){
        try{
            ObjectMessage eventMessage = topicSession.createObjectMessage();
            eventMessage.setObject(event);
            topicPublisher.publish(eventMessage);

        } catch ( Exception e ){
            e.printStackTrace();
        }

    }
    
    /**
     * Event processing thread class reads messages from JMS topic and calls receive() on subscribed
     * listeners. 
     * @author michael
     *
     */
   static class EventDispatcher implements Runnable{
    	
	   private boolean running = false;
    	
	   public void run(){	
		   try{
			   //Create JMS topic connection
			   ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(MQ_URL);   	        
			   Connection connection = connectionFactory.createConnection();
			   connection.start();
    	        
			   Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			   Destination destination = session.createTopic(JMS_TOPIC_NAME);
			   MessageConsumer consumer = session.createConsumer(destination);

			   running = true;

			   while (running)
			   {
				  // Wait for a message
				   Message message = consumer.receive(1000);

				   if (message != null)
				   {
					   if (message instanceof ObjectMessage)
					   {
						   ObjectMessage objMessage = (ObjectMessage) message;
						   Event event = (Event)objMessage.getObject();
						   dispatchEvent(event);
					   }
				   }
    	        }

    	        consumer.close();
    	        session.close();
    	        connection.close();
    	        
    	      } catch (Exception e){
    	    	  e.printStackTrace();
    	      }		
    		
    	}
    	
	   public void dispatchEvent(Event event){
    		
	   }
    }

}
