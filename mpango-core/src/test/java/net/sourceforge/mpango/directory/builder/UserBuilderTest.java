package net.sourceforge.mpango.directory.builder;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.sourceforge.mpango.directory.builder.UserBuilder;
import net.sourceforge.mpango.directory.dto.UserDTO;
import net.sourceforge.mpango.directory.entity.User;

/**
 * 
 * @author aplause
 *
 */
public class UserBuilderTest extends TestCase{

	/*
	 * very simple test
	 */
	public void testUserBuilder() {
		User user = new User();
		user.setIdentifier(1L);
		user.setUsername("test");
		user.setEmail("test@company.com");
		
		UserDTO dto = UserBuilder.instance().build(user);
		Assert.assertNotNull(dto);
		Assert.assertEquals(dto.getId(), user.getIdentifier());
		Assert.assertEquals(dto.getUsername(), user.getUsername());
		Assert.assertEquals(dto.getEmail(), user.getEmail());
	}
}
