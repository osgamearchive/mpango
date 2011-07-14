package net.sourceforge.mpango.web.directory;

import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import net.sourceforge.mpango.directory.dao.UserDAO;
import net.sourceforge.mpango.directory.entity.User;
import net.sourceforge.mpango.directory.service.ApplicationContextService;

import org.springframework.context.ApplicationContext;

@ManagedBean(name="changePasswordBackingBean")
@RequestScoped
public class ChangePasswordBackingBean {

	private static ApplicationContext appContext = ApplicationContextService.getApplicationContext();

	private String email;
	private String newPassword;
	private String retypePassword;
	private boolean passwordChangedFlag = false;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	public boolean isPasswordChangedFlag() {
		return passwordChangedFlag;
	}

	public void setPasswordChangedFlag(boolean passwordChangedFlag) {
		this.passwordChangedFlag = passwordChangedFlag;
	}

	//get value from "f:param"
	private String getResetParam(FacesContext fc){
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		return params.get("reset_key");
	}

	public void updatePassword() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String resetKey = getResetParam(fc);
		UserDAO userDAO = (UserDAO) appContext.getBean("userDAO");
		User user = userDAO.lookUpByResetKey(resetKey);
		System.out.println(user.getPassword());
		
		if (user != null) {
			if (newPassword.equalsIgnoreCase(retypePassword)) {
				user.setPassword(newPassword);
				userDAO.update(user);
				passwordChangedFlag = true;

				System.out.println(user.getPassword());
			}
		}
		
		// clear properties so that they are not populated on the form
		newPassword = null;
		retypePassword = null;
	}
}