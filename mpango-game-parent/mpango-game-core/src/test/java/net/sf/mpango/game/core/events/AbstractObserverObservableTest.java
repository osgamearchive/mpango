package net.sf.mpango.game.core.events;

import net.sf.mpango.game.core.exception.EventNotSupportedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbstractObserverObservableTest {

	private SupportedObserverBroadcaster listenerBroadcaster;
	private SupportedObserver listener;
	
	@Before
	public void setUp() throws Exception {
		SupportedObserver listener = new SupportedObserver();
		listenerBroadcaster = new SupportedObserverBroadcaster(listener);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testReceiveEvent() throws EventNotSupportedException {
		SupportedObserver listener = new SupportedObserver();
		listenerBroadcaster.addListener(listener);
		SupportedEvent event = new SupportedEvent(this);
		listenerBroadcaster.observe(event);
		SupportedEvent.assertEvent(event);
		SupportedObserver.assertListener(listener);
	}

	@Test
	public void testNotifyAllListeners() throws EventNotSupportedException {
		SupportedEvent event = new SupportedEvent(this);
		SupportedObserver listener = new SupportedObserver();
		listenerBroadcaster.addListener(listener);
		listenerBroadcaster.notifyListeners(event);
		SupportedEvent.assertEvent(event);
		SupportedObserver.assertListener(listener);
		assertEquals(
				"The broadcaster should not have picked up the message, just spread among its observers",
				0, 
				((SupportedObserver)listenerBroadcaster.getMyself()).numberOfEvents);
	}

	@Test
	public void testAddListener() {
		SupportedObserver listener = new SupportedObserver();
		listenerBroadcaster.addListener(listener);
		assertEquals("There must be one listener present", 1, listenerBroadcaster.getObservers().size());
	}

	@Test
	public void testRemoveListener() {
		listenerBroadcaster.removeListener(listener);
		assertEquals("The must not be any observers present", 0, listenerBroadcaster.getObservers().size());
	}
	
	class SupportedObserverBroadcaster extends ObservableBaseObserver {
		
		public SupportedObserverBroadcaster(Observer yourself) {
			super(yourself);
		}
	}
}
