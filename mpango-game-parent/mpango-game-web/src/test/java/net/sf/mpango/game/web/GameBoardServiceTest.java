package net.sf.mpango.game.web;

import net.sf.json.JSONObject;
import net.sf.mpango.game.core.entity.GameBoard;
import net.sf.mpango.game.core.service.IGameService;

import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.easymock.classextension.EasyMock;
import org.junit.Before;
import org.junit.Test;


/**
 * @author etux
 */
public class GameBoardServiceTest {

    private GameBoardServiceImpl testing;
    
    private ServerSession mockedLocalSession;
    private ServerSession mockedRemoteSession;
    private ServerMessage.Mutable mockedMessage;
    private IGameService mockedGameService;

    @Before
    public void setUp() {
        mockedGameService 	= EasyMock.createMock(IGameService.class);
        mockedLocalSession 	= EasyMock.createMock(ServerSession.class);
        mockedRemoteSession = EasyMock.createMock(ServerSession.class);
        mockedMessage 		= EasyMock.createMock(ServerMessage.Mutable.class);
        testing 			= new GameBoardServiceImpl();
        
        testing.setServerSession(mockedLocalSession);
        testing.setGameService(mockedGameService);
    }


    @Test
    public void testGetBoard() {
    	//Data for the unit test
        GameBoard board = TestUtils.prepareGameBoard();
    	//Expectations
    	EasyMock.expect(mockedGameService.getBoard()).andReturn(board);
        mockedMessage.setData(JSONObject.fromObject(board));
        mockedRemoteSession.deliver(mockedLocalSession, mockedMessage);
        //Replay
        EasyMock.replay(mockedLocalSession, mockedMessage, mockedRemoteSession, mockedGameService);
        //Testing
        testing.subscribe(mockedRemoteSession, mockedMessage);
        //Verifications and assertions
        EasyMock.verify(mockedLocalSession, mockedMessage, mockedRemoteSession, mockedGameService);
    }
}