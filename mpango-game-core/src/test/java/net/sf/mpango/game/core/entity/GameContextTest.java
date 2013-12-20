package net.sf.mpango.game.core.entity;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.classextension.EasyMock.createMock;
import static org.easymock.classextension.EasyMock.expect;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GameContextTest {

	private static final int TEST_ROW_SIZE = 5;
	private static final int TEST_COL_SIZE = 5;
	
	private GameContext testing;
	private GameConfiguration gameConfiguration;
	private GameBoard gameBoard;
    private BoardConfiguration boardConfiguration;

    @Before
	public void setUp() {
		testing = new GameContext();
		gameConfiguration = createMock(GameConfiguration.class);
		gameBoard = createMock(GameBoard.class);
        boardConfiguration = createMock(BoardConfiguration.class);
		testing.setConfiguration(gameConfiguration);
		testing.setBoard(gameBoard);
	}
	
	@After
	public void tearDown() {
	}
	
	@Test
	public void testPlayerJoins() {
		final Player player = new Player();
		testing.join(player);
		assertTrue("The context must contain the player", testing.containsPlayer(player));
	}
	
	@Test
	public void testGenerateRandomPosition() {
        expect(gameBoard.getConfiguration()).andReturn(boardConfiguration).anyTimes();
		expect(boardConfiguration.getRowNumber()).andReturn(TEST_ROW_SIZE);
		expect(boardConfiguration.getColNumber()).andReturn(TEST_COL_SIZE);
		replay(gameConfiguration);
		replay(gameBoard);
        replay(boardConfiguration);
		Position position = testing.generateRandomPosition();
		assertNotNull(position.getColNumber());
		assertNotNull(position.getRowNumber());
		assertTrue("There should be as many as n columns, starting from 0 and ending with n-1", position.getColNumber() < TEST_COL_SIZE);
		assertTrue("There should be as many as m rows, starting from 0 and ending with m-1", position.getRowNumber() < TEST_ROW_SIZE);
		verify(gameBoard);
		verify(gameConfiguration);
        verify(boardConfiguration);
	}
	
	@Test
	public void testGenerateStartingUnits() {
		List<Unit> units = testing.generateStartingUnits();
		assertNotNull(units);
		assertTrue("There has to be at least one starting unit.", units.size() > 0);
	}
}