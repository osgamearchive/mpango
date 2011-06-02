/**
 * 
 */
package net.sourceforge.mpango.directory.facade;

import java.util.Calendar;

import net.sourceforge.mpango.BaseSpringTest;
import net.sourceforge.mpango.directory.dto.UserDTO;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author aplause
 * 
 */
public class AuthenticationFacadeTest extends BaseSpringTest {

	@Autowired
	IAuthenticationFacade authFacade;

	@Test
	public void authenticationTest() {
		UserDTO dto = new UserDTO();
		dto.setDateOfBirth(Calendar.getInstance().getTime());
		dto.setEmail("User@company.com");
		dto.setGender("male");
		dto.setPassword("pwd");
		dto.setUsername("user");
		dto = authFacade.register(dto);
		Assert.assertNotNull(dto);
		Assert.assertNotNull(dto.getId());

		UserDTO userFound = authFacade.load("nonexisted@email.com");
		Assert.assertNull(userFound);
		userFound = authFacade.load("User@company.com");
		Assert.assertNotNull(userFound);
		Assert.assertEquals(userFound.getUsername(), dto.getUsername());

	}
}
