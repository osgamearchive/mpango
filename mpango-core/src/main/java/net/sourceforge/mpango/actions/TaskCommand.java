package net.sourceforge.mpango.actions;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.sourceforge.mpango.events.CommandExecutedEvent;
import net.sourceforge.mpango.events.Listener;
import net.sourceforge.mpango.exception.CommandException;
import net.sourceforge.mpango.exception.EventNotSupportedException;

/**
 * The purpose of this class is to execute tasks that need time to be executed.
 * The time it takes to execute the {@link Command#execute()} is determined by the {@link TaskCommand.calculateTotalTimeSlices()} method which returns milliseconds.
 * This class receives {@link Timer} as part of the constructor, a timer which will be responsible for executing the {@link Command}.
 * This class is responsible for the {@link Thread} created by the {@link Timer} creates at scheduling time.
 * @author etux
 *
 */
public abstract class TaskCommand extends TimerTask implements Command  {

	/** Default time milliseconds per time slice. */
	public static final long DEFAULT_MILLIS_PER_TIME_SLICE = 1000;
	
	/** Timer assigned to this command */
	private Timer timer;
	private List<Listener> listeners;
	private long millisPerSlice;
	
	/**
	 * Constructor that needs to be called by all extending classes.
	 * @param timer
	 * @param listeners
	 */
	protected TaskCommand(long millisPerSlice, Timer timer, List<Listener> listeners) {
		this.timer = timer;
		this.listeners = listeners;
		this.millisPerSlice = millisPerSlice;
	}
	/**
	 * Commodity method to ease up developer from constructing the {@link List}.
	 * Uses {@link Arrays#asList()} to construct the list.
	 * @param timer
	 * @param listeners
	 */
	protected TaskCommand(long millisPerSlice, Timer timer, Listener...listeners) {
		this(millisPerSlice, timer, Arrays.asList(listeners));
	}
	/**
	 * Constructor that uses the default time milliseconds per time slice.
	 * @param timer to schedule the command.
	 */
	protected TaskCommand(Timer timer, Listener...listeners) {
		this(DEFAULT_MILLIS_PER_TIME_SLICE, timer, listeners);
	}
	/**
	 * Implementation of the execute method of the command. 
	 * This method schedules the command as a TimerTask that will be called
	 * executed in the {@link TaskCommand#calculateTotalTimeSlices()} in the future.
	 * amount of time slices needed.
	 */
	public final void execute() throws CommandException {
		evaluateExecution();
		timer.schedule(this, calculateTotalTimeMillis(millisPerSlice));
	}
	/**
	 * Implementation of the {@link Timer#run()} method which is a 
	 * Template Method for the inheriting {@link Command}(s). 
	 */
	public void run() {
		runExecute();
		notifyListeners();
	}
	/**
	 * Method that notifies all listeners for the {@link Command}.
	 */
	private void notifyListeners() {
		for(Listener listener : listeners) {
			try {
				listener.receiveEvent(new CommandExecutedEvent(this));
			} catch (EventNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Method that calculates the total amount of milliseconds the command needs in order to be completed.
	 * @param timeMillisPerTimeSlice Time in milliseconds a time slice is.
	 * @return
	 */
	public abstract long calculateTotalTimeMillis(long timeMillisPerTimeSlice);
	/**
	 * Method that evaluates if a command can be executed.
	 * @throws CommandException in case a command can not be executed for a given reason.
	 */
	protected abstract void evaluateExecution() throws CommandException;
	/**
	 * Abstract method that all {@link TaskCommand} inheritors need to implement.
	 */
	protected abstract void runExecute();
	/**
	 * Method that add a listener to the {@link TaskCommand}.
	 * @param listener
	 */
	@Override
	public void addListener(Listener listener) {
		this.listeners.add(listener);
	}
	@Override
	public void removeListener(Listener listener) {
		this.listeners.remove(listener);
	}
}