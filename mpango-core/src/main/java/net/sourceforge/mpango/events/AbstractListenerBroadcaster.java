package net.sourceforge.mpango.events;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.mpango.exception.EventNotSupportedException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Class that receives an event, processes it, and propagates it to all the subscribed listeners.
 * @author edvera
 * @param <T> Object inheriting the Listener interface
 */
public abstract class AbstractListenerBroadcaster<T extends Listener> extends AbstractListener implements Broadcaster<T> {

	private static final Log logger = LogFactory.getLog(AbstractListenerBroadcaster.class);
	protected List<T> listeners;
	
	public AbstractListenerBroadcaster() {
		listeners = new ArrayList<T>();
	}
	
	@Override
	public void receiveEvent(Event event) throws EventNotSupportedException {
		try {
			super.receiveEvent (event);
		} catch (EventNotSupportedException e) {
			logger.debug("Unable to handle event", e);
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
				logger.debug("Error on listener: " + listener, e);
			}
		}
	}
	@Override
	public void addListener(T listener) {
		listeners = getListeners();
		listeners.add(listener);
	}
	public void removeListener(T listener) {
		listeners = getListeners();
		listeners.remove(listener);
	}
	protected List<T> obtainListenerList(List<T> list) {
		if (listeners == null) {
			listeners = new ArrayList<T>();
			for (T object : list) {
				T listener = object;
				listeners.add(listener);
			}
		}
		return listeners;
	}
	protected List<T> getListeners() {
		return listeners;
	}
}