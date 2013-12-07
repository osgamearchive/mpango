package net.sf.mpango.game.core.service;

import javax.jms.JMSException;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.game.core.entity.GameContext;
import net.sf.mpango.game.core.entity.Player;
import net.sf.mpango.game.core.jms.EventBasedMessageCreator;

import org.easymock.classextension.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author etux
 */
public class GameServiceTest {

	private static final String CORE_TO_INTERFACE_TOPIC = "/topic/core2interface";
	private static final String CORE_TO_INTERFACE_QUEUE = "/queue/core2interface";
	private static final String INTERFACE_TO_CORE_TOPIC = "/topic/interface2core";
	private static final String INTERFACE_TO_CORE_QUEUE = "/queue/interface2core";
	
	private IPlayerService playerService;
	private JmsTemplate jmsTemplate;
    private GameServiceImpl testing;
    private GameContext context;

    @Before
    public void setUp() {
        testing = new GameServiceImpl();
        playerService = EasyMock.createMock(IPlayerService.class);
        jmsTemplate = EasyMock.createMock(JmsTemplate.class);
        context = EasyMock.createMock(GameContext.class);
        testing.setPlayerService(playerService);
        testing.setGameContext(context);
        testing.setJmsTemplate(jmsTemplate);
        testing.setCoreToInterfaceQueue(CORE_TO_INTERFACE_QUEUE);
        testing.setCoreToInterfaceTopic(CORE_TO_INTERFACE_TOPIC);
        testing.setInterfaceToCoreQueue(INTERFACE_TO_CORE_QUEUE);
        testing.setInterfaceToCoreTopic(INTERFACE_TO_CORE_TOPIC);
    }

    @Test
    public void testJoin() throws JMSException {
		//Initializing data
        User user = new User();
        user.setId(new Long(1));
        Player player = new Player(user, context);
		//Recording behavior
        EasyMock.expect(playerService.join(user, context)).andReturn(player);
        jmsTemplate.send(EasyMock.eq(testing.getCoreToInterfaceTopic()), EasyMock.isA(EventBasedMessageCreator.class));
        EasyMock.replay(playerService);
        EasyMock.replay(jmsTemplate);
        EasyMock.replay(context);
        //Code under test
        Player returnedPlayer = testing.join(user);
        //Assertions
        Assert.assertEquals("The player must be the same as the returned by the player service", player, returnedPlayer);
        EasyMock.verify(context);
        EasyMock.verify(jmsTemplate);
        EasyMock.verify(playerService);
    }
}