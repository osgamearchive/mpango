package net.sourceforge.mpango.battle;

import net.sourceforge.mpango.Cell;

/**
 * This class is the battle engine that takes care of the development of a battle between two fleets.
 * As requirements for this class:
 * - There is a certain chance that the attacker casts a critical attack which deal double of its original damage.
 * @author etux
 *
 */
public class BattleEngine {

	/**
	 * Every battle involves two parts: attacker and defender. 
	 * Also it is developed on a Terrain that influences the result of the battle.
	 * @param attacker
	 * @param defender
	 * @param terrain
	 */
	public BattleResult battle(Fleet attacker, Fleet defender, Cell terrain) {
		BattleResult result = new BattleResult();
		FightResult fightResult = defender.fight (attacker, terrain.calculateDefenseBonus(), terrain.calculateAttackBonus());
		result.setWinner(fightResult.getWinners());
		result.setLooser(fightResult.getLoosers());
		result.setTerrain(terrain);
		return result;
	}
}
