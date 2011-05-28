package net.sourceforge.mpango;

import net.sourceforge.mpango.entity.Cell;
import net.sourceforge.mpango.entity.GameBoard;
import junit.framework.TestCase;

public class GameBoardTest extends TestCase {

	private GameBoard board;
	private static final int ROW_NUMBER = 4;
	private static final int COL_NUMBER = 6;
	
	public void setUp() {
		board = new GameBoard(ROW_NUMBER,COL_NUMBER);
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
