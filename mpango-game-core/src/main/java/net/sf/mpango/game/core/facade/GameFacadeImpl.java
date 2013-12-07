package net.sf.mpango.game.core.facade;

import net.sf.mpango.common.directory.adapter.UserAdapter;
import net.sf.mpango.common.directory.dto.UserDTO;
import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.game.core.service.IGameService;

/**
 * <p>Class that serves as a facade to the {@link net.sf.mpango.game.core.service.GameServiceImpl}</p>
 * @author edvera
 */
public class GameFacadeImpl implements IGameFacade {

    private IGameService gameService;

    @Override
    public void join(String name, UserDTO userDTO) {
        User user = UserAdapter.getInstance().fromDTO(userDTO);
        gameService.join(user);
    }

    public IGameService getGameService() {
        return gameService;
    }

    public void setGameService(IGameService service) {
        this.gameService = service;
    }
}
