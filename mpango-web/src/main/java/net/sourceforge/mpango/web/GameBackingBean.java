package net.sourceforge.mpango.web;

import net.sourceforge.mpango.directory.dto.UserDTO;
import net.sourceforge.mpango.facade.GameFacadeImpl;
import net.sourceforge.mpango.facade.IGameFacade;

import java.util.jar.Attributes;

/**
 * Created by IntelliJ IDEA.
 * User: etux
 * Date: 6/13/11
 * Time: 11:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameBackingBean {

    private IGameFacade gameFacade;
    private UserDTO user;
    private String name;

    public void join() {
        gameFacade.join(name, user);
    }

    public IGameFacade getGameFacade() {
        return gameFacade;
    }

    public void setGameFacade(IGameFacade gameFacade) {
        this.gameFacade = gameFacade;
    }
}
