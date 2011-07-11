package net.sourceforge.mpango.directory.dao;

import java.util.List;

import net.sourceforge.mpango.directory.entity.User;
import net.sourceforge.mpango.entity.Player;

/**
 * Data Access Object Interface for {@link User}.
 * 
 * @author etux
 * 
 */
public interface UserDAO {

	public User load(String email);
	
	public User lookUpByResetKey(String resetKey);
	
	public User save(User user);

	public void update(User user);

	public void delete(User user);

	public List<User> list();

	/**
	 * persists or updates {@link Player} data to database
	 * 
	 * @param player
	 *            {@link Player}
	 * @return {@link Player}
	 */
	public Player save(Player player);
	
	/**
	 * finds list of {@link Player} connected with {@link User} where state!=DELETED
	 * @param user {@link User}
	 * @return {@link List} of {@link Player}
	 */
	public List<Player> findPlayersByUser(User user);
}
