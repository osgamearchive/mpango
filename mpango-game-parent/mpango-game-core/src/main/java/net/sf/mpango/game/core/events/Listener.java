package net.sf.mpango.game.core.events;

import net.sf.mpango.game.core.exception.EventNotSupportedException;

/**
 * Listener interface.
 * It contains the method receive with a event as parameter. All the classes that implement this interface can be potentially registered
 * as observers of another class. 
 * @author edvera
 *
 */
public interface Listener {

	/**
	 * Method that all Listener implementations must implement. This method receives an event and reacts on it.
	 * @param event Event that the listener will react upon.
	 * @throws EventNotSupportedException In case the listener does not support the event.
	 */
	public void receive (Event event) throws EventNotSupportedException;
}
