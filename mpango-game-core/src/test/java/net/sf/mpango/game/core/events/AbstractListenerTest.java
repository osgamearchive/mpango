package net.sf.mpango.game.core.events;

import org.junit.Test;

import net.sf.mpango.game.core.exception.EventNotSupportedException;
import junit.framework.TestCase;

public class AbstractListenerTest extends TestCase {
	
	@SuppressWarnings("serial")
	@Test
	public void testDefaultReceiveEvent() {
		AbstractEvent event = new AbstractEvent(this) {};
		BaseListener listener = new BaseListener() {};
		try {
			listener.receive(event);
			fail("Exception exception not raised");
		} catch (EventNotSupportedException expected) {
			//do nothing
		}
	}

	@Test
	public void testReceiveSupportedEvent() throws EventNotSupportedException {
		SupportedEvent event = new SupportedEvent(this);
		SupportedListener listener = new SupportedListener();
		listener.receive(event);
		SupportedListener.assertListener(listener);
	}
	
	@Test
	public void testReceiveSupportedEventAsNotSupported () throws EventNotSupportedException {
		AbstractEvent event = new SupportedEvent(this);
		SupportedListener listener = new SupportedListener();
		listener.receive(event);
		SupportedListener.assertListener(listener);
	}
	
	@Test
	public void testReceiveSupportedEventInUnsupportedReference() throws EventNotSupportedException {
		SupportedEvent event = new SupportedEvent(this);
		BaseListener listener = new SupportedListener();
		listener.receive(event);
		SupportedListener.assertListener((SupportedListener) listener);
	}

	@SuppressWarnings("serial")
	@Test
	public void testReceiveUnsupportedEvent() {
		AbstractEvent event = new AbstractEvent(this){};
		SupportedListener listener = new SupportedListener();
		try {
			listener.receive(event);
			fail("Expected exception not raised");
		} catch (EventNotSupportedException expected) {
			assertTrue("The listener should not have handled the event", !(listener.numberOfEvents != 0));
		}
	}
}
