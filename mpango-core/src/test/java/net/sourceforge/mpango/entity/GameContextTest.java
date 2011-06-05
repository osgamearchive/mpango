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
	private BoardConfiguration boardConfiguration;
	
	@Before
	public void setUp() {
		configuration = createMock(GameConfiguration.class);
		boardConfiguration = createMock(BoardConfiguration.class);
		expect(configuration.getBoardConfiguration()).andReturn(boardConfiguration);
	    expectLastCall().anyTimes();
		expect(boardConfiguration.getRowNumber()).andReturn(TEST_ROW_SIZE);
	    expectLastCall().anyTimes();
		expect(boardConfiguration.getColNumber()).andReturn(TEST_COL_SIZE);
	    expectLastCall().anyTimes();
		replay(configuration);
		replay(boardConfiguration);
	}
	
	@After
	public void tearDown() {
		verify(configuration);
		verify(boardConfiguration);
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
