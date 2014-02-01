package net.sf.mpango.game.core.entity;

import junit.framework.TestCase;

public class CellTest extends TestCase {

	private static final int ROW_NUMBER = 1;
	private static final int COLUMN_NUMBER = 1;
	private Cell cell;
	
	protected void setUp() throws Exception {
		super.setUp();
		cell = new Cell(COLUMN_NUMBER, ROW_NUMBER);
		cell.setAttackBonus(1);
		cell.setDefenseBonus(2);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCalculateBasicDefenseBonus() {
		float defenseBonus = cell.calculateDefenseBonus();
		assertTrue("The defense bonus should be the expected", 2 == defenseBonus);
	}

	public void testCalculateBasicAttackBonus() {
		float attackBonus = cell.calculateAttackBonus();
		assertTrue("The attack bonus should be the expected", 1 == attackBonus);
	}

	public void testCalculateUnexistingConstructionDefenseBonus() {
		float constructionDefenseBonus = cell.calculateConstructionDefenseBonus();
		assertTrue("The defense bonus should be the expected", 0.0 == constructionDefenseBonus);
	}

	public void testCalculateUnexistingConstructionAttackBonus() {
		float constructionAttackBonus = cell.calculateConstructionAttackBonus();
		assertTrue("The attack bonus should be the expected", 0.0 == constructionAttackBonus);
	}
}
