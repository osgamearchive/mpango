package net.sourceforge.mpango.events;

public interface Listener {

	void receiveEvent(Event event) throws EventNotSupportedException;
}
