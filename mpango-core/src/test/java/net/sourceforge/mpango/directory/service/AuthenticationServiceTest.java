/**
 * 
 */
package net.sourceforge.mpango.directory.service;

import java.util.Calendar;

import net.sourceforge.mpango.BaseSpringTest;
import net.sourceforge.mpango.directory.entity.User;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author aplause
 *
 */
public class AuthenticationServiceTest extends BaseSpringTest{
	
	@Autowired
	IAuthenticationService authService;
	
	@Test
	public void authenticationTest() {
		User user  = new User();
		user.setDateOfBirth(Calendar.getInstance().getTime());
		user.setEmail("User@company.com");
		user.setGender("male");
		user.setPassword("pwd");
		user.setUsername("user");
		user = authService.register(user);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getIdentifier());
		
		User userFound = authService.load("nonexisted@email.com");
		Assert.assertNull(userFound);
		userFound = authService.load("User@company.com");
		Assert.assertNotNull(userFound);
		Assert.assertEquals(userFound.getUsername(), user.getUsername());
		
	}
	
}
