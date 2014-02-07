package net.sf.mpango.game.core.battle;

import net.sf.mpango.game.core.battle.result.BattleResult;
import net.sf.mpango.game.core.battle.result.FightResult;
import net.sf.mpango.game.core.entity.Cell;
import net.sf.mpango.game.core.entity.Fleet;
import junit.framework.TestCase;
import static org.easymock.classextension.EasyMock.*;

public class BattleEngineTest extends TestCase {

	private BattleEngine engine;
	private Cell terrain;
	private Fleet attacker;
	private Fleet defender;
	
	public void setUp() {
		engine = new BattleEngine();
		attacker = createMock(Fleet.class);
		defender = createMock(Fleet.class);
		terrain = createMock(Cell.class);
	}
	
	public void tearDown() {
		verify(attacker);
		verify(defender);
		verify(terrain);
	}
	
	public void testBattleDefenderWin () {
		expect(terrain.calculateDefenseBonus()).andReturn(1f);
		expect(terrain.calculateAttackBonus()).andReturn(1f);
		FightResult fightResult = new FightResult();
		fightResult.setWinners(defender);
		fightResult.setLosers(attacker);
		expect(defender.fight(attacker, 1f, 1f)).andReturn(fightResult);
		replay(defender);
		replay(attacker);
		replay(terrain);
		BattleResult result = engine.battle(attacker, defender, terrain);
		assertEquals("The defender should be the winner", defender, result.getWinner());
		assertEquals("The attacker should be the looser", attacker, result.getLooser());
		assertEquals("The terrain must be the expected", terrain, result.getTerrain());
	}
	
	public void testBattleAttackerWin() {
		expect(terrain.calculateDefenseBonus()).andReturn(1f);
		expect(terrain.calculateAttackBonus()).andReturn(1f);
		FightResult fightResult = new FightResult();
		fightResult.setWinners(attacker);
		fightResult.setLosers(defender);
		expect(defender.fight(attacker, 1f, 1f)).andReturn(fightResult);
		replay(defender);
		replay(attacker);
		replay(terrain);
		BattleResult result = engine.battle(attacker, defender, terrain);
		assertEquals("The attacker should be the winner", attacker, result.getWinner());
		assertEquals("The defender should be the looser", defender, result.getLooser());
		assertEquals("The terrain must be the expected", terrain, result.getTerrain());
	}
}
