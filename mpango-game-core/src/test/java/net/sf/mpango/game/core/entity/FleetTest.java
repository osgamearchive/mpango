package net.sf.mpango.game.core.entity;

import net.sf.mpango.game.core.battle.result.FightResult;
import net.sf.mpango.game.core.entity.Fleet;
import net.sf.mpango.game.core.entity.Official;
import net.sf.mpango.game.core.entity.Unit;
import net.sf.mpango.game.core.exception.InvalidUnitException;
import net.sf.mpango.game.core.exception.UnknownTechnologyException;
import junit.framework.TestCase;
import static org.easymock.classextension.EasyMock.*;

public class FleetTest extends TestCase {
	
	private static final float BASE_ATTACK_POINTS = 5;
	private static final float DEFENDING_BONUS = 0.5f;
	private static final float ATTACKING_BONUS = 0.25f;
	private static final Float ATTACKER_INITIAL_HITPOINTS = null;
	private static final Float DEFENDER_INITIAL_HITPOINTS = null;
	private static final Float ATTACKER_ENDING_HITPOINTS = null;
	private static final Float DEFENDER_ENDING_HITPOINTS = null;

	public void testAddUnit() throws InvalidUnitException, UnknownTechnologyException {
		Unit test = createMock(Unit.class);
		Fleet fleet = new Fleet();
		fleet.addUnit(test);
		fleet.removeUnit(test);
	}
	
	public void testRemoveUnexisting() {
		Unit unit = createMock(Unit.class);
		Fleet fleet = new Fleet();
		try {
			fleet.removeUnit(unit);
			fail("Expected exception not raised");
		} catch (InvalidUnitException expected) {
			// Expected
		}
	}
	
	public void testAddTwoOfficials() {
		Official official = createMock(Official.class);
		Official duplicatedOfficial = createMock(Official.class);
		Fleet fleet = new Fleet();
		try {
			fleet.addUnit(official);
			fleet.addUnit(duplicatedOfficial);
			fail("Expected Exception not raised");
		} catch (InvalidUnitException expected) {
			//Expected exception
		}
	}

	public void testRemoveUnit() throws InvalidUnitException {
		Fleet fleet = new Fleet();
		Unit unit = createMock(Unit.class);
		fleet.addUnit(unit);
		fleet.removeUnit(unit);
	}

	/**
	 * Test that demonstrates the result of attacking and empty fleet with an empty fleet
	 * 
	 */
	public void testReceiveAttackWithNoAttackingUnits() {
		Fleet defenders = new Fleet();
		Fleet attackers = new Fleet();
		FightResult result = defenders.fight(attackers, DEFENDING_BONUS, ATTACKING_BONUS);
		assertEquals("The defenders should be the winners", defenders, result.getWinners());
		assertEquals("The attackers should be the loosers", attackers, result.getLoosers());
	}
	/**
	 * Result of executing an attack with one attack unit and 0 defenders.
	 * @throws InvalidUnitException
	 */
	public void testReceiveAttackA1D0() throws InvalidUnitException {
		Fleet defenders = new Fleet();
		Fleet attackers = new Fleet();
		Unit attackUnit = createMock(Unit.class);
		attackers.addUnit(attackUnit);
		FightResult result = defenders.fight(attackers, 0, 0);
		assertEquals("The attackers should be the winners", attackers, result.getWinners());
		assertEquals("The defenders should be the loosers", defenders, result.getLoosers());
	}

