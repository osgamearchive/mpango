package net.sourceforge.mpango.turn;

import com.sun.tools.internal.ws.processor.model.java.JavaArrayType;
import net.sourceforge.mpango.actions.AbstractTaskCommand;
import net.sourceforge.mpango.actions.Command;
import net.sourceforge.mpango.actions.ITaskCommand;
import net.sourceforge.mpango.entity.Unit;
import net.sourceforge.mpango.exception.CommandException;

import java.util.Date;
import java.util.HashSet;

public class Timer {

    private java.util.Timer internalTimer;
    // Contains the commands to be executed timer after time.
    private HashSet<Command> commands;
	
	public Timer () {
        this.internalTimer = new java.util.Timer();
        this.commands = new HashSet<Command>();
	}

    /**
	 * <p>Method that adds a command to the command queue. If it is the first command, it also executes it after having it added.</p>
	 * @param command Command to be added and possibly executed.
	 * @throws net.sourceforge.mpango.exception.CommandException In case there is a problem with the execution of the command.
	 */
	public void addCommand(Command command) throws CommandException {
		boolean executeCommand = false;
		if (commands.size() == 0)
			executeCommand = true;
		this.commands.add(command);
		if (executeCommand)
			executeCommand(command);
	}

	/**
	 * <p>Method that executes a given command</p>
	 * @param command to be executed
	 * @throws CommandException In case there is a problem with the execution of the command.
	 */
	private void executeCommand(Command command) throws CommandException {
		command.execute();
	}
	/**
	 * <p>Method that removes a command. This can happen in two cases:
	 * <ol>
	 * 	<li>The user decides to cancel a specific command</li>
	 *  <li>The command has been successfully executed and must be removed</li>
	 * <oL>
	 * </p>
	 * <p>This method is also responsible for executing the next available command.</p>
	 * @param command to be removed.
	 * @throws CommandException In case there is a problem with the removal of the command.
	 */
	public void removeCommand(Command command) throws CommandException {
		System.out.println("Removing command: "+command);
		this.commands.remove(command);
		if (commands.iterator().hasNext()) {
			executeCommand(commands.iterator().next());
		}
	}

    public int getCommandSize() {
        return this.commands.size();
    }

    public HashSet<Command> getCommands() {
        return commands;
    }

    public void setCommands(HashSet<Command> commands) {
        this.commands = commands;
    }

    public java.util.Timer getInternalTimer() {
        return internalTimer;
    }
}
