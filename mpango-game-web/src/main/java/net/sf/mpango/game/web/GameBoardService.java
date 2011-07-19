package net.sf.mpango.game.web;

import net.sf.json.JSONObject;
import net.sf.mpango.game.core.builder.GameBoardBuilder;
import net.sf.mpango.game.core.dto.GameBoardDTO;
import net.sf.mpango.game.core.entity.GameBoard;
import net.sf.mpango.game.core.events.Event;
import net.sf.mpango.game.core.events.GameBoardEvent;
import net.sf.mpango.game.core.events.GameListener;
import net.sf.mpango.game.core.exception.EventNotSupportedException;
import net.sf.mpango.game.core.service.IGameService;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.java.annotation.Listener;
import org.cometd.java.annotation.Service;
import org.cometd.java.annotation.Session;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Service that takes care of the game board information.
 * 
 * @author: etux
 */

@Named
@Singleton
@Service("gameBoardService")
public class GameBoardService implements GameListener {
    
    @Inject
    private IGameService gameService;
    @Inject
    private BayeuxServer bayeux;
    @Session
    private ServerSession serverSession;
    
    private GameBoard gameBoard;
    
    //For communication purposes
    private ServerSession remoteSession;
    private ServerMessage.Mutable message;
    
    public GameBoardService() {
    
    }

    @PostConstruct
    public void init() {
    	gameBoard = gameService.getBoard();
    }
    
    /**
     *
     * @param remote
     * @param message
     */
    @Listener("/service/gameBoard")
    public void subscribe(ServerSession remoteSession, ServerMessage.Mutable message) {
        this.remoteSession = remoteSession;
        this.message = message;
        sendBoard();
    }


	@Override
	public void receiveEvent(Event event) throws EventNotSupportedException {
		if (event instanceof GameBoardEvent) {
			GameBoardEvent gameBoardEvent = (GameBoardEvent) event;
			gameBoard = gameBoardEvent.getBoard();
			sendBoard();
		} else {
			System.err.println("Ignoring event");
			throw new EventNotSupportedException(event);
		}
	}
	
	/**
	 * Method that sends the game board to the subscribed clients.
	 */
	private void sendBoard() {
		JSONObject jsonObject = JSONObject.fromObject(gameBoard);
        this.message.setData(jsonObject);
        this.remoteSession.deliver(serverSession, message);
	}
	
    /**
     * For testing purposes
     * @param serverSession
     */
    protected void setServerSession(ServerSession serverSession) {
        this.serverSession = serverSession;
    }
    /**
     * For testing purposes
     * @param gameService
     */
	public void setGameService(IGameService gameService) {
		this.gameService = gameService;
	}
}