	public void testReceiveAttackA1D1DW() throws InvalidUnitException {
		Fleet defenders = new Fleet();
		Fleet attackers = new Fleet();
		Unit attackerUnit = createMock(Unit.class);
		Unit defenderUnit = createMock(Unit.class);
		attackers.addUnit(attackerUnit);
		defenders.addUnit(defenderUnit);
		expect(attackerUnit.getHitPoints()).andReturn(ATTACKER_INITIAL_HITPOINTS);
		expect(defenderUnit.getHitPoints()).andReturn(DEFENDER_INITIAL_HITPOINTS);
		expect(attackerUnit.getEffectiveAttackPoints()).andReturn(BASE_ATTACK_POINTS);
		defenderUnit.receiveDamage(BASE_ATTACK_POINTS * (ATTACKING_BONUS / DEFENDING_BONUS));
		expect(defenderUnit.isDead()).andReturn(false);
		expect(defenderUnit.getEffectiveAttackPoints()).andReturn(BASE_ATTACK_POINTS);
		attackerUnit.receiveDamage(BASE_ATTACK_POINTS * (ATTACKING_BONUS / DEFENDING_BONUS));
		expect(attackerUnit.isDead()).andReturn(true);
		expect(attackerUnit.getHitPoints()).andReturn(ATTACKER_ENDING_HITPOINTS);
		expect(defenderUnit.getHitPoints()).andReturn(DEFENDER_ENDING_HITPOINTS);
		replay(attackerUnit);
		replay(defenderUnit);
		FightResult result = defenders.fight(attackers, DEFENDING_BONUS, ATTACKING_BONUS);
		assertEquals("The loosers must be the attackers", attackers, result.getLoosers());
		assertEquals("The winners must be the defenders", defenders, result.getWinners());
		assertEquals("There should only one fight to be taken place", 1, result.getAttackResults().size());
		verify(defenderUnit);
		verify(attackerUnit);
	}
	
	/**
	 * Attack demonstrating the behavior in case of an attacker win.
	 * @throws InvalidUnitException
	 */
	public void testReceiveAttackA1D1AW() throws InvalidUnitException {
		Fleet defenders = new Fleet();
		Fleet attackers = new Fleet();
		Unit attackUnit = createMock(Unit.class);
		Unit defenderUnit = createMock(Unit.class);
		attackers.addUnit(attackUnit);
		defenders.addUnit(defenderUnit);
		prepareAttackResult(attackUnit, defenderUnit, ATTACKER_INITIAL_HITPOINTS, DEFENDER_INITIAL_HITPOINTS);
		expect(attackUnit.getEffectiveAttackPoints()).andReturn(BASE_ATTACK_POINTS);
		defenderUnit.receiveDamage(BASE_ATTACK_POINTS * (ATTACKING_BONUS / DEFENDING_BONUS));
		expect(defenderUnit.isDead()).andReturn(true);
		prepareAttackResult(attackUnit, defenderUnit, ATTACKER_ENDING_HITPOINTS, DEFENDER_ENDING_HITPOINTS);
		replay(attackUnit);
		replay(defenderUnit);
		FightResult result = defenders.fight(attackers, DEFENDING_BONUS, ATTACKING_BONUS);
		assertEquals("The attackers should be the winners", attackers, result.getWinners());
		assertEquals("The defenders should be the loosers", defenders, result.getLoosers());
		assertEquals("There should only one fight to be taken place", 1, result.getAttackResults().size());
		verify(attackUnit);
		verify(defenderUnit);
	}
	
