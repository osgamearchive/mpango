package net.sf.mpango.game.core.action;

import net.sf.mpango.game.core.events.Observable;
import net.sf.mpango.game.core.exception.CommandException;

/**
 * <p>All elements that need to perform actions in the game should do it using commands.</p>
 * @author edvera
 */
public interface Command extends Observable {

	/**
	 * Method that executes the logic of the command.
	 * @throws CommandException in case there is a problem executing the Command.
	 */
	void execute() throws CommandException;
}
