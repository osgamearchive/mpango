package net.sourceforge.mpango.events;

import net.sourceforge.mpango.exception.EventNotSupportedException;

public interface Listener {

	void receiveEvent(Event event) throws EventNotSupportedException;
}
