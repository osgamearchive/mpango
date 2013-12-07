/**
 * 
 */
package net.sf.mpango.common.directory.service;

import java.util.Calendar;

import net.sf.mpango.common.directory.dao.UserDAO;
import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.test.BaseSpringTest;

import org.easymock.classextension.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author aplause
 * 
 */
public class AuthenticationServiceTest extends BaseSpringTest {

	@Autowired
	AuthenticationService authService;

	@Test
	public void authenticationTest() {
		User user = new User();
		user.setDateOfBirth(Calendar.getInstance().getTime());
		user.setEmail("User@company.com");
		user.setGender(User.Gender.MALE);
		user.setPassword("pwd");
		user.setUsername("user");
		user = authService.register(user);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());

		User userFound = authService.load("nonexisted@email.com");
		Assert.assertNull(userFound);
		userFound = authService.load("User@company.com");
		Assert.assertNotNull(userFound);
		Assert.assertEquals(userFound.getUsername(), user.getUsername());
	}
	
	@Test
	public void testGenerateResetKey() {
		String email = "email@domain.com";
		User user = EasyMock.createMock(User.class);
		UserDAO userDAO = EasyMock.createMock(UserDAO.class);
		authService.setUserDAO(userDAO);
		EasyMock.expect(userDAO.load(email)).andReturn(user);
		user.setResetKey(EasyMock.isA(String.class));
		userDAO.update(user);
		EasyMock.replay(userDAO);
		String resetKey = authService.generateResetKey(email);
		EasyMock.verify(userDAO);
		Assert.assertNotNull(resetKey);
		
	}

}
