/**
 * 
 */
package net.sf.mpango.common.directory.facade;

import java.util.Calendar;

import net.sf.mpango.common.directory.dto.UserDTO;
import net.sf.mpango.common.directory.enums.StateEnum;
import net.sf.mpango.common.directory.facade.IAuthenticationFacade;
import net.sf.mpango.common.test.BaseSpringTest;

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
		dto.setState(StateEnum.CREATED);
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
