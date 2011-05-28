package net.sourceforge.mpango.service;

import java.util.List;

import net.sourceforge.mpango.entity.User;

public interface IAuthenticationService {
	/**
	 * finds {link @User} by email
	 * 
	 * @param email
	 * @return {link @User}
	 */
	public User load(String email);

	/**
	 * persists {@link UserDTO} in database
	 * 
	 * @param user
	 * @return {link @UserDTO}
	 */
	public User save(User user);

	/**
	 * updates {@link User} in database
	 * 
	 * @param user
	 */
	public void update(User user);

	/**
	 * deletes {@link User} from database
	 * 
	 * @param user
	 */
	public void delete(User user);

	/**
	 * gets {@link List} of {@link User}
	 * 
	 * @return
	 */
	public List<User> list();
}
