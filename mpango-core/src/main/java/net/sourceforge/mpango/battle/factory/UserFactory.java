package net.sourceforge.mpango.battle.factory;

import net.sourceforge.mpango.directory.entity.User;
import net.sourceforge.mpango.dto.UserDTO;


/**
 * @author aplause
 *
 */
public class UserFactory {
	
	private UserFactory() {
		super();
	}

	/**
	 * returns new instance of factory
	 * 
	 * @return
	 */
	public static UserFactory instance() {
		return new UserFactory();

	}
	
	/**
	 * creates {@link User} entity from dto object
	 * 
	 * @param user
	 * @return {@link UserDTO}
	 */
	public User create(UserDTO dto) {
		User user = new User();
		user.setIdentifier(dto.getUserId());
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setDateOfBirth(dto.getDateOfBirth());
		user.setGender(dto.getGender());
		user.setPassword(dto.getPassword());

		return user;
	}
}
