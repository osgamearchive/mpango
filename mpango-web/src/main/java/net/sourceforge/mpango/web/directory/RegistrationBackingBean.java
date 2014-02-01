package net.sourceforge.mpango.web.directory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.model.SelectItem;

import net.sf.mpango.directory.entity.User;
import net.sf.mpango.directory.service.AuthenticationException;
import net.sf.mpango.directory.service.IAuthenticationService;
import net.sf.mpango.common.utils.LocalizedMessageBuilder;

/**
 * @author edvera
 */
public class RegistrationBackingBean {

    private Logger LOGGER = Logger.getLogger(RegistrationBackingBean.class.getName());

    protected static final String SEND_ACTION_SUCCESS = "success";
    protected static final String SEND_ACTION_FAILURE = "failure";
    private IAuthenticationService authService;

    private User user = new User();
    private List<User> users;
    private List<SelectItem> countries;

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
	public String registerUser() {
        try {
            authService.register(user);
            LOGGER.log(
                    Level.FINEST,
                    LocalizedMessageBuilder.getSystemMessage(this, MessageConstants.USER_REGISTRATION_SUCCESSFUL),
                    user.getEmail());
            return SEND_ACTION_SUCCESS;
        } catch (final AuthenticationException e) {
            LOGGER.log(
                    Level.INFO,
                    LocalizedMessageBuilder.getSystemMessage(this, MessageConstants.USER_REGISTRATION_UNSUCCESFUL), e);
            return SEND_ACTION_FAILURE;
        }
	}

	public List<SelectItem> getCountries() {
		countries = new ArrayList<>();
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