package net.sf.mpango.game.core.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import net.sf.mpango.game.core.battle.result.AttackResult;
import net.sf.mpango.game.core.battle.result.FightResult;
import net.sf.mpango.game.core.exception.InvalidUnitException;

/**
 * <h1>Class that represents a group of units.</h1>
 * <p>Two opponents each has a fleet which contains a set of units. Every round the attacker engages first then the defender goes next. The battle ends when either side is totally destroyed or retreats.
 * Retreating means being able to interact with the engine while the battle is going on. I think this adds complexity to it and maybe we can leave it for a later version. What do you think?
 * The defender gets some bonus from certain buildings, I.E. The bunker reduces attack power of the enemy.
 * Sounds great! I would also recommend taking into account the terrain or environment where the battle is tacking place (space, water, land, air). So attack points are calculated depending on where the target is, and there is a factor that influences the effectiveness of the attack (i.e. soldiers are almost useless in an land to air battle).
 * So production time & cost are determined by the features
 * Great idea! Besides the bonus, is the officer also a combat unit (meaning that it has attack points and hit points). Will be create special units that can target the officer in order to rest the bonus from the opponent?
 * </p>
 * @author etux
 *
 */
public class Fleet implements Serializable {

	// Each fleet has a officer slot, once occupied by a officer, the whole fleet gets some kind of bonus, such as, speed up, power up better retreat rate. so on.
	private Official official;
	private Queue<Unit> units;
    private Unit currentUnit;
	
	public Fleet() {
		units = new LinkedList<>();
	}

    public Fleet(Unit... units) {
        this(null, units);
    }

    public Fleet(Official official, Unit... units) {
        this.official = official;
        this.units = new LinkedList<>(Arrays.asList(units));
    }
	
	public void addUnit(final Unit unit) throws InvalidUnitException {
		if ((unit instanceof Official) && (official == null)) {
			this.official = (Official) unit;
		} else if ((unit instanceof Official) && (official != null)) {
			throw new InvalidUnitException("The fleet already has an official");
		} else {
			units.add(unit);
		}
	}

	public void removeUnit(final Unit unit) throws InvalidUnitException {
		if (units.contains(unit)) {
			units.remove(unit);
		} else {
			throw new InvalidUnitException("The unit is not in the fleet");
		}
	}
	
	/**
	 * The fleet receives an attack from another fleet. Each unit of the attack fleet gets to attack one unit of the defending fleet.
	 * In case there are more attacking units, then the units from the defending fleet will be attacked more than one time.
	 * @param attackers
	 * @param defenseBonus
	 * @param attackBonus
	 * @return true if the fleet has survived the attack, false in other case.
	 */
	public FightResult fight (final Fleet attackers, final float defenseBonus, final float attackBonus) {
		final FightResult result = new FightResult();
		Unit attacker = attackers.getNextStandingUnit();
		Unit defender = this.getNextStandingUnit();

		while ((attacker = attackers.getNextStandingUnit()) != null &&
               (defender = this.getNextStandingUnit()) != null) {
			final AttackResult actionResult = new AttackResult(attacker, defender);
			//The battle continues, a new defending unit stands
			defender.receiveDamage(attacker, attackBonus, defenseBonus);
			if (defender.isDead()) {
				//If defender is dead we eliminate the unit
                this.killUnit();
				actionResult.setResult(AttackResult.DEFENDER_DIED_RESULT);
			} else {
				//The defender counter attacks
				attacker.receiveDamage(defender, attackBonus, defenseBonus);
				if (attacker.isDead()) {
					attackers.killUnit();
					actionResult.setResult(AttackResult.ATTACKER_DIED_RESULT);
				}
			}
			if ((!attacker.isDead()) && (!defender.isDead())) {
				//Both survived
				actionResult.setResult(AttackResult.NONE_DIED_RESULT);
			}
			result.addActionResult(actionResult);
		}

		if (attacker == null) {
			// The attacker is dead. Defenders have succeeded.
			result.setWinners(this);
			result.setLosers(attackers);
		} else if (defender == null) {
			//The attack is over. No more defending units available.
			result.setWinners(attackers);
			result.setLosers(this);
		}
		return result;
	}
	
	/**
	 * This method returns the next available unit for battle. Once the iterator has gone through all the units,
	 * it will restart. Only those units alive should be available.
	 * @return may return null in case there isn't any unit left.
	 */
	private Unit getNextStandingUnit() {
        currentUnit = units.poll();
        return currentUnit;
	}

    private void killUnit() {
        units.remove(currentUnit);
        currentUnit.kill();
    }
}