	/**
	 * This test demonstrates the scenario in which one attacker kills more than one defender.
	 * @throws InvalidUnitException 
	 */
	public void testFightRepeatingAttacker() throws InvalidUnitException {
		Fleet defenders = new Fleet();
		Fleet attackers = new Fleet();
		Unit defender1 = createMock(Unit.class);
		defenders.addUnit(defender1);
		Unit defender2 = createMock(Unit.class);
		defenders.addUnit(defender2);
		Unit attacker = createMock(Unit.class);
		attackers.addUnit(attacker);
		prepareAttackResult(attacker, defender1, ATTACKER_INITIAL_HITPOINTS, DEFENDER_INITIAL_HITPOINTS);
		expect(attacker.getEffectiveAttackPoints()).andReturn(BASE_ATTACK_POINTS);
		defender1.receiveDamage(BASE_ATTACK_POINTS * (ATTACKING_BONUS / DEFENDING_BONUS));
		expect(defender1.isDead()).andReturn(true);
		prepareAttackResult(attacker, defender1, ATTACKER_ENDING_HITPOINTS, DEFENDER_ENDING_HITPOINTS);
		prepareAttackResult(attacker, defender2, ATTACKER_INITIAL_HITPOINTS, DEFENDER_INITIAL_HITPOINTS);
		expect(attacker.getEffectiveAttackPoints()).andReturn(BASE_ATTACK_POINTS);
		defender2.receiveDamage(BASE_ATTACK_POINTS * (ATTACKING_BONUS / DEFENDING_BONUS));
		expect(defender2.isDead()).andReturn(true);
		prepareAttackResult(attacker, defender2, ATTACKER_ENDING_HITPOINTS, DEFENDER_ENDING_HITPOINTS);
		replay(attacker);
		replay(defender1);
		replay(defender2);
		FightResult result = defenders.fight(attackers, DEFENDING_BONUS, ATTACKING_BONUS);
		assertEquals("The attackers should be the winners", attackers, result.getWinners());
		assertEquals("The defenders should be the loosers", defenders, result.getLoosers());
		assertEquals("There should be two fights", 2, result.getAttackResults().size());
		verify(attacker);
		verify(defender1);
		verify(defender2);
	}
	/**
	 * This test demonstrates the scenario in which one defender kills more than one attacker.
	 * @throws InvalidUnitException 
	 */
	public void testFightRepeatingDefender() throws InvalidUnitException {
		Fleet defenders = new Fleet();
		Fleet attackers = new Fleet();
		Unit attacker1 = createMock(Unit.class);
		attackers.addUnit(attacker1);
		Unit attacker2 = createMock(Unit.class);
		attackers.addUnit(attacker2);
		Unit defender = createMock(Unit.class);
		defenders.addUnit(defender);
		prepareAttackResult(attacker1, defender, ATTACKER_INITIAL_HITPOINTS, DEFENDER_INITIAL_HITPOINTS);
		expect(attacker1.getEffectiveAttackPoints()).andReturn(BASE_ATTACK_POINTS);
		defender.receiveDamage(BASE_ATTACK_POINTS * (ATTACKING_BONUS / DEFENDING_BONUS));
		expect(defender.isDead()).andReturn(false);
		expect(defender.getEffectiveAttackPoints()).andReturn(BASE_ATTACK_POINTS);
		attacker1.receiveDamage(BASE_ATTACK_POINTS * (ATTACKING_BONUS / DEFENDING_BONUS));
		expect(attacker1.isDead()).andReturn(true);
		prepareAttackResult(attacker1, defender, ATTACKER_ENDING_HITPOINTS, DEFENDER_ENDING_HITPOINTS);
		prepareAttackResult(attacker2, defender, ATTACKER_INITIAL_HITPOINTS, DEFENDER_ENDING_HITPOINTS);
		expect(attacker2.getEffectiveAttackPoints()).andReturn(BASE_ATTACK_POINTS);
		defender.receiveDamage(BASE_ATTACK_POINTS * (ATTACKING_BONUS / DEFENDING_BONUS));
		expect(defender.isDead()).andReturn(false);
		expect(defender.getEffectiveAttackPoints()).andReturn(BASE_ATTACK_POINTS);
		attacker2.receiveDamage(BASE_ATTACK_POINTS * (ATTACKING_BONUS / DEFENDING_BONUS));
		expect(attacker2.isDead()).andReturn(true);
		prepareAttackResult(attacker2, defender, ATTACKER_ENDING_HITPOINTS, DEFENDER_ENDING_HITPOINTS);
		replay(attacker1);
		replay(attacker2);
		replay(defender);
		FightResult result = defenders.fight(attackers, DEFENDING_BONUS, ATTACKING_BONUS);
		assertEquals("The defenders should be the winners", defenders, result.getWinners());
		assertEquals("The attackers should be the loosers", attackers, result.getLoosers());
		verify(attacker1);
		verify(attacker2);
		verify(defender);
	}
	
	private void prepareAttackResult(Unit attacker, Unit defender, Float attackerHitPoints, Float defenderHitPoints) {
		expect(attacker.getHitPoints()).andReturn(attackerHitPoints);
		expect(defender.getHitPoints()).andReturn(defenderHitPoints);
	}
	
	public void tearDown() {
	}
}
