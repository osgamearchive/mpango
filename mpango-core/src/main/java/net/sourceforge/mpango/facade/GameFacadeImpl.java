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

/**
 * <p>Class that serves as a facade to the {@link net.sourceforge.mpango.service.GameServiceImpl}</p>
 * @author edvera
 */
public class GameFacadeImpl implements IGameFacade {

    private GameServiceImpl service;

    @Override
    public void join(String name, UserDTO userDTO) {
        User user = UserFactory.instance().create(userDTO);
        service.join(name, user);
    }

    @Override
    public void settleUnit(UnitDTO unitDTO, CellDTO cellDTO) throws CommandException {
        Unit unit = UnitFactory.instance().create(unitDTO);
        Cell cell = CellFactory.instance().create(cellDTO);
        service.settleUnit(unit, cell);
    }

    public GameServiceImpl getService() {
        return service;
    }

    public void setService(GameServiceImpl service) {
        this.service = service;
    }
}
