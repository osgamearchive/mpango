package net.sf.mpango.game.core.events;

import static org.junit.Assert.assertEquals;

import net.sf.mpango.game.core.exception.EventNotSupportedException;

/**
 * Test class that proves how event support is implemented.
 * It overrides the BaseListener class that by default doesn't support any events and adds support to the SupportedEvent class.
 * @author edvera
 *
 */
class SupportedListener extends BaseListener {
	protected int numberOfEvents = 0;
	
	@Override
	public void receive(Event event) throws EventNotSupportedException {
		if (event instanceof SupportedEvent)
			receive((SupportedEvent) event);
		else
			super.receive(event);
	}
	
	public void receive(SupportedEvent event) {
		event.handled = true;
		numberOfEvents++;
	}
	
	protected static void assertListener(SupportedListener listener) {
		assertEquals(listener.numberOfEvents, 1);
	}
}
