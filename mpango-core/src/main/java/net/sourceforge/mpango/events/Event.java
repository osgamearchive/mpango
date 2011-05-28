package net.sourceforge.mpango.events;

import java.io.Serializable;

/**
 * Interface representing a event on the application
 * @author etux
 *
 */
public interface Event extends Serializable {

	Object getSource();
}
