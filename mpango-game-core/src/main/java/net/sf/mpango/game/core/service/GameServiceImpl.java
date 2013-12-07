package net.sf.mpango.game.core.service;

import org.springframework.jms.core.JmsTemplate;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.game.core.entity.GameBoard;
import net.sf.mpango.game.core.entity.GameContext;
import net.sf.mpango.game.core.entity.Player;
import net.sf.mpango.game.core.events.Event;
import net.sf.mpango.game.core.events.JoinGameEvent;
import net.sf.mpango.game.core.jms.EventBasedMessageCreator;

/**
 * <p>{@link IGameService} implementation.</p>
 * @author edvera
 */
public class GameServiceImpl implements IGameService {
	
    private GameContext context;
    private IPlayerService playerService;
    private JmsTemplate jmsTemplate;
	private String coreToInterfaceTopic;
	private String interfaceToCoreTopic;
	private String coreToInterfaceQueue;
	private String interfaceToCoreQueue;

    public void init() {
        //Implement the loading of a game.
    	context = new GameContext();
    }
    
    public Player join(User user) {
    	Player player = playerService.join(user, context);
    	JoinGameEvent event = new JoinGameEvent(player);
    	sendAll(event);
    	return player;
    }

    public GameBoard getBoard() {
    	return context.getBoard();
    }

    /**
     * Method that sends to all subscribers of the topic the event.
     * @param event Event to be broadcasted.
     */
    private void sendAll(Event event) {
    	jmsTemplate.send(coreToInterfaceTopic, new EventBasedMessageCreator(event));
    }
    
	public IPlayerService getPlayerService() {
		return playerService;
	}

	public void setPlayerService(IPlayerService playerService) {
		this.playerService = playerService;
	}

	protected void setGameContext(GameContext context) {
		this.context = context;
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public String getCoreToInterfaceTopic() {
		return this.coreToInterfaceTopic;
	}
	
	public void setCoreToInterfaceTopic(String coreToInterfaceTopic) {
		this.coreToInterfaceTopic = coreToInterfaceTopic;
	}
	public String getInterfaceToCoreTopic() {
		return interfaceToCoreTopic;
	}

	public void setInterfaceToCoreTopic(String interfaceToCoreTopic) {
		this.interfaceToCoreTopic = interfaceToCoreTopic;
	}

	public String getCoreToInterfaceQueue() {
		return coreToInterfaceQueue;
	}

	public void setCoreToInterfaceQueue(String coreToInterfaceQueue) {
		this.coreToInterfaceQueue = coreToInterfaceQueue;
	}

	public String getInterfaceToCoreQueue() {
		return interfaceToCoreQueue;
	}

	public void setInterfaceToCoreQueue(String interfaceToCoreQueue) {
		this.interfaceToCoreQueue = interfaceToCoreQueue;
	}
}