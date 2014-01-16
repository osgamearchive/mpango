package net.sourceforge.mpango.web.directory;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.service.AuthenticationException;
import net.sf.mpango.common.directory.service.IAuthenticationService;
import net.sf.mpango.common.utils.LocalizedMessageBuilder;

@ManagedBean(name="changePasswordBackingBean")
@RequestScoped
public class ChangePasswordBackingBean {

    private static final Logger LOGGER = Logger.getLogger(ChangePasswordBackingBean.class.getName());

    static final String CHANGE_PASSWORD_SUCCESS = "success";
    static final String CHANGE_PASSWORD_FAILURE = "failure";

    private String email;
	private String newPassword;
	private String retypePassword;
	private String resetKey;
	private IAuthenticationService authenticationService;
	private boolean passwordChangedFlag = false;
	
	/**
	 * Method that first obtains a user based on a reset key and then proceeds changing his password.
	 */
	public String changePasswordWithResetKey() {
        assert newPassword != null;
        assert retypePassword != null;

        try {
            if ((newPassword != null && newPassword.equals(retypePassword))) {
                final User user = authenticationService.getUserByResetKey(resetKey);
                authenticationService.changeUserPassword(user, newPassword);
                setPasswordChangedFlag(true);
                return CHANGE_PASSWORD_SUCCESS;
            } else {
                LOGGER.log(
                        Level.INFO,
                        LocalizedMessageBuilder.getSystemMessage(
                                this,
                                MessageConstants.USER_CHANGE_PASSWORD_FAILURE_DIFFERENT_PASSWORDS));
                return CHANGE_PASSWORD_FAILURE;
            }
        } catch (final AuthenticationException e) {
            LOGGER.log(
                    Level.INFO,
                    LocalizedMessageBuilder.getSystemMessage(
                            this,
                            MessageConstants.USER_CHANGE_PASSWORD_FAILURE), e);
            return CHANGE_PASSWORD_FAILURE;
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