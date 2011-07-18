package net.sf.mpango.game.core.service;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.game.core.entity.GameContext;
import net.sf.mpango.game.core.entity.Player;

/**
 * Interface of the service that deals with player operations.
 * @author etux
 *
 */
public interface IPlayerService {

	/**
	 * Method that registers a player in the game context.
	 * @param user
	 * @param context
	 * @return TODO
	 */
	Player register(User user, GameContext context);
}
