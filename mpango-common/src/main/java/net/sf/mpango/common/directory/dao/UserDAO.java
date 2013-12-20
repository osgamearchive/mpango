package net.sf.mpango.common.directory.dao;

import java.util.List;

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
	User load(String email) throws UserNotFoundException;
	/**
	 * Method that loads the user using its reset key.
	 * @param resetKey
	 * @return
	 */
	User lookUpByResetKey(String resetKey) throws UserNotFoundException;

    /**
     * Method that loads the user using its identifier.
     * @param id
     * @return User with the id.
     */
    @Override
    User load(Long id) throws UserNotFoundException;
    /**
	 * Method that saves the user and returns it with the identifier assigned.
	 * @param user
	 * @return
	 */
    @Override
	User save(User user) throws UserAlreadyExistsException;
	/**
	 * Method that updates the user.
	 * @param user
	 */
    @Override
	void update(User user) throws UserNotFoundException;
	/**
	 * Method that deletes the user.
	 * @param user
	 */
    @Override
	void delete(User user) throws UserNotFoundException;
	/**
	 * Method that lists all users.
	 * @return
	 */
	List<User> list();
}
