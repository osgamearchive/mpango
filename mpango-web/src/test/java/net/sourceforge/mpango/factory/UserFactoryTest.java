package net.sourceforge.mpango.factory;

import junit.framework.Assert;
import junit.framework.TestSuite;
import net.sourceforge.mpango.battle.factory.UserFactory;
import net.sourceforge.mpango.dto.UserDTO;
import net.sourceforge.mpango.entity.User;

public class UserFactoryTest extends TestSuite {
	/*
	 * very simple test
	 */
	public void testUserFactory() {
		UserDTO dto = new UserDTO();
		dto.setUserId(1L);
		dto.setUsername("test");
		dto.setEmail("test@company.com");
		
		User user = UserFactory.instance().create(dto);
		Assert.assertNotNull(dto);
		Assert.assertEquals(dto.getUserId(), user.getIdentifier());
		Assert.assertEquals(dto.getUsername(), user.getUsername());
		Assert.assertEquals(dto.getEmail(), user.getEmail());
	}
}
