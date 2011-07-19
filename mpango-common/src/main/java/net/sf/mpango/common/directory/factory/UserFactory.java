package net.sf.mpango.common.directory.factory;

import net.sf.mpango.common.directory.dto.UserDTO;
import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.entity.BaseFactory;

/**
 * @author aplause
 * 
 */
public class UserFactory extends BaseFactory<UserDTO, User> {

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.directory.factory.BaseFactory#create(net.sourceforge
	 * .mpango.dto.BaseDTO)
	 */
	public User create(UserDTO dto) {
		User user = new User();
		user.setIdentifier(dto.getId());
		user.setUsername(dto.getUsername());
		user.setEmail(dto.getEmail());
		user.setDateOfBirth(dto.getDateOfBirth());
		user.setGender(dto.getGender());
		user.setPassword(dto.getPassword());
		user.setState(dto.getState());
		return user;
	}
}
