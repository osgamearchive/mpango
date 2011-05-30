/**
 * 
 */
package net.sourceforge.mpango.directory.facade;

import net.sourceforge.mpango.directory.builder.UserBuilder;
import net.sourceforge.mpango.directory.dto.UserDTO;
import net.sourceforge.mpango.directory.factory.UserFactory;
import net.sourceforge.mpango.directory.service.IAuthenticationService;

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

}
