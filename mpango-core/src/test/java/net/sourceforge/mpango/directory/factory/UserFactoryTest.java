package net.sourceforge.mpango.directory.factory;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.sourceforge.mpango.directory.dto.UserDTO;
import net.sourceforge.mpango.directory.entity.User;
import net.sourceforge.mpango.directory.factory.UserFactory;

public class UserFactoryTest extends TestCase {
	/*
	 * very simple test
	 */
	public void testUserFactory() {
		UserDTO dto = new UserDTO();
		dto.setId(1L);
		dto.setUsername("test");
		dto.setEmail("test@company.com");
		
		User user = UserFactory.instance().create(dto);
		Assert.assertNotNull(dto);
		Assert.assertEquals(dto.getId(), user.getIdentifier());
		Assert.assertEquals(dto.getUsername(), user.getUsername());
		Assert.assertEquals(dto.getEmail(), user.getEmail());
	}
}
