package net.sourceforge.mpango.web.game;

import net.sourceforge.mpango.dto.GameBoardDTO;
import net.sourceforge.mpango.entity.GameBoard;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.easymock.classextension.EasyMock;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


/**
 * @author etux
 */
public class GameBoardServiceTest {

    private GameBoardService gameBoardService;
    private ServerSession mockedServerSession;
    private ServerSession mockedRemote;
    private ServerMessage.Mutable mockedMessage;

    @org.junit.Before
    public void setUp() {
        gameBoardService = new GameBoardService();
        mockedServerSession = EasyMock.createMock(ServerSession.class);
        mockedRemote = EasyMock.createMock(ServerSession.class);
        mockedMessage = EasyMock.createMock(ServerMessage.Mutable.class);
        gameBoardService.setServerSession(mockedServerSession);
    }


    @Test
    public void testGetBoard() {
        mockedMessage.setData(EasyMock.isA(GameBoardDTO.class));
        mockedRemote.deliver(mockedServerSession, mockedMessage);
        EasyMock.replay(mockedServerSession, mockedMessage, mockedRemote);
        gameBoardService.getBoard(mockedRemote, mockedMessage);
        EasyMock.verify(mockedServerSession, mockedMessage, mockedRemote);
    }
}
