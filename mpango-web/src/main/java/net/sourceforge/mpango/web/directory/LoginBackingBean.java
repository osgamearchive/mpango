package net.sourceforge.mpango.web.directory;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.mpango.directory.entity.User;
import net.sf.mpango.directory.service.AuthenticationException;
import net.sf.mpango.directory.service.IAuthenticationService;
import net.sf.mpango.common.utils.LocalizedMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;

@ManagedBean(name="loginBackingBean")
@SessionScoped
public class LoginBackingBean {

    private static final Logger LOGGER = Logger.getLogger(LoginBackingBean.class.getName());

    private static final String ACTION_RESULT_SUCCESS = "success";
    private static final String ACTION_RESULT_FAILURE = "fail";
    private static final String HTTP_SESSION_ATTR_USER = "user";

    @Autowired
    private IAuthenticationService authService;

    private User user;
    private String email;
    private String password;

	public String login() {
        assert authService != null;
        assert email != null;
        assert password != null;

		String action = null;
        try {
            user = getAuthService().login(email, password);
            LOGGER.log(
                    Level.INFO,
                    LocalizedMessageBuilder.getSystemMessage(
                            this,
                            MessageConstants.USER_LOGIN_SUCCESSFUL,
                            user.getEmail()));
            final FacesContext context = FacesContext.getCurrentInstance();
            final HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            final HttpSession httpSession = request.getSession(false);
            httpSession.setAttribute(HTTP_SESSION_ATTR_USER, user);
            action = ACTION_RESULT_SUCCESS;
        } catch (final AuthenticationException e) {
            LOGGER.log(
                    Level.INFO,
                    LocalizedMessageBuilder.getSystemMessage(this, MessageConstants.USER_LOGIN_FAILURE_WRONG_CREDENTIALS)
                    , e);
            action = ACTION_RESULT_FAILURE;
        }
		return action;
	}
	
	public boolean isLoggedIn() {
		return user != null;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
        return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public IAuthenticationService getAuthService() {
		return authService;
	}

	public void setAuthService(IAuthenticationService authService) {
		this.authService = authService;
	}
}
