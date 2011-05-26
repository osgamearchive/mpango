package net.sourceforge.mpango.events;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.mpango.events.AbstractEvent;
import net.sourceforge.mpango.events.AbstractListenerBroadcaster;
import net.sourceforge.mpango.events.Event;
import net.sourceforge.mpango.events.Listener;
import net.sourceforge.mpango.exception.EventNotSupportedException;

import junit.framework.TestCase;

public class AbstractListenerBroadcasterTest extends TestCase {

	private TestListenerBroadcaster listenerBroadcaster;
	private TestListener listener;
	
	protected void setUp() throws Exception {
		listenerBroadcaster = new TestListenerBroadcaster();
		listener = new TestListener();
		listenerBroadcaster.addListener(listener);
	}

	protected void tearDown() throws Exception {
	}

	public void testReceiveEvent() throws EventNotSupportedException {
		listenerBroadcaster.receiveEvent(new TestEvent(this) {});
		assertTrue("The event must have been handled", listenerBroadcaster.handledEvent);
	}

	public void testNotifyAllListeners() throws EventNotSupportedException {
		listenerBroadcaster.notifyAllListeners(new TestEvent(this) {});
		assertTrue("The event must have been propagated and handled by included listeners", listener.handledEvent);
	}

	public void testAddListener() {
		TestListener listener = new TestListener();
		listenerBroadcaster.addListener(listener);
		assertEquals("There must be two listeners present", 2, listenerBroadcaster.getListeners().size());
	}

	public void testRemoveListener() {
		listenerBroadcaster.removeListener(listener);
		assertEquals("The must not be any listeners present", 0, listenerBroadcaster.getListeners().size());
	}

	public void testGetList() {
		List<Listener> listeners = listenerBroadcaster.obtainListenerList(null);
		assertSame(listeners.get(0).getClass(), TestListener.class);
	}
	
	class TestListenerBroadcaster extends AbstractListenerBroadcaster<TestListener> {

		protected boolean handledEvent = false;
		protected List<TestListener> listeners = new ArrayList<TestListener>();	
		
		@Override
		protected void handleEvent(Event event) throws EventNotSupportedException {
			handledEvent = true;
		}
		@Override
		protected List<Listener> getListeners() {
			return obtainListenerList(listeners);
		}	
	}
	
	class TestListener implements Listener {
		protected boolean handledEvent = false;
		public void receiveEvent(Event event) throws EventNotSupportedException {
			handledEvent = true;
		}
		
	}
	
	class TestEvent extends AbstractEvent {
		public TestEvent(Object source) {
			super(source);
		}
		
	}
}
