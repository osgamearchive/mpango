package net.sourceforge.mpango.directory.builder;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.mpango.directory.entity.User;
import net.sourceforge.mpango.dto.UserDTO;

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

		if (null == user) {
			return null;
		}

		UserDTO dto = new UserDTO();
		dto.setUserId(user.getIdentifier());
		dto.setUsername(user.getUsername());
		dto.setPassword(user.getPassword());
		dto.setEmail(user.getEmail());
		dto.setDateOfBirth(user.getDateOfBirth());
		dto.setGender(user.getGender());

		return dto;
	}

	/**
	 * builds {@link List} of {@link UserDTO}
	 * 
	 * @param userList
	 * @return {@link List} of {@link UserDTO}
	 */
	public List<UserDTO> buildList(List<User> userList) {
		List<UserDTO> dtoList = new ArrayList<UserDTO>();

		for (User user : userList) {
			dtoList.add(build(user));
		}

		return dtoList;

	}
}
