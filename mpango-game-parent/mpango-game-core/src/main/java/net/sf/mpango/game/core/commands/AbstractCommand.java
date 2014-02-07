package net.sf.mpango.game.core.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.mpango.game.core.events.BaseObservable;
import net.sf.mpango.game.core.events.CommandEvent;
import net.sf.mpango.game.core.events.Event;
import net.sf.mpango.game.core.events.Observer;
import net.sf.mpango.game.core.exception.CommandException;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 *         Date: 05/02/14
 *         Time: 23:07
 */
public abstract class AbstractCommand<T extends CommandEvent> implements Command<T> {

    private static final Logger LOGGER = Logger.getLogger(AbstractCommand.class.getName());
    private BaseObservable baseObservable;

    public AbstractCommand() {
        baseObservable = new BaseObservable();
    }

    @Override
    final public T call() {
        try {
            final T commandEvent = execute();
            notifyListeners(commandEvent);
            return commandEvent;
        } catch (final CommandException e) {
            LOGGER.log(Level.INFO, "Error while executing the command", e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void notifyListeners(Event event) {
        baseObservable.notifyListeners(event);
    }

    @Override
    public void addListener(Observer observer) {
        baseObservable.addListener(observer);
    }

    @Override
    public void removeListener(Observer observer) {
        baseObservable.removeListener(observer);
    }
}
