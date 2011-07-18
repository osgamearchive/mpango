package net.sf.mpango.game.core.facade;

import net.sf.mpango.common.directory.dto.UserDTO;
import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.factory.UserFactory;
import net.sf.mpango.game.core.builder.PlayerBuilder;
import net.sf.mpango.game.core.dto.CellDTO;
import net.sf.mpango.game.core.dto.PlayerDTO;
import net.sf.mpango.game.core.dto.UnitDTO;
import net.sf.mpango.game.core.entity.Cell;
import net.sf.mpango.game.core.entity.Player;
import net.sf.mpango.game.core.entity.Unit;
import net.sf.mpango.game.core.exception.CommandException;
import net.sf.mpango.game.core.exception.PlayerAlreadyExistsException;
import net.sf.mpango.game.core.factory.CellFactory;
import net.sf.mpango.game.core.factory.PlayerFactory;
import net.sf.mpango.game.core.factory.UnitFactory;
import net.sf.mpango.game.core.service.IGameService;

/**
 * <p>Class that serves as a facade to the {@link net.sf.mpango.game.core.service.GameServiceImpl}</p>
 * @author edvera
 */
public class GameFacadeImpl implements IGameFacade {

    private IGameService gameService;

    @Override
    public void join(String name, UserDTO userDTO) {
        User user = UserFactory.instance().create(userDTO);
        gameService.join(name, user);
    }

    @Override
    public void settleUnit(UnitDTO unitDTO, CellDTO cellDTO) throws CommandException {
        Unit unit = UnitFactory.instance().create(unitDTO);
        Cell cell = CellFactory.instance().create(cellDTO);
        gameService.settleUnit(unit, cell);
    }
	@Override
	public PlayerDTO createPlayer(UserDTO userDTO, PlayerDTO playerDTO) throws PlayerAlreadyExistsException {
		User user = UserFactory.instance().create(userDTO);
		Player player = PlayerFactory.instance().create(playerDTO);
		player = gameService.createPlayer(user, player);
		return PlayerBuilder.instance().build(player);
	}

	@Override
	public void delete(PlayerDTO player) {
		gameService.delete(PlayerFactory.instance().create(player));

	}

    public IGameService getGameService() {
        return gameService;
    }

    public void setGameService(IGameService service) {
        this.gameService = service;
    }
}
