package net.sf.mpango.game.core.events;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.sf.mpango.game.core.events.AbstractListenerObservable;
import net.sf.mpango.game.core.events.Listener;
import net.sf.mpango.game.core.exception.EventNotSupportedException;

public class AbstractListenerObservableTest {

	private SupportedListenerBroadcaster listenerBroadcaster;
	private SupportedListener listener;
	
	@Before
	public void setUp() throws Exception {
		SupportedListener listener = new SupportedListener();
		listenerBroadcaster = new SupportedListenerBroadcaster(listener);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testReceiveEvent() throws EventNotSupportedException {
		SupportedListener listener = new SupportedListener();
		listenerBroadcaster.addListener(listener);
		SupportedEvent event = new SupportedEvent(this);
		listenerBroadcaster.receive(event);
		SupportedEvent.assertEvent(event);
		SupportedListener.assertListener(listener);
	}

	@Test
	public void testNotifyAllListeners() throws EventNotSupportedException {
		SupportedEvent event = new SupportedEvent(this);
		SupportedListener listener = new SupportedListener();
		listenerBroadcaster.addListener(listener);
		listenerBroadcaster.notifyListeners(event);
		SupportedEvent.assertEvent(event);
		SupportedListener.assertListener(listener);
		assertEquals(
				"The broadcaster should not have picked up the message, just spread among its listeners", 
				0, 
				((SupportedListener)listenerBroadcaster.getMyself()).numberOfEvents);
	}

	@Test
	public void testAddListener() {
		SupportedListener listener = new SupportedListener();
		listenerBroadcaster.addListener(listener);
		assertEquals("There must be one listener present", 1, listenerBroadcaster.getListeners().size());
	}

	@Test
	public void testRemoveListener() {
		listenerBroadcaster.removeListener(listener);
		assertEquals("The must not be any listeners present", 0, listenerBroadcaster.getListeners().size());
	}
	
	class SupportedListenerBroadcaster extends AbstractListenerObservable {
		
		public SupportedListenerBroadcaster(Listener yourself) {
			super(yourself);
		}
	}
}
