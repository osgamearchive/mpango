package net.sourceforge.mpango.events.channel;

import net.sourceforge.mpango.events.Event;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

/**
 * Class EventChannel encapsulates JMS topic session objects and event processing thread. 
 * @author sol2202
 *
 */
public class EventChannel{
	
	//Make the EventChannel a singleton
	private static EventChannel instance = new EventChannel();
	
	private static final String JMS_TOPIC_NAME = "java:comp/env/mpangoEvents";
    private static final String MQ_URL = "tcp://localhost:61616";

    private TopicConnectionFactory connFactory = null;
    private Topic eventTopic = null;
    private TopicConnection topicConnection = null;
    private TopicSession topicSession = null;
    private TopicSubscriber topicSubscriber = null;
    private TopicPublisher topicPublisher = null;

    private Thread eventDispatchThread = null;
    private EventDispatcher eventDispatchTask = null;
     
    private EventChannel(){
    	
    }
    
    public static EventChannel getInstance(){
    	return instance;
    }
    
    /**
     * Create JMS broker connection and start event dispatching thread
     */
    public void start(){
        try{
            System.out.println("Starting JMS Event Channel..");
            connFactory = new ActiveMQConnectionFactory( MQ_URL );
            topicConnection = connFactory.createTopicConnection();
            topicSession = topicConnection.createTopicSession( false, Session.AUTO_ACKNOWLEDGE );
            eventTopic = topicSession.createTopic( JMS_TOPIC_NAME );
            
            topicSubscriber = topicSession.createSubscriber( eventTopic );
            topicPublisher = topicSession.createPublisher( eventTopic );
            
            eventDispatchTask = new EventDispatcher();
            eventDispatchThread = new Thread( eventDispatchTask );
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
        
        if( eventDispatchTask != null ){
        	eventDispatchTask.shutDown();
        }

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
        
        //Delete myself
        instance = null;
        
    }
    

    /**
     * Publish specified event to the channel
     * TODO: think about sync
     * @param event
     */
    public synchronized void publish( final Event event ){
        try{
            ObjectMessage eventMessage = topicSession.createObjectMessage();
            eventMessage.setObject( event );
            topicPublisher.publish( eventMessage );

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
	   private ActiveMQConnectionFactory connectionFactory = null;
	   private Connection connection = null;
	   private Session session = null;
	   private Destination destination = null;
	   private MessageConsumer consumer = null;
    	
	   public void run(){	
		   try{
			   //Create JMS topic connection
			   connectionFactory = new ActiveMQConnectionFactory( MQ_URL );   	        
			   connection = connectionFactory.createConnection();
			   connection.start();
    	        
			   session = connection.createSession( false, Session.AUTO_ACKNOWLEDGE );
			   destination = session.createTopic( JMS_TOPIC_NAME );
			   consumer = session.createConsumer( destination );

			   running = true;
			   
			   // Message listening loop
			   while ( running )
			   {
				  // Wait for a message
				   Message message = consumer.receive( 1000 );

				   if ( message != null ){
					   
					   if ( message instanceof ObjectMessage ){
						   
						   ObjectMessage objMessage = ( ObjectMessage ) message;
						   Object object = objMessage.getObject();
						   
						   if( object instanceof Event ){
							   dispatchEvent( (Event)object ); 
						   } 				   					   
					   }
				   }
    	        }
    	        
    	      } catch ( Exception e ){
    	    	  e.printStackTrace();
    	      }		
    		
    	}
	      
	   public void shutDown(){
		   
		   if( consumer != null ){
			   try{
				   //This will exit the thread
				   consumer.close(); 
			   } catch ( JMSException e ){
				   e.printStackTrace();
			   }
		   }
		   
		   if ( session != null ){
			   try{
				   session.close();
			   } catch ( JMSException e ){
				   e.printStackTrace();
			   }
		   }
	        
		   if ( session != null ){
			   try{
				   connection.close();
			   } catch ( JMSException e ){
				   e.printStackTrace();
			   }
		   }
	   }
	   
	   public void dispatchEvent( Event event ){
    		// Event processing code
	   }
    }

}
