package net.sourceforge.mpango.battle.builder;

import net.sourceforge.mpango.dto.UserDTO;
import net.sourceforge.mpango.entity.User;

/**
 * 
 * @author aplause
 * 
 */
public class UserBuilder {

	private UserBuilder() {
		super();
	}

	/**
	 * returns new instance of builder
	 * 
	 * @return {@link UserBuilder}
	 */
	public static UserBuilder instance() {
		return new UserBuilder();

	}

	/**
	 * builds dto object from entity
	 * 
	 * @param user
	 * @return {@link UserDTO}
	 */
	public UserDTO build(User user) {
		UserDTO dto = new UserDTO();
		dto.setUserId(user.getIdentifier());
		dto.setUsername(user.getUsername());
		dto.setEmail(user.getEmail());

		return dto;
	}
}
