package net.sourceforge.mpango.web.directory;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.service.AuthenticationException;
import net.sf.mpango.common.directory.service.IAuthenticationService;

import org.apache.commons.lang.RandomStringUtils;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class ChangePasswordBackingBeanTest {

	private ChangePasswordBackingBean testing;
	private IAuthenticationService authService;
	
	@Before
	public void setUp() {
		testing = new ChangePasswordBackingBean();
		authService = EasyMock.createMock(IAuthenticationService.class);
		testing.setAuthenticationService(authService);
	}
	
	@Test
	public void testChangePassword() throws AuthenticationException {
		String resetKey = RandomStringUtils.random(12);
		String newPassword = RandomStringUtils.random(12);
		String email = "email@domain.com";
		User user = new User();
		user.setEmail(email);
		testing.setEmail(email);
		testing.setNewPassword(newPassword);
		testing.setRetypePassword(newPassword);
		testing.setResetKey(resetKey);
		EasyMock.expect(authService.getUserByResetKey(resetKey)).andReturn(user);
		authService.changeUserPassword(user, newPassword);
		EasyMock.replay(authService);
		testing.changePasswordWithResetKey();
		EasyMock.verify(authService);
	}
	
}
