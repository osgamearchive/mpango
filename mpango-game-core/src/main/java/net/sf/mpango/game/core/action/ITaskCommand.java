package net.sf.mpango.game.core.action;

import net.sf.mpango.game.core.events.CommandExecutedEvent;
import net.sf.mpango.game.core.exception.CommandException;

/**
 * <p>This interface offers the important methods in order to implement a TaskCommand class.
 * TaskCommands are those that require certain amount of time in order to complete and obtain the results of the command.
 * The time it takes for a TaskCommand to complete depends on two factors:
 * <ol>
 *   <li>the total amount of time slices the command needs.</li>
 *   <li>the factor (in milliseconds) that multiplies the time slices.</li>
 * </ol>
 * Developers are encourage to extend the {@link AbstractTaskCommand} class that already has built in functionality and to
 * implement the methods from this interface for customizing the command.
 * </p>
 * 
 * @author edvera
 *
 */
public interface ITaskCommand extends Command {

	/**
	 * Method that calculates the total amount of Time Slices needed to complete the command.
	 * @return
	 */
	int calculateTotalTimeSlices();
	/**
	 * Method that contains the logic of the command.
	 * @returns The event produced by the execution of this method.
	 */
	CommandExecutedEvent runExecute();
	/**
	 * Method that evaluates if the execution is possible before adding the command to the queue.
	 * @throws CommandException In case it is not possible to run the Command for different reasons.
	 */
	abstract void evaluateExecution() throws CommandException;

}