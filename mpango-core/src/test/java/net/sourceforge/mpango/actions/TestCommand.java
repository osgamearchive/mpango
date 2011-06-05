package net.sourceforge.mpango.actions;

import java.util.Arrays;
import java.util.List;

import net.sourceforge.mpango.events.CommandExecutedEvent;
import net.sourceforge.mpango.events.Event;
import net.sourceforge.mpango.events.Listener;
import net.sourceforge.mpango.exception.CommandException;
import net.sourceforge.mpango.exception.EventNotSupportedException;

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
