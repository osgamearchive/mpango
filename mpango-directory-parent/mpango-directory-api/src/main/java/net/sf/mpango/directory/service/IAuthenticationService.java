package net.sf.mpango.directory.service;

import java.util.List;

import net.sf.mpango.directory.entity.User;

/**
 * Authentication service Interface with the different operations available.
 * 
 * @author etux
 * 
 */
public interface IAuthenticationService {

	/**
	 * Finds {@link User} by email.
	 * 
	 *
     * @param email
     * @param password
     * @return Returns the {@link User} that is registered with the email
	 *         address supplied.
     * @throws AuthenticationException in case the user doesn't exist of the password doesn't match the existing user.
	 */
	User login(String email, String password) throws AuthenticationException;

	/**
	 * Registers a {@link User} in the game.
	 * 
	 * @param user
	 *            User to be registered.
	 * @return The {@link User} that has been registered.
	 */
	User register(User user) throws AuthenticationException;

	/**
	 * Returns the available users.
	 * 
	 * @return
	 */
	List<User> list();
	
	/**
	 * Method that generates a reset key for the user in order to reset the forgotten password.
	 * @param email
     * @throws AuthenticationException in case there isn't any user with the provided email address.
	 */
	String generateResetKey(String email) throws AuthenticationException;

	/**
	 * Method that returns the user associated to the reset key.
	 * @param resetKey
	 * @return
	 */
	User getUserByResetKey(String resetKey) throws AuthenticationException;
	
	/**
	 * Method used to change the password of the given user.
	 * @param user
	 * @param newPassword
	 */
	void changeUserPassword(User user, String newPassword) throws AuthenticationException;
}