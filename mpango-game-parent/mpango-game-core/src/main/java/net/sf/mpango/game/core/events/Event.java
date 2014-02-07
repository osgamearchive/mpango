package net.sf.mpango.game.core.events;

import java.io.Serializable;

/**
 * Interface representing a event on the application
 * @author edvera
 *
 */
public interface Event<T> extends Serializable {

	/**
	 * Source of the event
	 * @return Object that generated the event.
	 */
	T getSource();
	
	/**
	 * Method that returns the time where the event was originated.
	 */
	long getOccurrenceTime();
}
