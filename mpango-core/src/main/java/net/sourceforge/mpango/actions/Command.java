package net.sourceforge.mpango.actions;

import net.sourceforge.mpango.events.Listener;
import net.sourceforge.mpango.exception.CommandException;

public interface Command {

	void execute() throws CommandException;

	void removeListener(Listener listener);

	void addListener(Listener listener);
}
