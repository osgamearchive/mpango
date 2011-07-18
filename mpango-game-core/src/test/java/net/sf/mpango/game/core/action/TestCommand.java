package net.sf.mpango.game.core.action;

import java.util.Arrays;
import java.util.List;

import net.sf.mpango.game.core.action.Command;
import net.sf.mpango.game.core.events.CommandExecutedEvent;
import net.sf.mpango.game.core.events.Event;
import net.sf.mpango.game.core.events.Listener;
import net.sf.mpango.game.core.exception.CommandException;
import net.sf.mpango.game.core.exception.EventNotSupportedException;

public class TestCommand implements Command {
	public boolean executed;
	List<Listener> listeners;
	
	public TestCommand(Listener...listeners) {
		this.listeners = Arrays.asList(listeners);
		this.executed = false;
	}
	@Override
	public void execute() throws CommandException {
		executed = true;
		notifyListeners(new CommandExecutedEvent(this));
	}
	
	public boolean isExecuted() {
		return executed;
	}
	public void removeListener(Listener listener) {
	}
	public void addListener(Listener listener) {
	}
	@Override
	public void notifyListeners(Event event) {
		for (Listener listener : listeners) {
			try {
				listener.receive(event);
			} catch (EventNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}
}
