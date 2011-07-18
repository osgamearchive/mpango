package net.sf.mpango.game.core.service;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.game.core.entity.Cell;
import net.sf.mpango.game.core.entity.Player;
import net.sf.mpango.game.core.entity.Unit;
import net.sf.mpango.game.core.exception.CommandException;

/**
 * <p>Game Service that takes care of the interactions coming from the clients.</p>
 * @author edvera
 */
public interface IGameService {

    /**
     * Method that allows a User to join a game with a particular player name.
     * @param user {@User} behind the player.
     * @return {@Player} created after user joins.
     */
    Player join(User user);

    /**
     * Method that allows a User to settle a unit in a particular cell creating a city.
     * @param unit to settle.
     * @param cell Cell on which to build the city.
     * @throws CommandException in case it was impossible to execute the action.
     */
    void settleUnit(Unit unit, Cell cell) throws CommandException;
}
