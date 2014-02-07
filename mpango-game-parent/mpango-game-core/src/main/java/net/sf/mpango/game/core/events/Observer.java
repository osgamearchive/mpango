package net.sf.mpango.game.core.events;

import net.sf.mpango.game.core.exception.EventNotSupportedException;

/**
 * Observer interface.
 * It contains the method observe with a event as parameter. All the classes that implement this interface can be potentially registered
 * as observers of another class. 
 * @author edvera
 *
 */
public interface Observer<T extends Event> {

	/**
	 * Method that all Observer implementations must implement. This method receives an event and reacts on it.
	 * @param event Event that the listener will react upon.
	 * @throws EventNotSupportedException In case the listener does not support the event.
	 */
	public void observe(T event) throws EventNotSupportedException;
}
