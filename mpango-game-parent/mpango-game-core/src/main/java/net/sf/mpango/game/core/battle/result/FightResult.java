package net.sf.mpango.game.core.battle.result;

import java.util.ArrayList;
import java.util.List;

import net.sf.mpango.game.core.entity.Fleet;

/**
 * Class that contains the result of a fight.
 * @author etux
 *
 */
public class FightResult {
	
	private Fleet winners;
	private Fleet loosers;
	private List<AttackResult> attackResults;
	
	public FightResult () {
		winners = null;
		loosers = null;
		attackResults = new ArrayList<AttackResult>();
	}

	public void setWinners(Fleet winners) {
		this.winners = winners;
	}

	public Fleet getWinners() {
		return winners;
	}

	public void setLoosers(Fleet loosers) {
		this.loosers = loosers;
	}

	public Fleet getLoosers() {
		return loosers;
	}

	public void addActionResult(AttackResult actionResult) {
		attackResults.add(actionResult);
	}

	public List<AttackResult> getAttackResults() {
		return attackResults;
	}
}
