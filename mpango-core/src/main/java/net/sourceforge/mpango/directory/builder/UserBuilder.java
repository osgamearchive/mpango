package net.sourceforge.mpango.directory.builder;

import net.sourceforge.mpango.directory.dto.UserDTO;
import net.sourceforge.mpango.directory.entity.User;

/**
 * 
 * @author aplause
 * 
 */
public class UserBuilder extends BaseBuilder<User, UserDTO> {

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.directory.builder.BaseBuilder#build(java.lang.
	 * Object)
	 */
	public UserDTO build(User user) {

		if (null == user) {
			return null;
		}

		UserDTO dto = new UserDTO();
		dto.setId(user.getIdentifier());
		dto.setUsername(user.getUsername());
		dto.setPassword(user.getPassword());
		dto.setEmail(user.getEmail());
		dto.setDateOfBirth(user.getDateOfBirth());
		dto.setGender(user.getGender());

		return dto;
	}

}
