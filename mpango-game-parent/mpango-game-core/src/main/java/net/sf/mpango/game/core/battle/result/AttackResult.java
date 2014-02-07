package net.sf.mpango.game.core.battle.result;

import net.sf.mpango.game.core.entity.Unit;

public class AttackResult {

	public static final int DEFENDER_DIED_RESULT = 1;
	public static final int ATTACKER_DIED_RESULT = 2;
	public static final int NONE_DIED_RESULT = 3;
	
	private final Unit attacker;
	private final Unit defender;
	private final float attackersStartingHitPoints;
	private float attackersEndingHitPoints;
	private final float defendersStartingHitPoints;
	private float defendersEndingHitpoints;

	private int result;
	
	public AttackResult(final Unit attacker, final Unit defender) {
		this.attacker = attacker;
		this.defender = defender;
		this.attackersStartingHitPoints = attacker.getHitPoints();
		this.defendersStartingHitPoints = defender.getHitPoints();
	}

    public int getResult() {
   		return result;
   	}

	public void setResult (int result) {
		this.result = result;
		this.attackersEndingHitPoints = attacker.getHitPoints();
		this.defendersEndingHitpoints = defender.getHitPoints();
	}

	public float getAttackersStartingHitPoints() {
		return attackersStartingHitPoints;
	}

	public float getAttackersEndingHitPoints() {
		return attackersEndingHitPoints;
	}

	public void setAttackersEndingHitPoints(float attackersEndingHitPoints) {
		this.attackersEndingHitPoints = attackersEndingHitPoints;
	}

	public float getDefendersStartingHitPoints() {
		return defendersStartingHitPoints;
	}

	public float getDefendersEndingHitpoints() {
		return defendersEndingHitpoints;
	}

	public void setDefendersEndingHitpoints(float defendersEndingHitpoints) {
		this.defendersEndingHitpoints = defendersEndingHitpoints;
	}
}
