package net.sf.mpango.game.core.commands;

import java.util.concurrent.ExecutorService;

import net.sf.mpango.game.core.events.CommandExecutedEvent;
import net.sf.mpango.game.core.events.Observer;
import net.sf.mpango.game.core.exception.CommandException;

public class TestTaskCommand extends AbstractTaskCommand<CommandExecutedEvent>  {

	private String name;

	public TestTaskCommand(ExecutorService executorService, String name, Observer... observers) {
		super(1000, executorService, observers);
		this.name = name;
	}

	private volatile boolean executed;

	@Override
	public synchronized CommandExecutedEvent execute() {
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
		return 10;
	}
	
}
