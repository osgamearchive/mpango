package net.sf.mpango.game.web;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.json.JSONObject;
import net.sf.mpango.game.core.entity.GameBoard;
import net.sf.mpango.game.core.events.Event;
import net.sf.mpango.game.core.events.GameBoardEvent;
import net.sf.mpango.game.core.events.GameListener;
import net.sf.mpango.game.core.exception.EventNotSupportedException;
import net.sf.mpango.game.core.service.IGameBoardService;
import net.sf.mpango.game.core.service.IGameService;
import org.apache.log4j.Logger;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ConfigurableServerChannel;
import org.cometd.bayeux.server.ServerChannel;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerMessage.Mutable;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.java.annotation.Configure;
import org.cometd.java.annotation.Listener;
import org.cometd.java.annotation.Service;
import org.cometd.java.annotation.Session;

/**
 * Service that takes care of the game board information.
 * 
 * @author: etux
 */

@Named
@Singleton
@Service("gameBoardService")
public class GameBoardServiceImpl implements GameListener, IGameBoardService {
    
    @Inject
    private IGameService gameService;
    
	@Inject
    private BayeuxServer bayeux;
    
    @Session
    private ServerSession serverSession;
    
    private GameBoard gameBoard;

    @PostConstruct
    public void init() {
    	logger.debug("Initializing service: " + this.getClass().getAnnotation(Service.class).toString());
    }
    
    @Configure("/service/gameBoard")
    public void configure(final ConfigurableServerChannel channel) {
    	logger.debug("Configuring the /service/gameBoard");
        channel.setLazy(true);
        //channel.addAuthorizer(GrantAuthorizer.GRANT_PUBLISH);
    }
    
    /**
     *
     * @param remoteSession
     * @param message
     */
    @Listener("/service/gameBoard")
    public void subscribe(final ServerSession remoteSession, final ServerMessage.Mutable message) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("New subscription from client: "+remoteSession.getId());
    	}
        sendBoard(remoteSession, message);
    }


	@Override
	public void receive (final Event event) throws EventNotSupportedException {
		if (event instanceof GameBoardEvent) {
			//Cast the event to a GameEvent
			GameBoardEvent gameBoardEvent = (GameBoardEvent) event;
			//Create the resources to communicate the event
			ServerChannel channel = bayeux.getChannel("/service/gameBoard");
			Mutable message = bayeux.newMessage();
			//Handle the message
			message.setData(gameBoardEvent.getBoard());
			//Publish the message to all subscribers
			channel.publish (this.serverSession, message);
		} else {
			logger.warn("Ignoring event");
			throw new EventNotSupportedException(event);
		}
	}
	
	/**
	 * Method that sends the game board to the subscribed clients.
	 * @param message
	 * @param remoteSession 
	 */
	private void sendBoard(final ServerSession remoteSession, final Mutable message) {
		JSONObject jsonObject = JSONObject.fromObject(getGameBoard());
        message.setData(jsonObject);
        remoteSession.deliver(serverSession, message);
	}
	
    private GameBoard getGameBoard() {
		if (gameBoard == null) {
			gameBoard = gameService.getBoard();
		}
		return gameBoard;
	}

	/**
     * For testing purposes
     * @param serverSession
     */
    protected void setServerSession(final ServerSession serverSession) {
        this.serverSession = serverSession;
    }
    /**
     * For testing purposes
     * @param gameService
     */
	public void setGameService(final IGameService gameService) {
		this.gameService = gameService;
	}

	@Override
	public GameBoard getBoard() {
		return this.gameBoard;
	}
	
	private static final Logger logger = Logger.getLogger(GameBoardServiceImpl.class);
}