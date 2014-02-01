package net.sf.mpango.game.core.service;

import net.sf.mpango.directory.entity.User;
import net.sf.mpango.game.core.entity.GameBoard;
import net.sf.mpango.game.core.entity.Player;

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
     * Method that returns the board of the game.
     */
	GameBoard getBoard();
}
