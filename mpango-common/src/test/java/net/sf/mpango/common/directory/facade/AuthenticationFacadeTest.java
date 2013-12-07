/**
 * 
 */
package net.sf.mpango.common.directory.facade;

import net.sf.mpango.common.directory.dto.UserDTO;
import net.sf.mpango.common.test.BaseSpringTest;
import net.sf.mpango.common.test.CommonTestUtils;
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
        final String email = "User@company.com";
        UserDTO dto = CommonTestUtils.getUserDTO(email);
        dto = authFacade.register(dto);
        Assert.assertNotNull(dto);
        Assert.assertNotNull(dto.getId());

        UserDTO userFound = authFacade.load("nonexisted@email.com");
        Assert.assertNull(userFound);

        userFound = authFacade.load(email);
		Assert.assertNotNull(userFound);
		Assert.assertEquals(userFound.getUsername(), dto.getUsername());
	}
}
