package net.sourceforge.mpango.events;

import net.sourceforge.mpango.actions.Command;

/**
 * Exception that represents the successful execution of a command.
 * @author edvera
 *
 */
public class CommandExecutedEvent implements Event {

	private static final long serialVersionUID = 1323363565613127060L;
	private Command command;
	
	public CommandExecutedEvent(Command command) {
		this.command = command;
	}
	
	public Command getCommand() {
		return command;
	}
	
	@Override
	public Object getSource() {
		return command;
	}

}
