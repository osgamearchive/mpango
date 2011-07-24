package net.sf.mpango.game.core.entity;

import net.sf.mpango.game.core.entity.BoardConfiguration;
import net.sf.mpango.game.core.entity.Cell;
import net.sf.mpango.game.core.entity.GameBoard;
import junit.framework.TestCase;

public class GameBoardTest extends TestCase {

	private static final int ROW_NUMBER = 40;
	private static final int COL_NUMBER = 60;
	
	private GameBoard board;
	private BoardConfiguration boardConfiguration;
	
	public void setUp() {
		boardConfiguration = new BoardConfiguration(ROW_NUMBER, COL_NUMBER);
		board = GameBoard.generateRandomBoard(boardConfiguration);
	}
	
	public void tearDown() {
		board = null;
	}
	
	public void testGetExistingCell() {
		Cell cell = board.getCell(1, 1);
		assertNotNull("The cell should exist", cell);
	}
	
	public void testGetUnexistingCell() {
		try {
			board.getCell(ROW_NUMBER+1, COL_NUMBER+1);
			fail("Expected exception not raised");
		} catch (IllegalArgumentException expected) {
			//Do nothing
		}
	}
}
