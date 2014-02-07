package net.sf.mpango.game.core.entity;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.sf.mpango.game.core.commands.TestCommand;
import net.sf.mpango.game.core.commands.TestTaskCommand;
import net.sf.mpango.game.core.enums.ConstructionType;
import net.sf.mpango.game.core.events.CommandEvent;
import net.sf.mpango.game.core.exception.CommandException;
import net.sf.mpango.game.core.exception.UnknownTechnologyException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class is named after the entity Unit and for it's nature as a test.
 * @author edvera
 *
 */
public class UnitTest {

	private Unit unit;
    private Thread thread;
	
	@Before
	public void setUp() {
		unit = new TestUnit();
        thread = new Thread(unit);
        thread.start();
	}
	
	@After
	public void tearDown() {
        unit.kill();
    }

    @Test
	public void testEffectiveAttackPoints() throws UnknownTechnologyException {
		float expectedAttackPoints = TestUnit.UNIT_ATTACK_POINTS * TestWeaponTechnology.WEAPON_ATTACK_POINTS * unit.getHealth();
        assertThat("The unit should have the expected attack points", unit.getEffectiveAttackPoints(), is(equalTo(expectedAttackPoints)));
	}
    @Test
	public void testSurviving() throws UnknownTechnologyException {
        float attackPoints = 19f;
        Unit attackingUnit = mock(Unit.class);
        when(attackingUnit.getEffectiveAttackPoints()).thenReturn(attackPoints);
		unit.receiveDamage(attackingUnit, 0, 0);
        assertThat("The unit should be alive", unit.isDead(), is(Boolean.FALSE));
        assertThat("The hit points left should be the expected", unit.getHitPoints(), is(equalTo(TestUnit.UNIT_HIT_POINTS + TestUnit.SHIELD_HIT_POINTS - attackPoints)));
	}
    @Test
	public void testKilling() throws UnknownTechnologyException {
        float attackPoints = 19f;
        Unit attackingUnit = mock(Unit.class);
        when(attackingUnit.getEffectiveAttackPoints()).thenReturn(attackPoints);
		unit.receiveDamage(attackingUnit, 0, 0);
        assertThat("The unit should be dead by now", unit.isDead(), is(Boolean.TRUE));
        assertThat("The hit points left should be the expected", unit.getHitPoints(), is(equalTo(TestUnit.UNIT_HIT_POINTS + TestUnit.SHIELD_HIT_POINTS - attackPoints)));
	}
    @Test
	public void testWeapon() throws UnknownTechnologyException {
        Assert.assertNotNull("Since we have provided a weapon technology during construction, the unit should have a weapon", unit.getWeapon());
	}
    @Test
	public void testDamageShield() throws UnknownTechnologyException {
        Assert.assertNotNull("Since we have provided a shield technology during construction, the unit should have a shield", unit.getShield());
        assertThat("The shield should be initialized with the expected hit points", TestUnit.SHIELD_HIT_POINTS, is(equalTo(unit.getShield().getRemainingHitPoints())));
        Unit attackingUnit = mock(Unit.class);
        when(attackingUnit.getEffectiveAttackPoints()).thenReturn(1f);
        unit.receiveDamage(attackingUnit, 0, 0);
        Assert.assertNotNull("The shield should have been capable of surviving the attack", unit.getShield());
        Assert.assertEquals("The should have the exact remaining hit points", TestShieldTechnology.MAXIMUM_SHIELD_HIT_POINTS, TestUnit.SHIELD_HIT_POINTS - 1f, unit.getShield().getRemainingHitPoints());
	}
    @Test
	public void testDestroyShield() throws UnknownTechnologyException {
        Assert.assertNotNull("Since we have provided a shield technology during construction, the unit should have a shield", unit.getShield());
        assertThat("The shield should be initialized with the expected hit points", TestUnit.SHIELD_HIT_POINTS, is(equalTo(unit.getShield().getRemainingHitPoints())));
        Unit attackingUnit = mock(Unit.class);
        when(attackingUnit.getEffectiveAttackPoints()).thenReturn(TestUnit.UNIT_HIT_POINTS);
		unit.receiveDamage(attackingUnit, 0, 0);
        Assert.assertNull("The shield should have been destroyed during the attack", unit.getShield());
		
	}
    @Test
	public void testAddFirstCommand() throws CommandException, InterruptedException, ExecutionException {
		final TestCommand command = new TestCommand();
		final Future<CommandEvent<TestCommand>> result = unit.addCommand(command);
        final CommandEvent<TestCommand> event = result.get();
        assertThat("The event should not be null", event, is(notNullValue()));
        assertThat(command.isExecuted(), is(Boolean.TRUE));
        assertThat(event.getCommand(), is(equalTo(command)));
	}
    @Test
	public void testExecuteTaskCommand() throws CommandException, InterruptedException, ExecutionException {
		TestTaskCommand command = new TestTaskCommand(Unit.EXECUTOR_SERVICE, "");
		Future<CommandEvent> futureEvent = unit.addCommand(command);
        Assert.assertTrue("The command should not be executed instantly", !command.isExecuted());
		futureEvent.get();
        Assert.assertTrue("The command should have been executed by now", command.isExecuted());
		
	}
    @Test
	public void testAddSecondCommand() throws CommandException, InterruptedException, ExecutionException {
		TestCommand command1 = new TestCommand();
		Future<CommandEvent> futureEvent = unit.addCommand(command1);
        futureEvent.get();
        assertThat(command1.isExecuted(), is(Boolean.TRUE));
		TestCommand command2 = new TestCommand();
		futureEvent = unit.addCommand(command2);
        futureEvent.get();
        assertThat(command2.isExecuted(), is(Boolean.TRUE));
	}
    @Test
	public void testAddSecondCommandAfterCommandTask() throws CommandException, InterruptedException {
		TestTaskCommand command1 = new TestTaskCommand(Unit.EXECUTOR_SERVICE, "testAddSecondCommandAfterCommandTask", unit);
		TestCommand command2 = new TestCommand();
		unit.addCommand(command1);
		unit.addCommand(command2);
        Assert.assertFalse("Command should not have executed yet", command1.isExecuted());
        Assert.assertFalse("Command should not have executed yet", command2.isExecuted());
		Thread.sleep(command1.getTotalDuration() + 1000);
        Assert.assertTrue("Command should have executed already", command2.isExecuted());
	}
    @Test
	public void testAddSecondTaskCommand() throws CommandException, InterruptedException, ExecutionException {
		TestTaskCommand command1 = new TestTaskCommand(Unit.EXECUTOR_SERVICE, "1", unit);
		TestTaskCommand command2 = new TestTaskCommand(Unit.EXECUTOR_SERVICE, "2", unit);
		Future<CommandEvent> futureEvent1 = unit.addCommand(command1);
		Future<CommandEvent> futureEvent2 = unit.addCommand(command2);
        Assert.assertTrue("The first command should not be executed instantly", !command1.isExecuted());
		futureEvent1.get();
        Assert.assertTrue("The first command should have been executed by now", command1.isExecuted());
        Assert.assertTrue("The second command should not be executed instantly", !command2.isExecuted());
		futureEvent2.get();
        Assert.assertTrue("The second command should have been executed by now", command2.isExecuted());
	}
	@Test
	public void testSettle() throws UnknownTechnologyException, CommandException, InterruptedException, ExecutionException {
		final Cell cell = new Cell(0, 0);
		Future<CommandEvent> futureEvent = unit.settle(cell);
        futureEvent.get();
        assertThat(cell.getConstructions().get(0).getType(), is(ConstructionType.CITY));
	}
}
