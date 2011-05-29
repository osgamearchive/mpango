package net.sourceforge.mpango.builder;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.sourceforge.mpango.battle.builder.UserBuilder;
import net.sourceforge.mpango.directory.entity.User;
import net.sourceforge.mpango.dto.UserDTO;

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
		Assert.assertEquals(dto.getUserId(), user.getIdentifier());
		Assert.assertEquals(dto.getUsername(), user.getUsername());
		Assert.assertEquals(dto.getEmail(), user.getEmail());
	}
}
