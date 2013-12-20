package net.sourceforge.mpango.web.directory;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.service.AuthenticationException;
import net.sf.mpango.common.directory.service.IAuthenticationService;

/**
 * @author edvera
 */
public class AuthenticationBackingBean {

    protected static final String SEND_ACTION_SUCCESS = "success";
    protected static final String SEND_ACTION_FAILURE = "failure";
    private IAuthenticationService authService;

    private User user = new User();
    private List<User> users;
    private ArrayList<SelectItem> countries;

	public List<User> getUsers() {
		if (users == null)  {
			users = authService.list();
		}
		return users;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Method that is backed to a submit button of a form.
	 */
	public String send() {
        try {
            authService.register(user);
            return SEND_ACTION_SUCCESS;
        } catch (AuthenticationException e) {
            return SEND_ACTION_FAILURE;
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

    public IAuthenticationService getAuthService() {
        return authService;
    }

    public void setAuthService(IAuthenticationService authService) {
        this.authService = authService;
    }

}