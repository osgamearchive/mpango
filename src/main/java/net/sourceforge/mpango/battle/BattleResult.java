package net.sourceforge.mpango.battle;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.mpango.Cell;

/**
 * This class helps deal with the results of a battle.
 * It can be used for historical reason.
 * @author etux
 *
 */
public class BattleResult {

	private Fleet winner;
	private Fleet looser;
	private Cell terrain;
	private List<FightResult> fights;
	
	public BattleResult() {
		winner = null;
		looser = null;
		terrain = null;
		fights = new ArrayList<FightResult>();
	}
	
	public Fleet getWinner() {
		return winner;
	}
	
	public void setWinner(Fleet winner) {
		this.winner = winner;
	}

	public void setLooser(Fleet looser) {
		this.looser = looser;
	}

	public Fleet getLooser() {
		return looser;
	}

	public void setTerrain(Cell terrain) {
		this.terrain = terrain;
	}
	
	public Cell getTerrain() {
		return this.terrain;
	}

	public void addFightResult(FightResult lastFightResult) {
		this.fights.add(lastFightResult);		
	}
}