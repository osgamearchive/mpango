package net.sourceforge.mpango.directory.service;


import java.util.List;

import net.sourceforge.mpango.directory.entity.User;

/**
 * Authentication service Interface with the different operations available.
 * @author etux
 *
 */
public interface IAuthenticationService {
	
	/**
	 * Finds {@link User} by email.
	 * @param email
	 * @return Returns the {@link User} that is registered with the email address supplied. In case the user does not exist, then returns null.
	 */
	public User load(String email);

	/**
	 * Registers a {@link User} in the game.
	 * 
	 * @param user User to be registered.
	 * @return The {@link User} that has been registered. 
	 */
	public User register(User user);

	/**
	 * Returns the available users.
	 * @return
	 */
	public List<User> list();
}
