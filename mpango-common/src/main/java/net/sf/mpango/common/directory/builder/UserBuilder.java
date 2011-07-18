package net.sf.mpango.common.directory.builder;

import net.sf.mpango.common.builder.BaseBuilder;
import net.sf.mpango.common.directory.dto.UserDTO;
import net.sf.mpango.common.directory.entity.User;

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
		dto.setState(user.getState());

		return dto;
	}

}
