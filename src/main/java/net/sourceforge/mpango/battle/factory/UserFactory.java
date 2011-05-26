package net.sourceforge.mpango.battle.factory;

import net.sourceforge.mpango.dto.UserDTO;
import net.sourceforge.mpango.entity.User;


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

		return user;
	}
}
