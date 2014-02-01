package net.sf.mpango.game.core.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import net.sf.mpango.game.core.action.AbstractTaskCommand;
import net.sf.mpango.game.core.action.ConstructCommand;
import net.sf.mpango.game.core.action.TestCommand;
import net.sf.mpango.game.core.action.TestTaskCommand;
import net.sf.mpango.game.core.exception.CommandException;
import net.sf.mpango.game.core.exception.UnknownTechnologyException;

/**
 * This class is named after the entity Unit and for it's nature as a test.
 * @author edvera
 *
 */
public class UnitTest extends TestCase {

	private Unit unit;
	
	@Before
	public void setUp() {
		unit = new TestUnit();
	}
	
	@After
	public void tearDown() {}
	
	public void testEffectiveAttackPoints() throws UnknownTechnologyException {
		Float expectedAttackPoints = TestUnit.UNIT_ATTACK_POINTS * TestWeaponTechnology.WEAPON_ATTACK_POINTS * unit.getHealth();
		assertEquals("The unit should have the expected attack points", expectedAttackPoints, unit.getEffectiveAttackPoints());
	}
	
	public void testSurviving() throws UnknownTechnologyException {
		Float attackPoints = 19f;
		unit.receiveDamage(attackPoints);
		assertFalse(unit.isDead());
		assertEquals("The hit points left should be the expected", TestUnit.UNIT_HIT_POINTS + TestUnit.SHIELD_HIT_POINTS - attackPoints, unit.getHitPoints());
	}
	
	public void testKilling() throws UnknownTechnologyException {
		Float attackPoints = 20f;
		unit.receiveDamage(attackPoints);
		assertTrue(unit.isDead());
		assertEquals("The hit points left should be the expected", TestUnit.UNIT_HIT_POINTS + TestUnit.SHIELD_HIT_POINTS - attackPoints, unit.getHitPoints());
	}
	
	public void testWeapon() throws UnknownTechnologyException {
		assertNotNull("Since we have provided a weapon technology during construction, the unit should have a weapon", unit.getWeapon());
	}
	
	public void testDamageShield() throws UnknownTechnologyException {
		assertNotNull("Since we have provided a shield technology during construction, the unit should have a shield", unit.getShield());
		assertEquals("The shield should be initialized with the expected hit points", TestUnit.SHIELD_HIT_POINTS, unit.getShield().getRemainingHitPoints());
		unit.receiveDamage(1f);
		assertNotNull("The shield should have been capable of surviving the attack", unit.getShield());
		assertEquals("The should have the exact remaining hit points", TestShieldTechnology.MAXIMUM_SHIELD_HIT_POINTS, TestUnit.SHIELD_HIT_POINTS - 1f, unit.getShield().getRemainingHitPoints());
	}
	
	public void testDestroyShield() throws UnknownTechnologyException {
		assertNotNull("Since we have provided a shield technology during construction, the unit should have a shield", unit.getShield());
		assertEquals("The shield should be initialized with the expected hit points", TestUnit.SHIELD_HIT_POINTS, unit.getShield().getRemainingHitPoints());
		unit.receiveDamage(TestUnit.UNIT_HIT_POINTS);
		assertNull("The shield should have been destroyed during the attack", unit.getShield());
		
	}
	
	public void testAddFirstCommand() throws CommandException {
		TestCommand command = new TestCommand();
		unit.addCommand(command);
		assertTrue(command.isExecuted());
	}
	
	public void testExecuteTaskCommand() throws CommandException, InterruptedException {
		TestTaskCommand command = new TestTaskCommand(unit.getTimer(),"");
		unit.addCommand(command);
		assertTrue("The command should not be executed instantly", !command.isExecuted());
		Thread.sleep(command.calculateTotalTimeMillis(1000)+1);
		assertTrue("The command should have been executed by now", command.isExecuted());
		
	}
	
	public void testAddSecondCommand() throws CommandException {
		TestCommand command1 = new TestCommand(unit);
		unit.addCommand(command1);
		assertTrue(command1.isExecuted());
		TestCommand command2 = new TestCommand(unit);
		unit.addCommand(command2);
		assertTrue(command2.isExecuted());
	}
	
	public void testAddSecondCommandAfterCommandTask() throws CommandException, InterruptedException {
		TestTaskCommand command1 = new TestTaskCommand(unit.getTimer(),"3", unit);
		TestCommand command2 = new TestCommand(unit);
		unit.addCommand(command1);
		unit.addCommand(command2);
		assertFalse("Command should not have executed yet", command2.executed);
		Thread.sleep(AbstractTaskCommand.DEFAULT_MILLIS_PER_TIME_SLICE+100);
		assertTrue("Command should have executed already", command2.executed);
	}
	
	public void testAddSecondTaskCommand() throws CommandException, InterruptedException {
		TestTaskCommand command1 = new TestTaskCommand(unit.getTimer(),"1", unit);
		TestTaskCommand command2 = new TestTaskCommand(unit.getTimer(),"2", unit);
		unit.addCommand(command1);
		unit.addCommand(command2);
		assertTrue("The first command should not be executed instantly", !command1.isExecuted());
		while (!command1.isExecuted()) {
			Thread.sleep(AbstractTaskCommand.DEFAULT_MILLIS_PER_TIME_SLICE);
			System.out.print(".");
		}
		assertTrue("The first command should have been executed by now", command1.isExecuted());
		assertTrue("The second command should not be executed instantly", !command2.isExecuted());
		while(!command2.isExecuted()) {
			Thread.sleep(AbstractTaskCommand.DEFAULT_MILLIS_PER_TIME_SLICE);
			System.out.print(".");
		}
		assertTrue("The second command should have been executed by now", command2.isExecuted());
	}
	
	@Test
	public void testSettle() throws UnknownTechnologyException, CommandException, InterruptedException {
		City city = new City();
		
		// create a city
		Cell cell = new Cell(0, 0);
		ConstructCommand command = unit.settle(cell);
		Thread.sleep(command.calculateTotalTimeMillis(AbstractTaskCommand.DEFAULT_MILLIS_PER_TIME_SLICE)+100);
		assertTrue("The cell must contain a city", cell.getConstructions().contains(city));
	}
}
