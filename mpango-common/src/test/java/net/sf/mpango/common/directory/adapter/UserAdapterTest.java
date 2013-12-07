package net.sf.mpango.common.directory.adapter;

import junit.framework.Assert;
import net.sf.mpango.common.directory.dto.UserDTO;
import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.enums.StateEnum;
import org.junit.Test;

/**
 * 
 * @author aplause
 *
 */
public class UserAdapterTest {

	@Test
	public void testToDTO() {
		User user = new User();
		user.setId(1L);
		user.setUsername("test");
		user.setEmail("test@company.com");
		UserDTO dto = UserAdapter.getInstance().toDTO(user);
		Assert.assertNotNull(dto);
		Assert.assertEquals(dto.getId(), user.getId());
		Assert.assertEquals(dto.getUsername(), user.getUsername());
		Assert.assertEquals(dto.getEmail(), user.getEmail());
	}
	
	@Test
	public void testUserFactory() {
		UserDTO dto = new UserDTO();
		dto.setId(1L);
		dto.setUsername("test");
		dto.setEmail("test@company.com");
		dto.setState(StateEnum.CREATED);
        dto.setGender(User.Gender.UNDEFINED.name());
		User user =  UserAdapter.getInstance().fromDTO(dto);
		Assert.assertNotNull(dto);
		Assert.assertEquals(dto.getId(), user.getId());
		Assert.assertEquals(dto.getUsername(), user.getUsername());
		Assert.assertEquals(dto.getEmail(), user.getEmail());
	}
}
