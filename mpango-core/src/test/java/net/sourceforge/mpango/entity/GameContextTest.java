package net.sourceforge.mpango.entity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.easymock.classextension.EasyMock.*;

public class GameContextTest {

	private static final Integer TEST_ROW_SIZE = 5;
	private static final Integer TEST_COL_SIZE = 5;
	
	private GameContext context;
	private GameConfiguration configuration;
	private BoardConfiguration board;
	
	@Before
	public void setUp() {
		configuration = createMock(GameConfiguration.class);
		board = createMock(BoardConfiguration.class);
		expect(configuration.getBoardConfiguration()).andReturn(board);
		expectLastCall().times(1);
		expect(board.getRowNumber()).andReturn(TEST_ROW_SIZE);
		expect(board.getColNumber()).andReturn(TEST_COL_SIZE);
		replay(configuration);
		replay(board);
	}
	
	@After
	public void tearDown() {
		verify(board);
		verify(configuration);
	}
	
	@Test
	public void testConstructor() {
		context = new GameContext(configuration);
	}
	
	@Test
	public void testPlayerJoins() {
		Player player = createMock(Player.class);
		context = new GameContext(configuration);
		context.join(player);
		assertTrue("The context must contain the player", context.containsPlayer(player));
	}
	
}
