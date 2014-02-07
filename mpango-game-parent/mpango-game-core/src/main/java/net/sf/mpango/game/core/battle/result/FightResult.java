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
	private Fleet losers;
	private List<AttackResult> attackResults;

    public FightResult() {
        this (null, null, new ArrayList<AttackResult>());
    }
	
	public FightResult (final Fleet winners, final Fleet losers, final List<AttackResult> attackResults) {
		this.winners = winners;
        this.losers = losers;
        this.attackResults = attackResults;
	}

	public Fleet getWinners() {
		return winners;
	}

    public void setWinners(Fleet winners) {
        this.winners = winners;
    }

	public Fleet getLosers() {
		return losers;
	}

    public void setLosers(Fleet losers) {
        this.losers = losers;
    }


	public void addActionResult(AttackResult actionResult) {
		attackResults.add(actionResult);
	}

	public List<AttackResult> getAttackResults() {
		return attackResults;
	}
}
