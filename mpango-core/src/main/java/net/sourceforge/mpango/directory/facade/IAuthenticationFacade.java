package net.sourceforge.mpango.directory.facade;

import java.util.List;

import net.sourceforge.mpango.directory.dto.UserDTO;

public interface IAuthenticationFacade {

	/**
	 * finds {link @UserDTO} by email
	 * 
	 * @param email
	 * @return {link @UserDTO}
	 */
	public UserDTO load(String email);

	/**
	 * persists {@link UserDTO} in database
	 * 
	 * @param user
	 * @return {link @UserDTO}
	 */
	public UserDTO save(UserDTO user);

	/**
	 * updates {@link UserDTO} in database
	 * 
	 * @param user
	 */
	public void update(UserDTO user);

	/**
	 * deletes {@link UserDTO} from database
	 * 
	 * @param user
	 */
	public void delete(UserDTO user);

	/**
	 * gets {@link List} of {@link UserDTO}
	 * 
	 * @return
	 */
	public List<UserDTO> list();
}
