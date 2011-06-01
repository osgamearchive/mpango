package net.sourceforge.mpango.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import net.sourceforge.mpango.actions.Command;
import net.sourceforge.mpango.actions.ConstructCommand;
import net.sourceforge.mpango.actions.TaskCommand;
import net.sourceforge.mpango.entity.Cell;
import net.sourceforge.mpango.entity.City;
import net.sourceforge.mpango.entity.Shield;
import net.sourceforge.mpango.entity.Technology;
import net.sourceforge.mpango.entity.Unit;
import net.sourceforge.mpango.entity.Weapon;
import net.sourceforge.mpango.entity.technology.ShieldTechnology;
import net.sourceforge.mpango.entity.technology.WeaponTechnology;
import net.sourceforge.mpango.events.CommandExecutedEvent;
import net.sourceforge.mpango.events.Listener;
import net.sourceforge.mpango.exception.CommandException;
import net.sourceforge.mpango.exception.ConstructionAlreadyInPlaceException;
import net.sourceforge.mpango.exception.EventNotSupportedException;
import net.sourceforge.mpango.exception.UnknownTechnologyException;

/**
 * This class is named after the entity Unit and for it's nature as a test.
 * @author etux
 *
 */
public class UnitTest extends TestCase {

	private static final Float SHIELD_HIT_POINTS = 5f;
	private static final Float WEAPON_ATTACK_POINTS = 3f;
	private static final Float UNIT_ATTACK_POINTS = 10f;
	private static final Float UNIT_HIT_POINTS = 15f;
	private static final Float MAXIMUM_SHIELD_HIT_POINTS = 5f;
	
	@Before
	public void setUp() {
		
	}
	
	@After
	public void tearDown() {
	}
	
	public void testEffectiveAttackPoints() throws UnknownTechnologyException {
		Unit unit = new TestUnit ();
		Float expectedAttackPoints = UNIT_ATTACK_POINTS * WEAPON_ATTACK_POINTS * unit.getHealth();
		assertEquals("The unit should have the expected attack points", expectedAttackPoints, unit.getEffectiveAttackPoints());
	}
	
	public void testSurviving() throws UnknownTechnologyException {
		Unit unit = new TestUnit ();
		Float attackPoints = 19f;
		unit.receiveDamage(attackPoints);
		assertFalse(unit.isDead());
		assertEquals("The hit points left should be the expected", UNIT_HIT_POINTS + SHIELD_HIT_POINTS - attackPoints, unit.getHitPoints());
	}
	
	public void testKilling() throws UnknownTechnologyException {
		Unit unit = new TestUnit ();
		Float attackPoints = 20f;
		unit.receiveDamage(attackPoints);
		assertTrue(unit.isDead());
		assertEquals("The hit points left should be the expected", UNIT_HIT_POINTS + SHIELD_HIT_POINTS - attackPoints, unit.getHitPoints());
	}
	
	public void testWeapon() throws UnknownTechnologyException {
		Unit unit = new TestUnit ();
		assertNotNull("Since we have provided a weapon technology during construction, the unit should have a weapon", unit.getWeapon());
	}
	
	public void testDamageShield() throws UnknownTechnologyException {
		Unit unit = new TestUnit ();
		assertNotNull("Since we have provided a shield technology during construction, the unit should have a shield", unit.getShield());
		assertEquals("The shield should be initialized with the expected hit points", SHIELD_HIT_POINTS, unit.getShield().getRemainingHitPoints());
		unit.receiveDamage(1f);
		assertNotNull("The shield should have been capable of surviving the attack", unit.getShield());
		assertEquals("The should have the exact remaining hit points", MAXIMUM_SHIELD_HIT_POINTS - 1f, unit.getShield().getRemainingHitPoints());
	}
	
	public void testDestroyShield() throws UnknownTechnologyException {
		Unit unit = new TestUnit();
		assertNotNull("Since we have provided a shield technology during construction, the unit should have a shield", unit.getShield());
		assertEquals("The shield should be initialized with the expected hit points", SHIELD_HIT_POINTS, unit.getShield().getRemainingHitPoints());
		unit.receiveDamage(UNIT_HIT_POINTS);
		assertNull("The shield should have been destroyed during the attack", unit.getShield());
		
	}
	
	public void testAddFirstCommand() throws CommandException {
		Unit unit = new TestUnit();
		TestCommand command = new TestCommand();
		unit.addCommand(command);
		assertTrue(command.isExecuted());
	}
	
	public void testExecuteTaskCommand() throws CommandException, InterruptedException {
		Unit unit = new TestUnit();
		TestTaskCommand command = new TestTaskCommand(unit.getTimer(),"");
		unit.addCommand(command);
		assertTrue("The command should not be executed instantly", !command.isExecuted());
		Thread.sleep(command.calculateTotalTimeMillis(1000)+1);
		assertTrue("The command should have been executed by now", command.isExecuted());
		
	}
	
