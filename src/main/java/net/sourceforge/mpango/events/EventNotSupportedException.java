package net.sourceforge.mpango.events;

public class EventNotSupportedException extends Exception {
	
	private static final long serialVersionUID = -384602339695204717L;
	private Event event;
	
	public EventNotSupportedException(Event event) {
		this.event = event;
	}

}
