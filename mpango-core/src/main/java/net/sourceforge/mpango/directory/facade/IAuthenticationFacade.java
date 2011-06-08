package net.sourceforge.mpango.directory.facade;

import java.util.List;

import net.sourceforge.mpango.directory.dto.UserDTO;
import net.sourceforge.mpango.dto.PlayerDTO;
import net.sourceforge.mpango.exception.PlayerAlreadyExistsException;

public interface IAuthenticationFacade {

	/**
	 * finds {@link UserDTO} by email
	 * 
	 * @param email
	 * @return {@link UserDTO}
	 */
	public UserDTO load(String email);

	/**
	 * persists {@link UserDTO} in database
	 * 
	 * @param user
	 * @return {@link UserDTO}
	 */
	public UserDTO register(UserDTO user);

	/**
	 * Method that list all available users.
	 * 
	 * @return
	 */
	public List<UserDTO> list();

	/**
	 * creates {@link PlayerDTO} connected with {@link UserDTO}
	 * @param user
	 * @param player
	 * @return
	 * @throws PlayerAlreadyExistsException
	 */
	public PlayerDTO createPlayer(UserDTO user, PlayerDTO player)
			throws PlayerAlreadyExistsException;

	/**
	 * deletes {@link PlayerDTO} by setting its state as DELETED
	 * @param player
	 */
	public void delete(PlayerDTO player);
}
