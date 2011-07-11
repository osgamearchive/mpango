package net.sourceforge.mpango.web.game;

import com.sun.corba.se.spi.servicecontext.SendingContextServiceContext;
import net.sf.json.JSONObject;
import net.sourceforge.mpango.dto.CellDTO;
import net.sourceforge.mpango.dto.GameBoardDTO;
import net.sourceforge.mpango.entity.Cell;
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
        gameBoardDTO = new GameBoardDTO();
        gameBoardDTO.setCells(new CellDTO[10][10]);
        CellDTO cell = null;
        for (int i = 0; i<10; i++) {
            for (int j=0; j<10; j++) {
                cell = new CellDTO();
                cell.setColumn(i);
                cell.setRow(j);
                gameBoardDTO.getCells()[i][j] = new CellDTO();
            }
        }

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
