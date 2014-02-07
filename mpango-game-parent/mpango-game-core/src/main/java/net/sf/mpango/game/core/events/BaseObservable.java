package net.sf.mpango.game.core.events;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.mpango.common.utils.LocalizedMessageBuilder;
import net.sf.mpango.game.core.exception.EventNotSupportedException;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
public class BaseObservable implements Observable {

    private List<Observer> observerList;

    public BaseObservable() {
        LOGGER.log(Level.INFO, this.getClass().getSimpleName() + " created");
        observerList = new ArrayList<>();
    }

    @Override
    public void notifyListeners(final Event event) {
        for (final Observer observer : observerList) {
            try {
                LOGGER.log(Level.INFO, "Observer " + this.getClass().getSimpleName() +" informed about event " + event.getClass().getSimpleName());
                observer.observe(event);
            } catch (final EventNotSupportedException e) {
                LOGGER.log(Level.FINEST,
                        LocalizedMessageBuilder.getSystemMessage(
                                this,
                                MessageConstants.EVENT_NOT_SUPPORTED,
                                event.getClass().getName()
                        ), e);
            }
        }
    }

    public void addListeners(final List<Observer> observers) {
        for (Observer observer : observers) {
            addListener(observer);
        }
    }

    public void removeListeners() {
        this.observerList = null;
    }

    @Override
    public void addListener(final Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeListener(final Observer observer) {
        observerList.remove(observer);
    }

    private static final Logger LOGGER = Logger.getLogger(BaseObservable.class.getName());
}
