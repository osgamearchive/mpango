package net.sourceforge.mpango.web.directory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.service.IAuthenticationService;

@ManagedBean(name="loginBackingBean")
@SessionScoped
public class LoginBackingBean {
	
	private IAuthenticationService authService;
	private String email;
	private String password;
	private boolean loggedIn = false;

	public String login() {
		String action = "";
		User user = authService.load(email);
		if (user != null) {
			
			System.out.println(user.getEmail());
			System.out.println(user.getPassword());
			
			if (password.equals(user.getPassword())) {
				FacesContext context = FacesContext.getCurrentInstance();  
				HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
				HttpSession httpSession = request.getSession(false);  
				httpSession.setAttribute("user", user);  
				action = "success";
				loggedIn = true;
			} else {
				action = "fail";
			}
		} else {
			action = "fail";
		}
		
		return action;
	}
	
	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public IAuthenticationService getAuthService() {
		return authService;
	}

	public void setAuthService(IAuthenticationService authService) {
		this.authService = authService;
	}
}
