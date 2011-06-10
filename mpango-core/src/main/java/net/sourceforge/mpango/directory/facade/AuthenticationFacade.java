/**
 * 
 */
package net.sourceforge.mpango.directory.facade;

import java.util.List;

import net.sourceforge.mpango.builder.PlayerBuilder;
import net.sourceforge.mpango.directory.builder.UserBuilder;
import net.sourceforge.mpango.directory.dto.UserDTO;
import net.sourceforge.mpango.directory.factory.PlayerFactory;
import net.sourceforge.mpango.directory.factory.UserFactory;
import net.sourceforge.mpango.directory.service.IAuthenticationService;
import net.sourceforge.mpango.dto.PlayerDTO;
import net.sourceforge.mpango.exception.PlayerAlreadyExistsException;

/**
 * @author aplause
 * 
 */
public class AuthenticationFacade implements IAuthenticationFacade {

	IAuthenticationService authService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.facade.IAuthenticationFacade#load(java.lang.String
	 * )
	 */
	@Override
	public UserDTO load(String email) {
		return UserBuilder.instance().build(authService.load(email));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.facade.IAuthenticationFacade#save(net.sourceforge
	 * .mpango.dto.UserDTO)
	 */
	@Override
	public UserDTO register(UserDTO dto) {
		return UserBuilder.instance().build(
				authService.register(UserFactory.instance().create(dto)));
	}

	public IAuthenticationService getAuthService() {
		return authService;
	}

	public void setAuthService(IAuthenticationService authService) {
		this.authService = authService;
	}

	@Override
	public List<UserDTO> list() {
		List<UserDTO> users = null;
		users = UserBuilder.instance().buildList(authService.list());
		return users;
	}

	@Override
	public PlayerDTO createPlayer(UserDTO user, PlayerDTO player)
			throws PlayerAlreadyExistsException {
		return PlayerBuilder.instance().build(
				authService.createPlayer(UserFactory.instance().create(user),
						PlayerFactory.instance().create(player)));
	}

	@Override
	public void delete(PlayerDTO player) {
		authService.delete(PlayerFactory.instance().create(player));

	}
}
