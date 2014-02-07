package net.sf.mpango.game.core.events;

import junit.framework.TestCase;
import net.sf.mpango.game.core.exception.EventNotSupportedException;
import org.junit.Test;

public class AbstractObserverTest extends TestCase {

	@Test(expected = EventNotSupportedException.class)
	public void testDefaultReceiveEvent() throws EventNotSupportedException {
		AbstractEvent event = new AbstractEvent(this) {};
		BaseObserver listener = new BaseObserver() {};
        listener.observe(event);
	}

	@Test
	public void testReceiveSupportedEvent() throws EventNotSupportedException {
		SupportedEvent event = new SupportedEvent(this);
		SupportedObserver listener = new SupportedObserver();
		listener.receive(event);
		SupportedObserver.assertListener(listener);
	}
	
	@Test
	public void testReceiveSupportedEventAsNotSupported () throws EventNotSupportedException {
		AbstractEvent event = new SupportedEvent(this);
		SupportedObserver listener = new SupportedObserver();
		listener.observe(event);
		SupportedObserver.assertListener(listener);
	}
	
	@Test
	public void testReceiveSupportedEventInUnsupportedReference() throws EventNotSupportedException {
		SupportedEvent event = new SupportedEvent(this);
		BaseObserver listener = new SupportedObserver();
		listener.observe(event);
		SupportedObserver.assertListener((SupportedObserver) listener);
	}

	@Test(expected = EventNotSupportedException.class)
	public void testReceiveUnsupportedEvent() throws EventNotSupportedException {
		AbstractEvent event = new AbstractEvent(this){};
		SupportedObserver listener = new SupportedObserver();
        listener.observe(event);
	}

    /**
     * Test class that proves how event support is implemented.
     * It overrides the BaseObserver class that by default listens to all events silently and adds support to the SupportedEvent class.
     * @author edvera
     *
     */
    private static class SupportedObserver extends BaseObserver {

    	protected int numberOfEvents = 0;

    	@Override
    	public void observe(Event event) throws EventNotSupportedException {
    		if (event instanceof SupportedEvent)
    			receive((SupportedEvent) event);
    		else
    			super.observe(event);
    	}

    	public void receive(final SupportedEvent event) {
    		event.handled = true;
    		numberOfEvents++;
    	}

    	protected static void assertListener(final SupportedObserver listener) {
    		assertEquals(listener.numberOfEvents, 1);
    	}
    }

}
