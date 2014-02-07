package net.sf.mpango.game.core.turn;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import net.sf.mpango.game.core.commands.AbstractTaskCommand;
import net.sf.mpango.game.core.commands.Command;
import net.sf.mpango.game.core.events.CommandEvent;
import net.sf.mpango.game.core.exception.CommandException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * User: etux
 */
public class TimerTest {

    private ExecutorService executorService;
    @Mock
    private Command<CommandEvent> mockCommand;
    @Mock
    private CommandEvent mockCommandEvent;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        executorService = Executors.newSingleThreadExecutor();
    }

    @Test
    public void testAddCommand() throws Exception {
        FutureTask<CommandEvent> task = new FutureTask<>(mockCommand);
        when(mockCommand.call()).thenReturn(mockCommandEvent);
        executorService.execute(task);

        final CommandEvent result = task.get();

        assertThat("The command event should be the expected one", result, is(equalTo(mockCommandEvent)));
        verify(mockCommand).call();
    }

    @Test
    public void testAddAbstractTaskCommand() throws ExecutionException, InterruptedException {

        final CommandEvent mockCommandEvent = mock(CommandEvent.class);

        AbstractTaskCommand<CommandEvent> command = new AbstractTaskCommand (100l, executorService) {
//            private boolean executed = false;
            @Override
            public int calculateTotalTimeSlices() {
                return 1;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public CommandEvent execute() {
//                executed = true;
                return mockCommandEvent;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void evaluateExecution() throws CommandException {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        FutureTask<CommandEvent> task = new FutureTask<>(command);
        executorService.execute(task);

        final CommandEvent result = task.get();

        assertThat(result, is(equalTo(mockCommandEvent)));
    }
}
