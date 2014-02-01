package net.sf.mpango.game.core.events;

import net.sf.mpango.game.core.events.channel.*;
import junit.framework.TestCase;
import java.util.*;


public class EventChannelTest extends TestCase {
	
	private EventChannel testChannel = EventChannel.getInstance();
	
	public void testAddListener(){
		
		TestListener testListener = new TestListener();		
		testChannel.addListener( LeftRightEvent.class, testListener );
		
		ArrayList<Listener> listeners = testChannel.getListeners().get(LeftRightEvent.class);
		
		assertTrue( listeners.contains(testListener) );
	}
	
	
	public void testRemoveListener(){
		
		TestListener listener = new TestListener();
		testChannel.addListener( RightEvent.class, listener );
		
		testChannel.removeListener( RightEvent.class, listener);
		
		ArrayList<Listener> listeners = testChannel.getListeners().get(RightEvent.class);
		
		assertFalse( listeners.contains(listener) );
		
	}
	
	
	public void testDispatch(){
		
		EventChannel.EventDispatcher dispatcher = new EventChannel.EventDispatcher();
		
		TestListener RightEventListener = new TestListener();
		testChannel.addListener( RightEvent.class, RightEventListener );
		
		TestListener LeftEventListener = new TestListener();
		testChannel.addListener( LeftEvent.class, LeftEventListener );
		
		TestListener RightRightEventListener = new TestListener();
		testChannel.addListener( RightRightEvent.class, RightRightEventListener );
		
		TestListener LeftRightEventListener = new TestListener();
		testChannel.addListener( LeftRightEvent.class, LeftRightEventListener );
		
		Event rightEvent = new RightEvent();
		dispatcher.dispatchEvent( rightEvent );
		assertTrue( RightEventListener.getReceivedEvent() == rightEvent );
		assertFalse( LeftEventListener.getReceivedEvent() == rightEvent );
		assertFalse( RightRightEventListener.getReceivedEvent() == rightEvent );
		assertFalse( LeftRightEventListener.getReceivedEvent() == rightEvent );
		
		Event rightRightEvent = new RightRightEvent();
		dispatcher.dispatchEvent( rightRightEvent );
		assertTrue( RightRightEventListener.getReceivedEvent() == rightRightEvent );
		assertTrue( RightEventListener.getReceivedEvent() == rightRightEvent );
		assertFalse( LeftEventListener.getReceivedEvent() == rightRightEvent );
		assertFalse( LeftRightEventListener.getReceivedEvent() == rightRightEvent );			
		
	}
	
static class TestListener implements Listener{
		
	private Event event = null;
		
	public void receive( Event event ){
		this.event = event;
	}
		
	public Event getReceivedEvent(){
		return event;
	}
}
	
	
static class RightEvent extends AbstractEvent{
		
	private static final long serialVersionUID = -1715877684150597704L;
		
	RightEvent(){
		super( "Unit test" );
	}
}
	

static class LeftEvent extends AbstractEvent{
		
	private static final long serialVersionUID = -1195472690272592101L;
		
	LeftEvent(){
		super( "Unit test" );
	}
}
	

static class RightRightEvent extends RightEvent{
		
	private static final long serialVersionUID = -1294677650134623987L;
}
	

static class LeftRightEvent extends RightEvent{
		
	private static final long serialVersionUID = -1895677684179585491L;
}

}