	public void testAddSecondCommand() throws CommandException {
		Unit unit = new TestUnit();
		TestCommand command1 = new TestCommand(unit);
		unit.addCommand(command1);
		assertTrue(command1.isExecuted());
		TestCommand command2 = new TestCommand(unit);
		unit.addCommand(command2);
		assertTrue(command2.isExecuted());
	}
	
	public void testAddSecondCommandAfterCommandTask() throws CommandException, InterruptedException {
		Unit unit = new TestUnit();
		TestTaskCommand command1 = new TestTaskCommand(unit.getTimer(),"3", unit);
		TestCommand command2 = new TestCommand(unit);
		unit.addCommand(command1);
		unit.addCommand(command2);
		assertFalse("Command should not have executed yet", command2.executed);
		Thread.sleep(TaskCommand.DEFAULT_MILLIS_PER_TIME_SLICE+100);
		assertTrue("Command should have executed already", command2.executed);
	}
	
	public void testAddSecondTaskCommand() throws CommandException, InterruptedException {
		Unit unit = new TestUnit();
		TestTaskCommand command1 = new TestTaskCommand(unit.getTimer(),"1", unit);
		TestTaskCommand command2 = new TestTaskCommand(unit.getTimer(),"2", unit);
		unit.addCommand(command1);
		unit.addCommand(command2);
		assertTrue("The first command should not be executed instantly", !command1.isExecuted());
		while (!command1.isExecuted()) {
			Thread.sleep(TaskCommand.DEFAULT_MILLIS_PER_TIME_SLICE);
			System.out.print(".");
		}
		assertTrue("The first command should have been executed by now", command1.isExecuted());
		assertTrue("The second command should not be executed instantly", !command2.isExecuted());
		while(!command2.isExecuted()) {
			Thread.sleep(TaskCommand.DEFAULT_MILLIS_PER_TIME_SLICE);
			System.out.print(".");
		}
		assertTrue("The second command should have been executed by now", command2.isExecuted());
	}
	
	@Test
	public void testSettle() throws UnknownTechnologyException, CommandException, InterruptedException {
		Unit unit = new TestUnit();
		City city = new City();
		
		// create a city
		Cell cell = new Cell(0, 0);
		ConstructCommand command = unit.settle(cell);
		Thread.sleep(command.calculateTotalTimeMillis(TaskCommand.DEFAULT_MILLIS_PER_TIME_SLICE)+100);
		assertTrue("The cell must contain a city", cell.getConstructions().contains(city));
	}
	
	private List<Technology> createTechnologies() {
		List<Technology> technologies = new ArrayList<Technology>();
		technologies.add(new TestShieldTechnology());
		technologies.add(new TestWeaponTechnology());
		return technologies;
	}
	
	public class TestWeaponTechnology extends WeaponTechnology {
		public TestWeaponTechnology() {}
		public Weapon createWeapon() {
			return new Weapon(WEAPON_ATTACK_POINTS);
		}
		public List<Technology> getRequiredTechnologies() {
			// TODO Auto-generated method stub
			return null;
		}
		public Integer getTechnologyCost() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	public class TestShieldTechnology extends ShieldTechnology {
		public TestShieldTechnology() {}
		public Shield createShield() {
			return new Shield(MAXIMUM_SHIELD_HIT_POINTS);
		}
		public List<Technology> getRequiredTechnologies() {
			// TODO Auto-generated method stub
			return null;
		}
		public Integer getTechnologyCost() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	public class TestUnit extends Unit {
		
		public TestUnit() {
			super(new ArrayList<Command>(), createTechnologies(), UNIT_ATTACK_POINTS, UNIT_HIT_POINTS);
		}
		private static final long serialVersionUID = 1L;
		public float repair() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
	
	public class TestCommand implements Command {
		private boolean executed;
		private List<Listener> listeners;
		
		public TestCommand(Listener...listeners) {
			this.listeners = Arrays.asList(listeners);
			this.executed = false;
		}
		@Override
		public void execute() throws CommandException {
			executed = true;
			for (Listener listener : listeners) {
				try {
					listener.receiveEvent(new CommandExecutedEvent(this));
				} catch (EventNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public boolean isExecuted() {
			return executed;
		}
		public void removeListener(Listener listener) {
		}
		public void addListener(Listener listener) {
		}
	}
	
	public class TestTaskCommand extends TaskCommand  {
		private String name;
		public TestTaskCommand(Timer timer, String name, Listener... listeners) {
			super(timer, listeners);
			this.name = name;
		}

		private boolean executed;

		@Override
		public void runExecute() {
			this.executed = true;
			System.out.println("Executed: "+this.name+":"+this);
		}
		
		public synchronized boolean isExecuted() {
			return executed;
		}

		@Override
		protected void evaluateExecution() throws CommandException {
			
		}

		@Override
		public long calculateTotalTimeMillis(long timeMillisPerTimeSlice) {
			return timeMillisPerTimeSlice;
		}
		
	}
}
