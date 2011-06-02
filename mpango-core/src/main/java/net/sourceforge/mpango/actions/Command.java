package net.sourceforge.mpango.actions;

import net.sourceforge.mpango.events.Broadcaster;
import net.sourceforge.mpango.exception.CommandException;

/**
 * Command interface. All elements that need to perform actions in the game should do it using commands.
 * @author etux
 *
 */
public interface Command extends Broadcaster {

	/**
	 * Method that executes the logic of the command.
	 * @throws CommandException in case there is a problem executing the Command.
	 */
	void execute() throws CommandException;
}
