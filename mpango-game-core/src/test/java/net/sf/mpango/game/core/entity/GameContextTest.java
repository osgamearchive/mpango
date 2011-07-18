package net.sf.mpango.game.core.entity;

import net.sf.mpango.game.core.entity.GameConfiguration;
import net.sf.mpango.game.core.entity.GameContext;
import net.sf.mpango.game.core.entity.Player;
import net.sf.mpango.game.core.entity.Position;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.classextension.EasyMock.*;

public class GameContextTest {

	private static final Integer TEST_ROW_SIZE = 5;
	private static final Integer TEST_COL_SIZE = 5;
	
	private GameContext testing;
	private GameConfiguration gameConfiguration;
	private GameBoard gameBoard;
	
	@Before
	public void setUp() {
		testing = new GameContext();
		gameConfiguration = createMock(GameConfiguration.class);
		gameBoard = createMock(GameBoard.class);
		testing.setConfiguration(gameConfiguration);
		testing.setBoard(gameBoard);
		expect(gameBoard.getRowSize()).andReturn(TEST_ROW_SIZE);
		expect(gameBoard.getColSize()).andReturn(TEST_COL_SIZE);
		replay(gameConfiguration);
		replay(gameBoard);
	}
	
	@After
	public void tearDown() {
		verify(gameBoard);
		verify(gameConfiguration);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testPlayerJoins() {
		Player player = createMock(Player.class);
        player.setPosition(isA(Position.class));
        player.setUnits(isA(java.util.List.class));
        replay(player);
		testing.join(player);
		assertTrue("The context must contain the player", testing.containsPlayer(player));
        verify(player);
	}
	
}