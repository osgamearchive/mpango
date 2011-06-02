package net.sourceforge.mpango.entity;

import net.sourceforge.mpango.entity.Cell;
import net.sourceforge.mpango.entity.GameBoard;
import junit.framework.TestCase;

public class GameBoardTest extends TestCase {

	private GameBoard board;
	private BoardConfiguration boardConfiguration;
	private static final int ROW_NUMBER = 4;
	private static final int COL_NUMBER = 6;
	
	public void setUp() {
		boardConfiguration = new BoardConfiguration(ROW_NUMBER,COL_NUMBER);
		board = new GameBoard(boardConfiguration);
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
			board.getCell(7, 6);
			fail("Expected exception not raised");
		} catch (IllegalArgumentException expected) {
			//Do nothing
		}
	}
}
