package net.sf.mpango.game.core.service;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.game.core.dao.PlayerDAO;
import net.sf.mpango.game.core.entity.GameContext;

import org.easymock.classextension.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class PlayerServiceImplTest {

	private PlayerServiceImpl testing;
	private PlayerDAO playerDAO;
	
	@Before
	public void setUp() {
		testing = new PlayerServiceImpl();
		playerDAO = EasyMock.createMock(PlayerDAO.class);
		testing.setPlayerDAO(playerDAO);
	}
	
	@Test
	public void testUserJoinsFirstTime() {
		User user = new User();
		GameContext context = EasyMock.createMock(GameContext.class);
		EasyMock.expect(playerDAO.findPlayersByUser(user)).andReturn(null);
		EasyMock.replay(playerDAO);
		EasyMock.replay(context);
		testing.register(user, context);
		EasyMock.verify(playerDAO);
		EasyMock.verify(context);
	}
}
