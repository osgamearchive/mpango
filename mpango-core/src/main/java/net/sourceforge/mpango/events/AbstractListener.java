package net.sourceforge.mpango.events;

import net.sourceforge.mpango.exception.EventNotSupportedException;

/**
 * Commodity class that implements the Listener interface.
 * @author etux
 *
 */
public abstract class AbstractListener implements Listener {
	
	/**
	 * Basic implementation of the method in order to identify the unsupported events
	 * @param event
	 * @throws EventNotSupportedException
	 */
	public void receive(Event event) throws EventNotSupportedException {
		throw new EventNotSupportedException(event);
	}

}
