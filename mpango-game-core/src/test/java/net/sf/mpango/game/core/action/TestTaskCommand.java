package net.sf.mpango.game.core.action;

import java.util.Timer;

import net.sf.mpango.game.core.action.AbstractTaskCommand;
import net.sf.mpango.game.core.events.CommandExecutedEvent;
import net.sf.mpango.game.core.events.Listener;
import net.sf.mpango.game.core.exception.CommandException;

public class TestTaskCommand extends AbstractTaskCommand  {
	private String name;
	public TestTaskCommand(Timer timer, String name, Listener... listeners) {
		super(timer, listeners);
		this.name = name;
	}

	private boolean executed;

	@Override
	public CommandExecutedEvent runExecute() {
		this.executed = true;
		System.out.println("Executed: "+this.name+":"+this);
		return new CommandExecutedEvent(this);
	}
	
	public synchronized boolean isExecuted() {
		return executed;
	}

	@Override
	public void evaluateExecution() throws CommandException {
		
	}

	@Override
	public long calculateTotalTimeMillis(long timeMillisPerTimeSlice) {
		return timeMillisPerTimeSlice;
	}

	@Override
	public int calculateTotalTimeSlices() {
		return 1;
	}
	
}
