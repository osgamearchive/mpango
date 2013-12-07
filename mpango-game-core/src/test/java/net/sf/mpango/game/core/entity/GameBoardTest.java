package net.sf.mpango.game.core.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertNotNull;

public class GameBoardTest {

	private static final int ROW_NUMBER = 40;
	private static final int COL_NUMBER = 60;
	
	private GameBoard board;
	private BoardConfiguration boardConfiguration;

    @Before
	public void setUp() {
		boardConfiguration = new BoardConfiguration(ROW_NUMBER, COL_NUMBER);
		board = GameBoard.generateRandomBoard(boardConfiguration);
	}

    @After
	public void tearDown() {
		board = null;
	}

    @Test
	public void testGetExistingCell() {
		Cell cell = board.getCell(new Position(1, 1));
		assertNotNull("The cell should exist", cell);
	}

    @Test
	public void testGetUnexistingCell() {
		try {
			board.getCell(new Position(ROW_NUMBER+1, COL_NUMBER+1));
			fail("Expected exception not raised");
		} catch (IllegalArgumentException expected) {
			//Do nothing
		}
	}
}
