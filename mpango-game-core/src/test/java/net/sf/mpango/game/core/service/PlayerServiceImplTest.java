package net.sf.mpango.game.core.service;

import java.util.Arrays;

import net.sf.mpango.common.dao.AlreadyExistsException;
import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.game.core.dao.PlayerDAO;
import net.sf.mpango.game.core.entity.GameContext;
import net.sf.mpango.game.core.entity.Player;
import net.sf.mpango.game.core.entity.Position;
import net.sf.mpango.game.core.entity.Unit;

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
	public void testUserJoinsFirstTime() throws AlreadyExistsException {
		User user = new User();
		GameContext context = EasyMock.createMock(GameContext.class);
		Player player = new Player(user, context);
		Position position = new Position(0,0);
		EasyMock.expect(context.generateRandomPosition()).andReturn(position);
		EasyMock.expect(context.generateStartingUnits()).andReturn(Arrays.asList(new Unit[] {new Unit()} ));
		EasyMock.expect(playerDAO.findPlayer(user)).andReturn(null);
		EasyMock.expect(playerDAO.save(player)).andReturn(player);
		context.join(player);
		EasyMock.replay(playerDAO);
		EasyMock.replay(context);
		testing.join(user, context);
		EasyMock.verify(playerDAO);
		EasyMock.verify(context);
	}
	
	@Test
	public void testUserJoinsSecondTime() throws AlreadyExistsException {
		User user = new User();
		GameContext context = EasyMock.createMock(GameContext.class);
		Player player = new Player(user, context);
		EasyMock.expect(playerDAO.findPlayer(user)).andReturn(player);
		//EasyMock.expect(playerDAO.save(player)).andReturn(player);
		context.join(player);
		EasyMock.replay(playerDAO);
		EasyMock.replay(context);
		testing.join(user, context);
		EasyMock.verify(playerDAO);
		EasyMock.verify(context);
	}
}
