package net.sf.mpango.game.web;

import net.sf.json.JSONObject;
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
    
    @SuppressWarnings("unused")
	@Inject
    private BayeuxServer bayeux;
    
    @Session
    private ServerSession serverSession;
    
    private GameBoard gameBoard;
    
    //For communication purposes
    private ServerSession remoteSession;
    private ServerMessage.Mutable message;

    @PostConstruct
    public void init() {}
    
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
		GameBoard board = getGameBoard();
		JSONObject jsonObject = JSONObject.fromObject(gameBoard);
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