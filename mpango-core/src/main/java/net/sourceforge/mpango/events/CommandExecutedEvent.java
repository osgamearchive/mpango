package net.sourceforge.mpango.events;

import net.sourceforge.mpango.actions.Command;

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
