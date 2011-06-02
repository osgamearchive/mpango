package net.sourceforge.mpango.events;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.mpango.exception.EventNotSupportedException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class that receives an event, processing it, and propagating it to all the listeners it contains.
 * @author etux
 * @param <T> Object inheriting the Listener interface
 */
public abstract class AbstractListenerBroadcaster<T extends Listener> extends AbstractListener implements Broadcaster {

	private static final Log logger = LogFactory.getLog(AbstractListenerBroadcaster.class);
	protected List<Listener> listeners;
	
	/**
	 * This method should be overriden by those who extend this class
	 * @param event
	 */
	protected void handleEvent(Event event) throws EventNotSupportedException {
		throw new EventNotSupportedException(event);
	}
	@Override
	public void receiveEvent(Event event) throws EventNotSupportedException {
		try {
			handleEvent(event);
		} catch (EventNotSupportedException e) {
			logger.error("Unable to handle event", e);
		} finally {
			//We make sure the chain is not broken because of the exception
			notifyListeners(event);
		}
	}
	
	public void notifyListeners(Event event) {
		for (Listener listener : getListeners()) {
			try {
				listener.receiveEvent(event);
			} catch (EventNotSupportedException e) {
				logger.error("Error on listener: " + listener, e);
			}
		}
	}
	public void addListener(Listener listener) {
		listeners = getListeners();
		listeners.add(listener);
	}
	public void removeListener(Listener listener) {
		listeners = getListeners();
		listeners.remove(listener);
	}
	protected List<Listener> obtainListenerList(List<T> list) {
		if (listeners == null) {
			listeners = new ArrayList<Listener>();
			for (T object : list) {
				Listener listener = object;
				listeners.add(listener);
			}
		}
		return listeners;
	}
	protected abstract List<Listener> getListeners();
}