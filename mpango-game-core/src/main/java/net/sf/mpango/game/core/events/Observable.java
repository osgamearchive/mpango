package net.sf.mpango.game.core.events;

/**
 * Interface that exposes the listeners that an object has. All objects that needed to be listened to will implement
 * this interface.
 * @author etux
 *
 */
public interface Observable {
	
	/**
	 * Method iterates over all subscribed listeners and notifies then of an event.
	 * @param event
	 */
	void notifyListeners(Event event);
	/**
	 * To add a new listener to the class.
	 * @param listener
	 */
	void addListener(Listener listener);
	/**
	 * To remove a listener from the class.
	 * @param listener
	 */
	void removeListener(Listener listener);
	
}
