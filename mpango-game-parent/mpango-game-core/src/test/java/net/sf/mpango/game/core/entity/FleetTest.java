package net.sf.mpango.game.core.entity;

import net.sf.mpango.game.core.battle.result.FightResult;
import net.sf.mpango.game.core.exception.InvalidUnitException;
import net.sf.mpango.game.core.exception.UnknownTechnologyException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FleetTest {
	
	private static final float BASE_ATTACK_POINTS = 5;
	private static final float DEFENDING_BONUS = 0.5f;
	private static final float ATTACKING_BONUS = 0.25f;
	private static final float ATTACKER_INITIAL_HIT_POINTS = 0;
	private static final float DEFENDER_INITIAL_HIT_POINTS = 0;
	private static final float ATTACKER_ENDING_HIT_POINTS = 0;
	private static final float DEFENDER_ENDING_HIT_POINTS = 0;

    @Mock
    private Unit testing;
    @Mock
    private Official official;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
	public void testAddUnit() throws InvalidUnitException, UnknownTechnologyException {
		Fleet fleet = new Fleet();
		fleet.addUnit(testing);
		fleet.removeUnit(testing);
	}

    @Test(expected = InvalidUnitException.class)
	public void testRemoveUnexisting() throws InvalidUnitException {
		Fleet fleet = new Fleet();
        fleet.removeUnit(testing);
	}

    @Test(expected = InvalidUnitException.class)
	public void testAddTwoOfficials() throws InvalidUnitException {
		final Official duplicatedOfficial = mock(Official.class);
		Fleet fleet = new Fleet(official);
        fleet.addUnit(duplicatedOfficial);
	}

    @Test
	public void testRemoveUnit() throws InvalidUnitException {
		Fleet fleet = new Fleet();
		fleet.addUnit(testing);
		fleet.removeUnit(testing);
	}

	/**
	 * Test that demonstrates the result of attacking and empty fleet with an empty fleet
	 * 
	 */
    @Test
	public void testReceiveAttackWithNoAttackingUnits() {
		Fleet defenders = new Fleet();
		Fleet attackers = new Fleet();

		FightResult result = defenders.fight(attackers, DEFENDING_BONUS, ATTACKING_BONUS);

		assertEquals("The defenders should be the winners", defenders, result.getWinners());
		assertEquals("The attackers should be the losers", attackers, result.getLosers());
	}
	/**
	 * Result of executing an attack with one attack unit and 0 defenders.
	 * @throws InvalidUnitException
	 */
    @Test
	public void testReceiveAttackA1D0() throws InvalidUnitException {
        Unit attackUnit = mock(Unit.class);
        Fleet defenders = new Fleet();
        Fleet attackers = new Fleet(attackUnit);

		FightResult result = defenders.fight(attackers, 0, 0);

		assertEquals("The attackers should be the winners", attackers, result.getWinners());
		assertEquals("The defenders should be the losers", defenders, result.getLosers());
	}

    @Test
	public void receiveDamage_attackers_1_defenders_1_defenders_win() throws InvalidUnitException {
		final Unit attackerUnit = mock(Unit.class);
        final Unit defenderUnit = mock(Unit.class);
        final Fleet defenders = new Fleet(attackerUnit);
        final Fleet attackers = new Fleet(defenderUnit);

        when(attackerUnit.getHitPoints()).thenReturn(ATTACKER_INITIAL_HIT_POINTS);
		when(defenderUnit.getHitPoints()).thenReturn(DEFENDER_INITIAL_HIT_POINTS);
		when(attackerUnit.getEffectiveAttackPoints()).thenReturn(BASE_ATTACK_POINTS);
		when(defenderUnit.isDead()).thenReturn(false);
		when(defenderUnit.getEffectiveAttackPoints()).thenReturn(BASE_ATTACK_POINTS);
		when(attackerUnit.isDead()).thenReturn(true);
		when(attackerUnit.getHitPoints()).thenReturn(ATTACKER_ENDING_HIT_POINTS);
		when(defenderUnit.getHitPoints()).thenReturn(DEFENDER_ENDING_HIT_POINTS);

		final FightResult result = defenders.fight(attackers, DEFENDING_BONUS, ATTACKING_BONUS);

		assertEquals("The losers must be the attackers", attackers, result.getLosers());
		assertEquals("The winners must be the defenders", defenders, result.getWinners());
		assertEquals("There should only one fight to be taken place", 1, result.getAttackResults().size());

		verify(defenderUnit).receiveDamage(attackerUnit, ATTACKING_BONUS, DEFENDING_BONUS);
		verify(attackerUnit).receiveDamage(defenderUnit, ATTACKING_BONUS, DEFENDING_BONUS);
	}
	
	/**
	 * Attack demonstrating the behavior in case of an attacker win.
	 * @throws InvalidUnitException
	 */
    @Test
	public void receiveAttack_attackers_1_defenders_1_attackers_win() throws InvalidUnitException {
        Unit attackUnit = mock(Unit.class);
        Unit defenderUnit = mock(Unit.class);
        Fleet defenders = new Fleet(attackUnit);
        Fleet attackers = new Fleet(defenderUnit);

		prepareAttackResult(attackUnit, defenderUnit, ATTACKER_INITIAL_HIT_POINTS, DEFENDER_INITIAL_HIT_POINTS);
		when(attackUnit.getEffectiveAttackPoints()).thenReturn(BASE_ATTACK_POINTS);
		when(defenderUnit.isDead()).thenReturn(true);
		prepareAttackResult(attackUnit, defenderUnit, ATTACKER_ENDING_HIT_POINTS, DEFENDER_ENDING_HIT_POINTS);

		final FightResult result = defenders.fight(attackers, DEFENDING_BONUS, ATTACKING_BONUS);

		assertEquals("The attackers should be the winners", attackers, result.getWinners());
		assertEquals("The defenders should be the loosers", defenders, result.getLosers());
		assertEquals("There should only one fight to be taken place", 1, result.getAttackResults().size());

		verify(defenderUnit).receiveDamage(attackUnit, ATTACKING_BONUS, DEFENDING_BONUS);
	}
	
	/**
	 * This test demonstrates the scenario in which one attacker kills more than one defender.
	 * @throws InvalidUnitException 
	 */
    @Test
	public void testFightRepeatingAttacker() throws InvalidUnitException {
		Unit defender1 = mock(Unit.class);
		Unit defender2 = mock(Unit.class);
		Unit attacker = mock(Unit.class);

        Fleet defenders = new Fleet(defender1, defender2);
        Fleet attackers = new Fleet(attacker);

		prepareAttackResult(attacker, defender1, ATTACKER_INITIAL_HIT_POINTS, DEFENDER_INITIAL_HIT_POINTS);
		when(attacker.getEffectiveAttackPoints()).thenReturn(BASE_ATTACK_POINTS);
		when(defender1.isDead()).thenReturn(true);
		prepareAttackResult(attacker, defender1, ATTACKER_ENDING_HIT_POINTS, DEFENDER_ENDING_HIT_POINTS);
		prepareAttackResult(attacker, defender2, ATTACKER_INITIAL_HIT_POINTS, DEFENDER_INITIAL_HIT_POINTS);
		when(attacker.getEffectiveAttackPoints()).thenReturn(BASE_ATTACK_POINTS);
		when(defender2.isDead()).thenReturn(true);
		prepareAttackResult(attacker, defender2, ATTACKER_ENDING_HIT_POINTS, DEFENDER_ENDING_HIT_POINTS);
		FightResult result = defenders.fight(attackers, DEFENDING_BONUS, ATTACKING_BONUS);

		assertEquals("The attackers should be the winners", attackers, result.getWinners());
		assertEquals("The defenders should be the loosers", defenders, result.getLosers());
		assertEquals("There should be two fights", 2, result.getAttackResults().size());

		verify(attacker);
		verify(defender1).receiveDamage(attacker, ATTACKING_BONUS, DEFENDING_BONUS);
        verify(defender2).receiveDamage(attacker, ATTACKING_BONUS, DEFENDING_BONUS);
	}
	/**
	 * This test demonstrates the scenario in which one defender kills more than one attacker.
	 * @throws InvalidUnitException 
	 */
    @Test
	public void testFightRepeatingDefender() throws InvalidUnitException {
        Unit attacker1 = mock(Unit.class);
        Unit attacker2 = mock(Unit.class);
        Unit defender = mock(Unit.class);

        Fleet defenders = new Fleet(defender);
        Fleet attackers = new Fleet(attacker1, attacker2);

		prepareAttackResult(attacker1, defender, ATTACKER_INITIAL_HIT_POINTS, DEFENDER_INITIAL_HIT_POINTS);
		when(attacker1.getEffectiveAttackPoints()).thenReturn(BASE_ATTACK_POINTS);
		when(defender.isDead()).thenReturn(false);
		when(defender.getEffectiveAttackPoints()).thenReturn(BASE_ATTACK_POINTS);
		when(attacker1.isDead()).thenReturn(true);
		prepareAttackResult(attacker1, defender, ATTACKER_ENDING_HIT_POINTS, DEFENDER_ENDING_HIT_POINTS);
		prepareAttackResult(attacker2, defender, ATTACKER_INITIAL_HIT_POINTS, DEFENDER_ENDING_HIT_POINTS);
		when(attacker2.getEffectiveAttackPoints()).thenReturn(BASE_ATTACK_POINTS);
		when(defender.isDead()).thenReturn(false);
		when(defender.getEffectiveAttackPoints()).thenReturn(BASE_ATTACK_POINTS);
		when(attacker2.isDead()).thenReturn(true);
		prepareAttackResult(attacker2, defender, ATTACKER_ENDING_HIT_POINTS, DEFENDER_ENDING_HIT_POINTS);
        
		FightResult result = defenders.fight(attackers, DEFENDING_BONUS, ATTACKING_BONUS);
		assertEquals("The defenders should be the winners", defenders, result.getWinners());
		assertEquals("The attackers should be the loosers", attackers, result.getLosers());

		verify(attacker1).receiveDamage(defender, ATTACKING_BONUS, DEFENDING_BONUS);
		verify(attacker2).receiveDamage(defender, ATTACKING_BONUS, DEFENDING_BONUS);
		verify(defender).receiveDamage(attacker1, ATTACKING_BONUS, DEFENDING_BONUS);
        verify(defender).receiveDamage(attacker2, ATTACKING_BONUS, DEFENDING_BONUS);
	}
	
	private void prepareAttackResult(Unit attacker, Unit defender, Float attackerHitPoints, Float defenderHitPoints) {
		when(attacker.getHitPoints()).thenReturn(attackerHitPoints);
		when(defender.getHitPoints()).thenReturn(defenderHitPoints);
	}

    @After
	public void tearDown() {
	}
}
