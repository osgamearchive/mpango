package net.sf.mpango.common.directory.dao;

import net.sf.mpango.common.dao.DAO;
import net.sf.mpango.common.directory.entity.User;

/**
 * Data Access Object Interface for {@link User}.
 * 
 * @author etux
 * 
 */
public interface UserDAO extends DAO<User, Long> {

	/**
	 * Method that loads the user using its email address.
	 * @param email
	 * @return
	 */
	User loadByEmail(String email) throws UserNotFoundException;
	/**
	 * Method that loads the user using its reset key.
	 * @param resetKey
	 * @return
	 */
	User lookUpByResetKey(String resetKey) throws UserNotFoundException;
}
