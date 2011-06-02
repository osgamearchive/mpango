package net.sourceforge.mpango.events;

import net.sourceforge.mpango.events.AbstractEvent;
import junit.framework.TestCase;

public class AbstractEventTest extends TestCase {

	private AbstractEvent event;
	
	@SuppressWarnings("serial")
	public void setUp() {
		event = new AbstractEvent(this) {};
	}
	public void testGetSource() {
		assertEquals("Source is missing", this,event.getSource());
	}

}
