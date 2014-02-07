package net.sf.mpango.game.core.events;

import net.sf.mpango.game.core.exception.EventNotSupportedException;

import static org.junit.Assert.assertEquals;

/**
 * Test class that proves how event support is implemented.
 * It overrides the BaseObserver class that by default listens to all events silently and adds support to the SupportedEvent class.
 * @author edvera
 *
 */
class SupportedObserver extends BaseObserver {
	protected int numberOfEvents = 0;
	
	@Override
	public void observe(Event event) throws EventNotSupportedException {
		if (event instanceof SupportedEvent)
			receive((SupportedEvent) event);
		else
			super.observe(event);
	}
	
	public void receive(SupportedEvent event) {
		event.handled = true;
		numberOfEvents++;
	}
	
	protected static void assertListener(SupportedObserver listener) {
		assertEquals(listener.numberOfEvents, 1);
	}
}
