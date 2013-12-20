package net.sourceforge.mpango.web.directory;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.service.AuthenticationException;
import net.sf.mpango.common.directory.service.IAuthenticationService;

@ManagedBean(name="loginBackingBean")
@SessionScoped
public class LoginBackingBean {
    
    private static final Logger LOGGER = Logger.getLogger(LoginBackingBean.class.getName());

    private IAuthenticationService authService;
	private User user;

	public String login(String email, String password) {
        
        assert authService != null;
        assert email != null;
        
		String action = "";
        try {
            user = authService.login(email, password);
            LOGGER.log(Level.INFO, "User {0} is logged in", user.getEmail());
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
            HttpSession httpSession = request.getSession(false);
            httpSession.setAttribute("user", user);
            action = "success";
        } catch (AuthenticationException e) {
            LOGGER.log(Level.INFO, "The user is either unknown or didn't provide the right credentials");
            action = "fail";
        }
		return action;
	}
	
	public boolean isLoggedIn() {
		return user != null;
	}
	
	public String getEmail() {
		return user.getEmail();
	}

	public void setEmail(String email) {
		user.setEmail(email);
	}

	public String getPassword() {
        return user.getPassword();
	}

	public void setPassword(String password) {
		user.setPassword(password);
	}

	public IAuthenticationService getAuthService() {
		return authService;
	}

	public void setAuthService(IAuthenticationService authService) {
		this.authService = authService;
	}
}
