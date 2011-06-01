package net.sourceforge.mpango.actions;

import java.util.Timer;

import junit.framework.Assert;

import net.sourceforge.mpango.events.CommandExecutedEvent;
import net.sourceforge.mpango.events.Listener;
import net.sourceforge.mpango.exception.CommandException;
import net.sourceforge.mpango.exception.EventNotSupportedException;

import org.easymock.classextension.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class TaskCommandTest {

	private static long MILLIS_PER_SLICE = 1;
	private TestTaskCommand taskCommand;
	private Timer timer;
	private Listener listener;
	
	@Before
	public void setUp() {
		timer = new Timer();
		listener = EasyMock.createMock(Listener.class);
		taskCommand = new TestTaskCommand(MILLIS_PER_SLICE,timer, listener);
	}
	
	@Test
	public void testScheduledExecution() throws CommandException, EventNotSupportedException, InterruptedException {
		listener.receiveEvent(EasyMock.isA(CommandExecutedEvent.class));
		EasyMock.replay(listener);
		int numberOfThreadPriorExecution = Thread.activeCount();
		taskCommand.execute();
		Assert.assertFalse("The task must not have been executed", taskCommand.executed);
		Thread.sleep(taskCommand.calculateTotalTimeMillis(1)+1); //Give time to the thread to execute.
		Assert.assertEquals(numberOfThreadPriorExecution, Thread.activeCount());
		Assert.assertTrue("The task must have been executed", taskCommand.executed);
		EasyMock.verify(listener);
	}
	
	@Test
	public void testRun() throws EventNotSupportedException {
		CommandExecutedEvent event = new CommandExecutedEvent(taskCommand);
		listener.receiveEvent(EasyMock.isA(event.getClass()));
		EasyMock.replay(listener);
		taskCommand.run();
		Assert.assertTrue("The task must have been executed.",taskCommand.executed);
		EasyMock.verify(listener);
	}
	
	public class TestTaskCommand extends TaskCommand {

		public boolean executed; //To prove the command has been executed.
		
		protected TestTaskCommand(long millisPerSlice, Timer timer, Listener...listeners) {
			super(millisPerSlice, timer,listeners);
			executed = false;
		}

		@Override
		protected void evaluateExecution() throws CommandException {
			
		}

		@Override
		protected void runExecute() {
			executed = true;
		}

		@Override
		public long calculateTotalTimeMillis(long timeMillisPerTimeSlice) {
			return 10;
		}
		
	}
}
