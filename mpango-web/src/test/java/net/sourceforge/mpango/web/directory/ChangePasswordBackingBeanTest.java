package net.sourceforge.mpango.web.directory;

import java.util.UUID;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.service.AuthenticationException;
import net.sf.mpango.common.directory.service.IAuthenticationService;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class ChangePasswordBackingBeanTest {

    @Mock
	private IAuthenticationService authService;
    private String resetKey;
    private String newPassword;
    private String email;

    private ChangePasswordBackingBean testing;

	@Before
	public void setUp() {
        MockitoAnnotations.initMocks(this);
        resetKey = UUID.randomUUID().toString();
        newPassword = RandomStringUtils.random(16);
        email = "user@domain.com";
        testing = new ChangePasswordBackingBean();
		testing.setAuthenticationService(authService);
        testing.setEmail(email);
        testing.setNewPassword(newPassword);
        testing.setRetypePassword(newPassword);
        testing.setResetKey(resetKey);
	}

    @Test
    public void changePassword_with_resetKey() throws AuthenticationException {
        final User user = TestUtils.createUser(email, resetKey);
        Mockito.when(authService.getUserByResetKey(resetKey)).thenReturn(user);

        final String result = testing.changePasswordWithResetKey();

        assertThat(result, is(equalTo(ChangePasswordBackingBean.CHANGE_PASSWORD_SUCCESS)));
        Mockito.verify(authService).getUserByResetKey(resetKey);
        Mockito.verify(authService).changeUserPassword(user, newPassword);
    }

    @Test
    public void changePassword_with_resetKey_differentPasswords() throws AuthenticationException {
        //Overwrite the default reset password to make sure it is different.
        testing.setRetypePassword(RandomStringUtils.random(16));

        final String result = testing.changePasswordWithResetKey();

        assertThat(result, is(equalTo(ChangePasswordBackingBean.CHANGE_PASSWORD_FAILURE)));
        Mockito.verifyZeroInteractions(authService);
    }
	
}
