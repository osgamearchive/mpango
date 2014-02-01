package net.sf.mpango.game.core.events;

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
