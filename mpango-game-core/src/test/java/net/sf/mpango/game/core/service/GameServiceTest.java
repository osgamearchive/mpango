package net.sf.mpango.game.core.service;

import java.util.Arrays;
import java.util.Calendar;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.enums.StateEnum;
import net.sf.mpango.game.core.TestUtils;
import net.sf.mpango.game.core.entity.Player;
import net.sf.mpango.game.core.exception.PlayerAlreadyExistsException;
import net.sf.mpango.game.core.service.GameServiceImpl;
import net.sf.mpango.game.core.dao.PlayerDAO;

import org.apache.commons.lang.RandomStringUtils;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author etux
 */
public class GameServiceTest {

    private GameServiceImpl service;
    private PlayerDAO playerDAO;

    @Before
    public void setUp() {
    	playerDAO = EasyMock.createMock(PlayerDAO.class);
        service = new GameServiceImpl();
        service.setPlayerDAO(playerDAO);
    }

    @Test
    public void testJoin() {
        String name = RandomStringUtils.random(12);
        User user = new User();
        service.join(name, user);
    }
    
    @Test
	public void createPlayerTest() throws PlayerAlreadyExistsException {
		User user = new User();
		user.setDateOfBirth(Calendar.getInstance().getTime());
		user.setEmail("User@company.com");
		user.setGender("male");
		user.setPassword("pwd");
		user.setUsername("user");
		Player player = TestUtils.getPlayer();
		EasyMock.expect(playerDAO.findPlayersByUser(user)).andReturn(null);
		EasyMock.expect(playerDAO.save(player)).andReturn(player);
		EasyMock.replay(playerDAO);
		Player resultingPlayer = service.createPlayer(user, player);
		Assert.assertEquals("The player returned by the service should be the one saved.", player, resultingPlayer);
		EasyMock.verify(playerDAO);
	}
    
    @Test
    public void testDeletePlayer() {
    	Player player = TestUtils.getPlayer();
    	EasyMock.expect(playerDAO.save(player)).andReturn(player);
    	EasyMock.replay(playerDAO);
    	service.delete(player);
    	Assert.assertEquals("A player after being deleted must have StateEnum.DELETED as its state", StateEnum.DELETED, player.getState());
    	EasyMock.verify(playerDAO);
    }
    
    @Test
    public void testAlreadyExistingPlayer () {
    	User user = new User();
    	Player player = new Player();
    	player.setUser(user);
    	EasyMock.expect(playerDAO.findPlayersByUser(user)).andReturn(Arrays.asList(new Player[] {player}));
    	EasyMock.replay(playerDAO);
    	try {
			service.createPlayer(user, player);
			Assert.fail("Expected exception not raised");
		} catch (PlayerAlreadyExistsException expected) {
			// TODO Auto-generated catch block
		}
		EasyMock.verify(playerDAO);
    }
}
