package net.sf.mpango.game.web;

import net.sf.mpango.directory.entity.User;
import net.sf.mpango.game.core.service.IGameService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author etux
 */
public class GameBackingBean {

    @Autowired
    private IGameService gameService;
    private User user;

    public void join() {
        gameService.join(user);
    }

    public IGameService getGameService() {
        return gameService;
    }

    public void setGameService(IGameService gameService) {
        this.gameService = gameService;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
