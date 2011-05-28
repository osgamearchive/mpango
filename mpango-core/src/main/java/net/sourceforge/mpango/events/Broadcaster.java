package net.sourceforge.mpango.events;

/**
 * Interface that exposes the listeners that an object has.
 * @author etux
 *
 */
public interface Broadcaster {

	void notifyAllListeners(Event event);
	void addListener(Listener listener);
	void removeListener(Listener listener);
	
}
