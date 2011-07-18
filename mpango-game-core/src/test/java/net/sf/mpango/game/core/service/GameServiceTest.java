package net.sf.mpango.game.core.service;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.game.core.entity.GameContext;
import net.sf.mpango.game.core.entity.Player;
import net.sf.mpango.game.core.service.GameServiceImpl;

import org.easymock.classextension.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author etux
 */
public class GameServiceTest {

	private IPlayerService playerService;
    private GameServiceImpl testing;
    private GameContext context;

    @Before
    public void setUp() {
        testing = new GameServiceImpl();
        playerService = EasyMock.createMock(IPlayerService.class);
        context = EasyMock.createMock(GameContext.class);
        testing.setPlayerService(playerService);
        testing.setGameContext(context);
    }

    @Test
    public void testJoin() {
        User user = new User();
        Player player = new Player(user, context);
        EasyMock.expect(playerService.register(user, context)).andReturn(player);
        EasyMock.replay(playerService);
        EasyMock.replay(context);
        Player returnedPlayer = testing.join(user);
        Assert.assertEquals("The player must be the same as the returned by the player service", player, returnedPlayer);
        EasyMock.verify(playerService);
        EasyMock.verify(context);
    }
}
