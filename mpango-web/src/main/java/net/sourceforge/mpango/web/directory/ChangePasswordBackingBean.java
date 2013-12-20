package net.sourceforge.mpango.web.directory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.service.AuthenticationException;
import net.sf.mpango.common.directory.service.IAuthenticationService;

@ManagedBean(name="changePasswordBackingBean")
@RequestScoped
public class ChangePasswordBackingBean {

	
	private String email;
	private String newPassword;
	private String retypePassword;
	private String resetKey;
	private IAuthenticationService authenticationService;
	private boolean passwordChangedFlag = false;
	
	/**
	 * Method that first obtains a user based on a reset key and then proceeds changing his password.
	 */
	public void changePasswordWithResetKey() {
		User user = null;
		if ((user = checkAccount(resetKey)) != null) {
			if (newPassword.equals(retypePassword)) {
                try {
                    authenticationService.changeUserPassword(user, newPassword);
                    setPasswordChangedFlag(true);
                } catch (AuthenticationException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
			}
		}
	}
	
	private User checkAccount(final String resetKey) {
        try {
            return authenticationService.getUserByResetKey(resetKey);
        } catch (final AuthenticationException exception) {
            return null;
        }

	}
	
	public String getResetKey() {
		return resetKey;
	}

	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}

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

	public IAuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	public void setAuthenticationService(
			IAuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
}