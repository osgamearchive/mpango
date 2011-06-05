package net.sourceforge.mpango.exception;

import net.sourceforge.mpango.events.Event;

public class EventNotSupportedException extends Exception {
	
	private Event event;
	
	public EventNotSupportedException(Event event) {
		this.setEvent(event);
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Event getEvent() {
		return event;
	}
	
	private static final long serialVersionUID = -384602339695204717L;

}
