package net.sourceforge.mpango.events;

import net.sourceforge.mpango.events.AbstractEvent;
import net.sourceforge.mpango.events.AbstractListener;
import net.sourceforge.mpango.events.Event;
import net.sourceforge.mpango.exception.EventNotSupportedException;
import junit.framework.TestCase;

public class AbstractListenerTest extends TestCase {
	
	public void testDefaultReceiveEvent() {
		AbstractEvent event = new AbstractEvent(this) {};
		AbstractListener listener = new AbstractListener() {};
		try {
			listener.receiveEvent(event);
			fail("Exception exception not raised");
		} catch (EventNotSupportedException expected) {
			//do nothing
		}
	}
	
	public void testReceiveSupportedEvent() throws EventNotSupportedException {
		SupportedEvent event = new SupportedEvent(this);
		AbstractListener listener = new SupportedListener();
		listener.receiveEvent(event);
	}

	public void testReceiveUnsupportedEvent() {
		AbstractEvent event = new AbstractEvent(this){};
		AbstractListener listener = new SupportedListener();
		try {
			listener.receiveEvent(event);
			fail("Expected exception not raised");
		} catch (EventNotSupportedException expected) {}
	}
	
	class SupportedEvent extends AbstractEvent {
		public SupportedEvent(Object source) {
			super(source);
		}
	}
	/**
	 * Class proving that in order to determine what events are handled, it should be done on the listener implementation.
	 * @author etux
	 *
	 */
	class SupportedListener extends AbstractListener {
		@Override
		public void receiveEvent(Event event) throws EventNotSupportedException {
			if (!(event instanceof SupportedEvent)) {
				throw new EventNotSupportedException(event);
			}
		}
	}
}
