package net.sf.mpango.game.core.dao;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.game.core.entity.Player;

public interface PlayerDAO {


	/**
	 * Saves {@link Player} data to database
	 * 
	 * @param player {@link Player}
	 * @return {@link Player}
	 */
	public Player save(Player player);
	
	/**
	 * Updates {@link Player} data to database
	 * @param player {@link Player}
	 */
	public void update(Player player);
	
	/**
	 * Finds a {@link Player} connected with {@link User} where state!=DELETED
	 * @param user {@link User}
	 * @return If user has previously joined the game, then return {@link Player} otherwise return null.
	 */
	public Player findPlayer(User user);
}
