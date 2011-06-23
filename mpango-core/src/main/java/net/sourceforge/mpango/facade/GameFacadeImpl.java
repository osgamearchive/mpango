package net.sourceforge.mpango.facade;

import net.sourceforge.mpango.directory.dto.UserDTO;
import net.sourceforge.mpango.directory.entity.User;
import net.sourceforge.mpango.directory.factory.CellFactory;
import net.sourceforge.mpango.directory.factory.UnitFactory;
import net.sourceforge.mpango.directory.factory.UserFactory;
import net.sourceforge.mpango.dto.CellDTO;
import net.sourceforge.mpango.dto.UnitDTO;
import net.sourceforge.mpango.entity.Cell;
import net.sourceforge.mpango.entity.Unit;
import net.sourceforge.mpango.exception.CommandException;
import net.sourceforge.mpango.service.GameServiceImpl;
import net.sourceforge.mpango.service.IGameService;

/**
 * <p>Class that serves as a facade to the {@link net.sourceforge.mpango.service.GameServiceImpl}</p>
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

    public IGameService getGameService() {
        return gameService;
    }

    public void setGameService(IGameService service) {
        this.gameService = service;
    }
}
