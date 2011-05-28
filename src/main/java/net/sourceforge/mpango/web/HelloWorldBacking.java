package net.sourceforge.mpango.web;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.model.SelectItem;

import net.sourceforge.mpango.dto.UserDTO;
import net.sourceforge.mpango.facade.AuthenticationFacade;
import net.sourceforge.mpango.facade.IAuthenticationFacade;

/**
 * @author edvera
 */
public class HelloWorldBacking {

	private IAuthenticationFacade authFacade = new AuthenticationFacade();

	UserDTO user = new UserDTO();
	// private String username;
	// private String email;
	// private Long identifier;
	private String message;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HelloWorldBacking() {
		System.out.println("Creating instance of " + HelloWorldBacking.class);
	}

	/**
	 * Method that is backed to a submit button of a form.
	 */
	public String send() {
		UserDTO userFound = authFacade.load(user.getEmail());
		if (userFound != null) {
			message = "User already exists with that email address.";
			return "failure";
		} else {
			user = authFacade.save(user);
			message = "User registered succesfully";
			return "success";
		}

	}

	// -------------------getter & setter

	public ArrayList<SelectItem> getCountries() {
		countries = new ArrayList<SelectItem>();
		countries.add(new SelectItem("Country1", "Country 1"));
		countries.add(new SelectItem("Country2", "Country 2", ""));
		countries.add(new SelectItem("Country3", "Country 3", ""));
		countries.add(new SelectItem("Country4", "Country 4", ""));
		return countries;
	}

}