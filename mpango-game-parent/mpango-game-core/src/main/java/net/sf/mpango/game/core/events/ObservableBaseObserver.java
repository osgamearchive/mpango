package net.sf.mpango.game.core.events;

import java.util.ArrayList;
import java.util.List;

import net.sf.mpango.game.core.exception.EventNotSupportedException;

/**
 * Class that receives an event, processes it, and propagates it to all the subscribed observers.
 * @author edvera
 */
public class ObservableBaseObserver extends BaseObserver implements Observable {
	
	protected List<Observer> observers;
	private Observer myself;
	
	public ObservableBaseObserver(Observer yourself) {
		this.myself = yourself;
		observers = new ArrayList<Observer>();
	}
	
	@Override
	public final void observe(Event event) throws EventNotSupportedException {
		try {
			if (myself != null) {
				myself.observe(event);
			}
		} catch (EventNotSupportedException e) {
			
		} finally {
			//We make sure the chain is not broken because of the exception
			notifyListeners(event);
		}
	}
	
	@Override
	 public void notifyListeners (Event event) {
		for (Observer observer : observers) {
			try {
				observer.observe(event);
			} catch (EventNotSupportedException e) {}
		}
	}
	@Override
	public void addListener(Observer observer) {
		observers.add(observer);
	}
	@Override
	public void removeListener(Observer observer) {
		observers.remove(observer);
	}

	protected List<Observer> getObservers() {
		return observers;
	}

	public Observer getMyself() {
		return myself;
	}
}