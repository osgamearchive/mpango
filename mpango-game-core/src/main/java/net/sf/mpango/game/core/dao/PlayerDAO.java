package net.sf.mpango.game.core.dao;

import net.sf.mpango.common.dao.DAO;
import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.game.core.entity.Player;

public interface PlayerDAO extends DAO<Player, Long> {


    /**
	 * Finds a {@link Player} connected with {@link User} where state!=DELETED
	 * @param user {@link User}
	 * @return If user has previously joined the game, then return {@link Player} otherwise return null.
	 */
	public Player findPlayer(User user);
}
