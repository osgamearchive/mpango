package net.sf.mpango.game.core.events;

import net.sf.mpango.game.core.exception.EventNotSupportedException;

/**
 * Commodity class that implements the Observer interface.
 * @author etux
 *
 */
public class BaseObserver<T extends Event> implements Observer<T> {
	
	/**
	 * Basic implementation of the method in order to identify the unsupported events
	 * @param event
	 * @throws EventNotSupportedException
	 */
	public void observe(T event) throws EventNotSupportedException {
        //The default observer observers silently.
    }

}
