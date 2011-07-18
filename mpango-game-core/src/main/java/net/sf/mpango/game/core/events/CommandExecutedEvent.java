package net.sf.mpango.game.core.events;

import net.sf.mpango.game.core.action.Command;

/**
 * Exception that represents the successful execution of a command.
 * @author edvera
 *
 */
public class CommandExecutedEvent extends CommandEvent {

	/**
	 * Constructor
	 * @param command
	 */
	public CommandExecutedEvent(Command command) {
		super(command);
	}
	
	private static final long serialVersionUID = 1323363565613127060L;
}
