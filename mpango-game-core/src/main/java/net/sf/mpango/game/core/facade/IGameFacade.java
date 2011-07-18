package net.sf.mpango.game.core.facade;

import net.sf.mpango.common.directory.dto.UserDTO;
import net.sf.mpango.game.core.dto.CellDTO;
import net.sf.mpango.game.core.dto.UnitDTO;
import net.sf.mpango.game.core.exception.CommandException;

/**
 * Created by IntelliJ IDEA.
 * User: etux
 * Date: 6/13/11
 * Time: 11:25 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IGameFacade {

        /**
     * Method that allows a User to join a game with a particular player name.
     * @param name Name of the player.
     * @param user User behind the player.
     */
    void join(String name, UserDTO user);

    /**
     * Method that allows a User to settle a unit in a particular cell creating a city.
     * @param unit Unit to settle.
     * @param cell Cell on which to build the city.
     * @throws net.sf.mpango.game.core.exception.CommandException in case it was impossible to execute the action.
     */
    void settleUnit(UnitDTO unit, CellDTO cell) throws CommandException;

}
