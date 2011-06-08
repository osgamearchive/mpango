/**
 * 
 */
package net.sourceforge.mpango.directory.facade;

import java.util.Calendar;

import net.sourceforge.mpango.BaseSpringTest;
import net.sourceforge.mpango.TestUtils;
import net.sourceforge.mpango.directory.dto.UserDTO;
import net.sourceforge.mpango.dto.PlayerDTO;
import net.sourceforge.mpango.enums.StateEnum;
import net.sourceforge.mpango.exception.PlayerAlreadyExistsException;

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
	
	@Test()
	public void createPlayerTest() throws PlayerAlreadyExistsException {
		UserDTO userDTO = new UserDTO();
		userDTO.setDateOfBirth(Calendar.getInstance().getTime());
		userDTO.setEmail("User@company.com");
		userDTO.setGender("male");
		userDTO.setPassword("pwd");
		userDTO.setUsername("user");
		userDTO = authFacade.register(userDTO);

		PlayerDTO dto1 = TestUtils.getPlayerDTO();
		dto1 = authFacade.createPlayer(userDTO, dto1);
		Assert.assertNotNull(dto1.getId());

		//authFacade.delete(dto1);
		
		//dto1 = TestUtils.getPlayerDTO();
		//dto1 = authFacade.createPlayer(userDTO, dto1);
		
		//PlayerDTO dto2 = TestUtils.getPlayerDTO();
		// here exception should be raised 
		//dto2 = authFacade.createPlayer(userDTO, dto2);
	}
}
