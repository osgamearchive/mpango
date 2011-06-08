package net.sourceforge.mpango.directory.factory;

import net.sourceforge.mpango.directory.dto.UserDTO;
import net.sourceforge.mpango.directory.entity.User;
import net.sourceforge.mpango.entity.Player;

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

		// players
		if (null != dto.getPlayerList()) {
			user.setPlayerList(PlayerFactory.instance().createList(
					dto.getPlayerList()));
			for (Player player : user.getPlayerList()) {
				player.setUser(user);
			}
		}

		return user;
	}
}
