package net.sf.mpango.game.core.events;

/**
 * Interface that exposes the observers that an object has. All objects that needed to be listened to will implement
 * this interface.
 * @author etux
 *
 */
public interface Observable {
	
	/**
	 * Method iterates over all subscribed observers and notifies then of an event.
	 * @param event
	 */
	void notifyListeners(Event event);
	/**
	 * To add a new observer to the class.
	 * @param observer
	 */
	void addListener(Observer observer);
	/**
	 * To remove a observer from the class.
	 * @param observer
	 */
	void removeListener(Observer observer);
	
}
