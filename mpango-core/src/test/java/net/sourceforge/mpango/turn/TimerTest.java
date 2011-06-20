package net.sourceforge.mpango.turn;

import com.sun.tools.corba.se.idl.constExpr.Times;
import net.sourceforge.mpango.actions.AbstractTaskCommand;
import net.sourceforge.mpango.actions.Command;
import net.sourceforge.mpango.events.CommandExecutedEvent;
import net.sourceforge.mpango.exception.CommandException;
import org.easymock.classextension.EasyMock;
import static org.easymock.classextension.EasyMock.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: etux
 * Date: 6/13/11
 * Time: 1:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class TimerTest {

    private Timer timer;

    @Before
    public void setUp() {
        timer = new Timer();
    }

    @Test
    public void testAddCommand() throws CommandException {
        Command command = EasyMock.createMock(Command.class);
        command.execute();
        replay(command);
        timer.addCommand(command);
        assertEquals(timer.getCommandSize(),1);
        timer.addCommand(command);
        assertEquals(timer.getCommandSize(), 1);
        AbstractTaskCommand command2 = EasyMock.createMock(AbstractTaskCommand.class);
        timer.addCommand(command2);
        assertEquals(timer.getCommandSize(), 2);
    }

    @Test
    public void testAddAbstractTaskCommand() throws CommandException {

        AbstractTaskCommand command = new AbstractTaskCommand (100l, timer.getInternalTimer()) {
            public boolean executed = false;
            @Override
            public int calculateTotalTimeSlices() {
                return 1;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public CommandExecutedEvent runExecute() {
                executed = true;
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void evaluateExecution() throws CommandException {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        timer.addCommand(command);
    }
}
