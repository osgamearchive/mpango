package net.sourceforge.mpango.events;

/**
 * Interface that exposes the listeners that an object has. All objects that needed to be listened to will implement
 * this interface.
 * @author etux
 *
 */
public interface Broadcaster<T extends Listener> {
	/**
	 * Method iterates over all subscribed listeners and notifies then of an event.
	 * @param event
	 */
	void notifyListeners(Event event);
	/**
	 * To add a new listener to the class.
	 * @param listener
	 */
	void addListener(T listener);
	/**
	 * To remove a listener from the class.
	 * @param listener
	 */
	void removeListener(T listener);
	
}
