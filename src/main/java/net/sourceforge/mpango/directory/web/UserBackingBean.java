package net.sourceforge.mpango.directory.web;

import java.util.List;

import net.sourceforge.mpango.dto.UserDTO;
import net.sourceforge.mpango.facade.AuthenticationFacade;

public class UserBackingBean {
	
	private List<UserDTO> users;
	private AuthenticationFacade authFacade;
	
	
	public List<UserDTO> getUsers() {
		return authFacade.list();
	}


	public AuthenticationFacade getAuthFacade() {
		return authFacade;
	}


	public void setAuthFacade(AuthenticationFacade authFacade) {
		this.authFacade = authFacade;
	}
	
	
	
}
