package net.sf.mpango.game.core.commands;

import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.Assert;
import net.sf.mpango.game.core.events.CommandExecutedEvent;
import net.sf.mpango.game.core.events.Observer;
import net.sf.mpango.game.core.exception.CommandException;
import net.sf.mpango.game.core.exception.EventNotSupportedException;
import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TaskCommandTest {

	private static long MILLIS_PER_SLICE = 1;
	private TestTaskCommand taskCommand;
	private Timer timer;
	private Observer observer;
    private ExecutorService executorService;

    @Before
	public void setUp() {
        executorService = Executors.newSingleThreadExecutor();
        timer = new Timer();
		observer = EasyMock.createMock(Observer.class);
		taskCommand = new TestTaskCommand(MILLIS_PER_SLICE, executorService, observer);
	}

    @After
    public void tearDown() {
        executorService = null;
        timer = null;
        observer = null;
        taskCommand = null;
    }
	
	@Test
	public void testScheduledExecution() throws CommandException, EventNotSupportedException, InterruptedException {
        assert taskCommand.executed == false;

		observer.observe(EasyMock.isA(CommandExecutedEvent.class));
		EasyMock.replay(observer);
		taskCommand.call();
		Assert.assertTrue("The task must have been executed", taskCommand.executed);
		EasyMock.verify(observer);
	}
	
	@Test
	public void testRun() throws EventNotSupportedException {
		observer.observe(EasyMock.isA(CommandExecutedEvent.class));
		EasyMock.replay(observer);
		taskCommand.call();
		Assert.assertTrue("The task must have been executed.", taskCommand.executed);
		EasyMock.verify(observer);
	}
	
	public class TestTaskCommand extends AbstractTaskCommand {

		private boolean executed; //To prove the command has been executed.
		
		protected TestTaskCommand(long millisPerSlice, ExecutorService executorService, Observer... observers) {
			super(millisPerSlice, executorService, observers);
			executed = false;
		}

		@Override
		public void evaluateExecution() throws CommandException {
			
		}

		@Override
		public CommandExecutedEvent execute() {
			executed = true;
			return new CommandExecutedEvent(this);
		}

		@Override
		public int calculateTotalTimeSlices() {
			return 5;
		}
		
	}
}
