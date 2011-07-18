/**
 * 
 */
package net.sf.mpango.common.directory.facade;

import java.util.List;

import net.sf.mpango.common.directory.builder.UserBuilder;
import net.sf.mpango.common.directory.dto.UserDTO;
import net.sf.mpango.common.directory.factory.UserFactory;
import net.sf.mpango.common.directory.service.IAuthenticationService;

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
}
