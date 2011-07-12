package net.sourceforge.mpango.web.game;

import com.sun.corba.se.spi.servicecontext.SendingContextServiceContext;
import net.sf.json.JSONObject;
import net.sourceforge.mpango.builder.GameBoardBuilder;
import net.sourceforge.mpango.dto.CellDTO;
import net.sourceforge.mpango.dto.GameBoardDTO;
import net.sourceforge.mpango.entity.Cell;
import net.sourceforge.mpango.entity.GameBoard;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.java.annotation.Listener;
import org.cometd.java.annotation.Service;
import org.cometd.java.annotation.Session;
import org.eclipse.jetty.util.ajax.JSON;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: etux
 */

@Named
@Singleton
@Service("gameBoardService")
public class GameBoardService {

    private GameBoardDTO gameBoardDTO;

    @Inject
    private BayeuxServer bayeux;
    @Session
    private ServerSession serverSession;

    public GameBoardService() {
        GameBoard board = new GameBoard(10,10);
        gameBoardDTO = GameBoardBuilder.instance().build(board);
    }

    @PostConstruct
    public void init() {
    }
    /**
     *
     * @param remote
     * @param message
     */
    @Listener("/service/gameBoard")
    public void getBoard(ServerSession remote, ServerMessage.Mutable message) {
        System.out.print("Sending gameBoardDTO: "+gameBoardDTO);
        JSONObject jsonObject = JSONObject.fromObject(gameBoardDTO);
        message.setData(jsonObject);
        remote.deliver(serverSession, message);
    }

    /**
     * For testing purposes
     * @param serverSession
     */
    protected void setServerSession(ServerSession serverSession) {
        this.serverSession = serverSession;
    }
}
