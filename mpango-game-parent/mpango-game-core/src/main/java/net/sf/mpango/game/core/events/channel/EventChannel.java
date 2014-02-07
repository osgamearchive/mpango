package net.sf.mpango.game.core.events.channel;

import java.util.ArrayList;
import java.util.HashMap;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import net.sf.mpango.game.core.events.Event;
import net.sf.mpango.game.core.events.Observer;
import net.sf.mpango.game.core.exception.EventNotSupportedException;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Class EventChannel encapsulates ActiveMQ session objects and event processing thread. 
 * @author sol2202
 *
 */
public class EventChannel{
	
	private static EventChannel instance = new EventChannel();
	
	private static final String TOPIC_NAME = "${eventChannel.jndiTopicName}";
    private static final String MQ_URL = "${eventChannel.jmsURL}";
    private static HashMap<Class<?>, ArrayList<Observer>> listenersMap = new HashMap< Class<?>, ArrayList<Observer> >();
    
    private TopicConnectionFactory connFactory = null;
    private Topic eventTopic = null;
    private TopicConnection topicConnection = null;
    private TopicSession topicSession = null;
    private TopicSubscriber topicSubscriber = null;
    private TopicPublisher topicPublisher = null;
    private Thread eventDispatchThread = null;
    private EventDispatcher eventDispatchTask = null;
   
    
    private EventChannel(){}
    
    public static EventChannel getInstance(){
    	return instance;
    }
    
    public HashMap<Class<?>, ArrayList<Observer>> getListeners(){
    	return listenersMap;   	
    }
    
    /**
     * Create ActiveMQ broker connection and start event dispatching thread
     */
    public void start(){
        try{
            connFactory = new ActiveMQConnectionFactory( MQ_URL );
            topicConnection = connFactory.createTopicConnection();
            topicSession = topicConnection.createTopicSession( false, Session.AUTO_ACKNOWLEDGE );
            eventTopic = topicSession.createTopic( TOPIC_NAME );
            
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
     * Stop the event dispatching thread and ActiveMQ facilities
     */
    public void stop(){
        
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
     * Add event observer
     * @param eventClass
     * @param observer
     */
    public synchronized void addListener( final Class<?> eventClass, final Observer observer){
    	
    	ArrayList<Observer> observers = null;

        Class<?> leafCls = getEventLeafInterface( eventClass );
        
        if( leafCls == null){
        	return;
        }
        
        /**
         * Create a Map record with eventClass and empty observers list if we
         * don't have any observers for this eventClass yet
         */       
        if ( listenersMap.get( leafCls ) == null ){
        	
        	observers = new ArrayList<Observer>();
        	listenersMap.put( leafCls, observers);
        }
        else{
        	/**
        	 * Or get existing observers list for adding new observer
        	 */
        	observers = ( ArrayList<Observer> ) listenersMap.get( leafCls );
        }
        
        /**
         * Add observer if it is not in the list
         */
        if ( observers.indexOf(observer) < 0 ){
        	observers.add(observer);
        }
	
    }
    
    
    /**
     * Remove event observer
     * @param eventClass
     * @param observer
     */
    public synchronized void removeListener( final Class<?> eventClass, final Observer observer){
    	
    	ArrayList<Observer> observers = listenersMap.get( eventClass );
    	
    	if( observers != null ){
    		if( observers.contains(observer) ){
    			observers.remove(observer);
    		}
    	}
    	
    }
    
    /**
     * Remove all observers from the channel
     */
    public void removeAllListeners(){
    	
    }
    
    /**
     * Publish specified event to the channel
     * TODO: probably move method to observers
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
     * Get 
     * @param cls
     * @return
     */
	private static Class<?> getEventLeafInterface(Class<?> cls){
		
		Class<?> retVal = null;
		
		if( Event.class.isAssignableFrom( cls ) ){		
			retVal = cls;
			
			if ( cls.isInterface() ){
				return retVal;
			}			
		}
	   

	    Class<?>[] interfaces = cls.getInterfaces();
	    
	    if ( interfaces != null ){    	
	       for ( Class<?> iface : interfaces ){	   
	         if ( Event.class.isAssignableFrom( iface ) ){ 	 
	        	 
	        	 retVal = iface;
	        	 break;
	         }
	         
	         retVal = getEventLeafInterface( iface );
	       }
	     }

	     return retVal;
	   }
    
 /**
 * Event processing thread class reads messages from JMS topic and calls observe() on subscribed
 * observers.
 * @author sol2202
 */
public static class EventDispatcher implements Runnable{
    	
	   private boolean running = false;   
	   private ActiveMQConnectionFactory connectionFactory = null;	   
	   private Connection connection = null;	   
	   private Session session = null;   
	   private Destination destination = null; 
	   private MessageConsumer consumer = null;
	   
	   /**
	    * Event processing thread task
	    */
	   public void run(){	
		   try{
			   
			   connectionFactory = new ActiveMQConnectionFactory( MQ_URL );			   
			   connection = connectionFactory.createConnection();			   
			   connection.start();
    	        
			   session = connection.createSession( false, Session.AUTO_ACKNOWLEDGE );			   
			   destination = session.createTopic( TOPIC_NAME );			   
			   consumer = session.createConsumer( destination );
		   
			   running = true;
			   	   
			   while ( running )
			   {				  
				   Message message = consumer.receive( 1000 );

				   if ( message != null ){					   
					   if ( message instanceof ObjectMessage ){
						   
						   ObjectMessage objMessage = ( ObjectMessage ) message;
						   Object object = objMessage.getObject();
						   
						   if( Event.class.isAssignableFrom( object.getClass() ) ){
							   dispatchEvent( (Event)object );
						   } 				   					   
					   }
				   }
    	        }
    	        
    	      } catch ( Exception e ){
    	    	  e.printStackTrace();
    	      	}		
    		
    	}
	   
	   
	   /**
	    * Thread shutdown and cleanup
	    */
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
	   
	   
	   /**
	    * Send the event to all observers of this specific event class
	    * then send to observers of superclass
	    * then to observers of superclass of superclass etc. through events hierarchy.
	    * @param event
	    */
	   public void dispatchEvent( Event event ){
		   
		   for( Class<?> eventClass = event.getClass();
		   		Event.class.isAssignableFrom( eventClass );
		   		eventClass = eventClass.getSuperclass() ){
			   
			   if( listenersMap.containsKey(eventClass) ){
				   
				   ArrayList<Observer> observers = listenersMap.get( eventClass );
				   ArrayList<Observer> received = new ArrayList<Observer>();
				  
				   for( Observer observer : observers){
					   					   
					   if( !received.contains(observer) ){
						   
						   try{
							   received.add(observer);
							   observer.observe(event);
						   } catch (EventNotSupportedException e){
					   
						   }  
					   }			  
				   } 
			   }		   				   		
		   }		   
	   }
	   
    }

}
