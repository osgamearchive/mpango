package net.sf.mpango.game.core.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.mpango.game.core.events.BaseObservable;
import net.sf.mpango.game.core.events.CommandEvent;
import net.sf.mpango.game.core.events.Event;
import net.sf.mpango.game.core.events.Observer;
import net.sf.mpango.game.core.exception.CommandException;

/**
 * <p>The purpose of this class is to execute tasks that take time.
 * The time it takes to execute the {@link Command#execute()} is determined by the {@link ITaskCommand#calculateTotalTimeSlices()} method which returns milliseconds.
 * This class receives {@link Timer} as part of the constructor, a timer which will be responsible for executing the {@link Command}.
 * This class is responsible for the {@link Thread} created by the {@link Timer} creates at scheduling time.</p>
 * @author etux
 *
 */
public abstract class AbstractTaskCommand<T extends CommandEvent> implements ITaskCommand<T>  {

	/** Default time milliseconds per time slice. */
	public static final long DEFAULT_MILLIS_PER_TIME_SLICE = 500;
	
	/** Timer assigned to this command */
	private final ExecutorService executorService;
	private final long millisPerSlice;
    private BaseObservable baseObservable;
	
	/**
	 * Constructor that needs to be called by all extending classes.
     * @param millisPerSlice the time it takes for a slice to happen.
	 * @param executorService the executor service used to execute this command.
	 * @param observers the list of observers that are interested in receiving events from this command.
	 */
	protected AbstractTaskCommand(final long millisPerSlice, final ExecutorService executorService, final List<Observer> observers) {
		this.executorService = executorService;
        this.baseObservable = new BaseObservable();
		this.millisPerSlice = millisPerSlice;
        this.baseObservable.addListeners(observers);
	}
	/**
	 * Constructor to ease up developer from constructing the {@link List}.
	 */
	protected AbstractTaskCommand(final long millisPerSlice, final ExecutorService executorService, final Observer... observers) {
		this(millisPerSlice, executorService, Arrays.asList(observers));
	}
	/**
	 * Constructor that assigns the {@link AbstractTaskCommand#DEFAULT_MILLIS_PER_TIME_SLICE} to the command.
	 */
	protected AbstractTaskCommand(final ExecutorService executorService, final Observer... observers) {
		this(DEFAULT_MILLIS_PER_TIME_SLICE, executorService, observers);
	}

	/*
	 * Method that returns the number of milliseconds the command will take to execute.
	 * @param timeMillisPerTimeSlice factor multiplying the time slices of the command.
	 * @return total amount of time the command will take to execute.
	 */
	long calculateTotalTimeMillis(final long timeMillisPerTimeSlice) {
        //Basic time calculation that will fit most of the cases.
		return (timeMillisPerTimeSlice * calculateTotalTimeSlices());
	}
	/**
	 * Implementation of the {@link java.util.TimerTask#run()} ()} method which is a
	 * Template Method for the inheriting {@link Command}(s). 
	 */
	@Override
	final public T call() {
        try {
            Thread.sleep(getTotalDuration());
            final T event = execute();
            notifyListeners(event);
            return event;
        } catch (final InterruptedException e) {
            LOGGER.log(Level.INFO, "The thread executing the command has been interrupted", e);
        } catch (final CommandException e) {
            LOGGER.log(Level.INFO, "The execution of the command failed", e);
        }
        return null;
    }

    @Override
    public long getTotalDuration() {
        return calculateTotalTimeMillis(millisPerSlice);
    }

	@Override
	public void notifyListeners(final Event event) {
		baseObservable.notifyListeners(event);
	}

	@Override
	public void addListener(final Observer observer) {
		baseObservable.addListener(observer);
	}
	@Override
	public void removeListener(final Observer observer) {
		baseObservable.removeListener(observer);
	}

    private static final Logger LOGGER = Logger.getLogger(AbstractTaskCommand.class.getName());
}