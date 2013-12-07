package net.sourceforge.mpango.web.directory;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import net.sf.mpango.common.directory.dto.UserDTO;
import net.sf.mpango.common.directory.facade.IAuthenticationFacade;

/**
 * @author edvera
 */
public class AuthenticationBackingBean {

	private IAuthenticationFacade authFacade;

	private UserDTO user = new UserDTO();
	private List<UserDTO> users;
	
	public List<UserDTO> getUsers() {
		if (users == null)  {
			users = authFacade.list();
		}
		return users;
	}

	private ArrayList<SelectItem> countries;

	public IAuthenticationFacade getAuthFacade() {
		return authFacade;
	}

	public void setAuthFacade(IAuthenticationFacade authFacade) {
		this.authFacade = authFacade;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	/**
	 * Method that is backed to a submit button of a form.
	 */
	public String send() {
		UserDTO userFound = authFacade.load(user.getEmail());
		if (userFound != null) {
			user = userFound;
			return "failure";
		} else {
			user = authFacade.register(user);
			return "success";
		}

	}

	public ArrayList<SelectItem> getCountries() {
		countries = new ArrayList<SelectItem>();
		countries.add(new SelectItem("Country1", "Country 1"));
		countries.add(new SelectItem("Country2", "Country 2", ""));
		countries.add(new SelectItem("Country3", "Country 3", ""));
		countries.add(new SelectItem("Country4", "Country 4", ""));
		return countries;
	}

}