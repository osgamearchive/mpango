package net.sf.mpango.game.core.battle.result;

import net.sf.mpango.game.core.entity.Unit;

public class AttackResult {
	
	public static final Integer DEFENDER_DIED_RESULT = 1;
	public static final Integer ATTACKER_DIED_RESULT = 2;
	public static final Integer NONE_DIED_RESULT = 3;
	
	private Unit attacker;
	private Unit defender;
	private Float attackersStartingHitPoints;
	private Float attackersEndingHitPoints;
	private Float defendersStartingHitPoints;
	private Float defendersEndingHitpoints;

	private Integer result;
	
	public AttackResult(Unit attacker, Unit defender) {
		this.attacker = attacker;
		this.defender = defender;
		this.attackersStartingHitPoints = attacker.getHitPoints();
		this.defendersStartingHitPoints = defender.getHitPoints();
	}

	public void setResult (Integer result) {
		this.result = result;
		this.attackersEndingHitPoints = attacker.getHitPoints();
		this.defendersEndingHitpoints = defender.getHitPoints();
	}

	public Float getAttackersStartingHitPoints() {
		return attackersStartingHitPoints;
	}

	public void setAttackersStartingHitPoints(Float attackersStartingHitPoints) {
		this.attackersStartingHitPoints = attackersStartingHitPoints;
	}

	public Float getAttackersEndingHitPoints() {
		return attackersEndingHitPoints;
	}

	public void setAttackersEndingHitPoints(Float attackersEndingHitPoints) {
		this.attackersEndingHitPoints = attackersEndingHitPoints;
	}

	public Float getDefendersStartingHitPoints() {
		return defendersStartingHitPoints;
	}

	public void setDefendersStartingHitPoints(Float defendersStartingHitPoints) {
		this.defendersStartingHitPoints = defendersStartingHitPoints;
	}

	public Float getDefendersEndingHitpoints() {
		return defendersEndingHitpoints;
	}

	public void setDefendersEndingHitpoints(Float defendersEndingHitpoints) {
		this.defendersEndingHitpoints = defendersEndingHitpoints;
	}

	public Integer getResult() {
		return result;
	}
}
