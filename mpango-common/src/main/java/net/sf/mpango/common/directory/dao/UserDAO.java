package net.sf.mpango.common.directory.dao;

import java.util.List;

import net.sf.mpango.common.directory.entity.User;

/**
 * Data Access Object Interface for {@link User}.
 * 
 * @author etux
 * 
 */
public interface UserDAO {

	/**
	 * Method that loads the user using its identifier.
	 * @param id
	 * @return User with the id.
	 */
	public User load(Long id);
	/**
	 * Method that loads the user using its email address.
	 * @param email
	 * @return
	 */
	public User load(String email);
	/**
	 * Method that loads the user using its reset key.
	 * @param resetKey
	 * @return
	 */
	public User lookUpByResetKey(String resetKey);
	/**
	 * Method that saves the user and returns it with the identifier assigned.
	 * @param user
	 * @return
	 */
	public User save(User user);
	/**
	 * Method that updates the user.
	 * @param user
	 */
	public void update(User user);
	/**
	 * Method that deletes the user.
	 * @param user
	 */
	public void delete(User user);
	/**
	 * Method that lists all users.
	 * @return
	 */
	public List<User> list();
}
