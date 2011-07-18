package net.sf.mpango.game.core.dao;

import java.util.List;

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
	 * finds list of {@link Player} connected with {@link User} where state!=DELETED
	 * @param user {@link User}
	 * @return {@link List} of {@link Player}
	 */
	public List<Player> findPlayersByUser(User user);
}
