package net.sourceforge.mpango.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class without any value besides proving the different test cases of this unit test.
 * @author edvera
 *
 */
@SuppressWarnings("serial")
class SupportedEvent extends AbstractEvent {
	
	protected boolean handled;
	
	public SupportedEvent(Object source) {
		super(source);
	}
	
	protected static void assertEvent(SupportedEvent event) {
		assertTrue("The event must have been handled", event.handled);
		assertNotNull("The event must contain the source which ocasionated it", event.getSource());
		assertNotNull("The event must contain the time it happened", event.getTime());
	}
}
