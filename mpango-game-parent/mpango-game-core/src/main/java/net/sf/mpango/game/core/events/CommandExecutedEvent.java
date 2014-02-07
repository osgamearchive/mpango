package net.sf.mpango.game.core.events;

import net.sf.mpango.game.core.commands.Command;

/**
 * Exception that represents the successful execution of a command.
 * @author edvera
 *
 */
public class CommandExecutedEvent<T extends Command> extends CommandEvent<T> {

	/**
	 * Constructor
	 * @param command
	 */
	public CommandExecutedEvent(final T command) {
		super(command);
	}
	
	private static final long serialVersionUID = 1323363565613127060L;
}
