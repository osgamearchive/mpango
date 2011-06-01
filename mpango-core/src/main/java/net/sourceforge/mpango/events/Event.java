package net.sourceforge.mpango.events;

import java.io.Serializable;

/**
 * Interface representing a event on the application
 * @author edvera
 *
 */
public interface Event extends Serializable {

	/**
	 * Source of the event
	 * @return Object that generated the event.
	 */
	Object getSource();
}
