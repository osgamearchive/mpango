package net.sf.mpango.game.core.commands;

import java.util.concurrent.Callable;

import net.sf.mpango.game.core.events.CommandEvent;
import net.sf.mpango.game.core.events.Observable;
import net.sf.mpango.game.core.exception.CommandException;

/**
 * A command is a class that executes an action and as result produces events that are communicated to its observers.
 * Call method should always call execute. Abstract classes implementing Command may implement call, which is the
 * generic way of executing a command, and leave execute unimplemented for specific commands to do so.
 * @author edvera
 */
public interface Command<T extends CommandEvent> extends Observable, Callable<T> {

	/**
	 * Method that executes the logic of the command.
	 * @throws CommandException in case there is a problem executing the Command.
	 */
	T execute() throws CommandException;

}
