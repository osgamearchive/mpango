package net.sourceforge.mpango.events;

import net.sourceforge.mpango.exception.EventNotSupportedException;

public abstract class AbstractListener implements Listener {
	
	/**
	 * Basic implementation of the method in order to identify the unsupported events
	 * @param event
	 * @throws EventNotSupportedException
	 */
	public void receiveEvent(Event event) throws EventNotSupportedException {
		throw new EventNotSupportedException(event);
	}

}
