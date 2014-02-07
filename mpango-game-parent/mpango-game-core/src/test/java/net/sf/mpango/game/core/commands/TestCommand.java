package net.sf.mpango.game.core.commands;

import net.sf.mpango.game.core.events.CommandEvent;
import net.sf.mpango.game.core.events.CommandExecutedEvent;
import net.sf.mpango.game.core.exception.CommandException;

public class TestCommand extends AbstractCommand<CommandEvent> {

	private boolean executed;
	
	public TestCommand() {
		this.executed = false;
	}
	@Override
	public CommandEvent execute() throws CommandException {
        executed = true;
        final CommandEvent commandEvent = new CommandExecutedEvent(this);
        return commandEvent;
	}

    public boolean isExecuted() {
        return executed;
	}
}
