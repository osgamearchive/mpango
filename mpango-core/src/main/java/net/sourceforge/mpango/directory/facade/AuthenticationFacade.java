/**
 * 
 */
package net.sourceforge.mpango.directory.facade;

import java.util.List;

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

	public IAuthenticationService getAuthService() {
		return authService;
	}

	public void setAuthService(IAuthenticationService authService) {
		this.authService = authService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.facade.IAuthenticationFacade#delete(net.sourceforge
	 * .mpango.dto.UserDTO)
	 */
	@Override
	public void delete(UserDTO dto) {
		authService.delete(UserFactory.instance().create(dto));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sourceforge.mpango.facade.IAuthenticationFacade#list()
	 */
	@Override
	public List<UserDTO> list() {
		return UserBuilder.instance().buildList(authService.list());
	}

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
	public UserDTO save(UserDTO dto) {
		return UserBuilder.instance().build(
				authService.save(UserFactory.instance().create(dto)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.mpango.facade.IAuthenticationFacade#update(net.sourceforge
	 * .mpango.dto.UserDTO)
	 */
	@Override
	public void update(UserDTO dto) {

		authService.save(UserFactory.instance().create(dto));

	}

}
