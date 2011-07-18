/**
 * 
 */
package net.sf.mpango.common.directory.dao;

import java.util.Calendar;
import java.util.List;

import net.sf.mpango.common.directory.dao.UserDAO;
import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.enums.StateEnum;
import net.sf.mpango.common.test.BaseSpringTest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author aplause
 *
 */
public class UserDAOTest extends BaseSpringTest {

	@Autowired
	UserDAO userDAO;
	
	@Test
	public void testSaveDelete() {
		
		User user  = new User();
		user.setDateOfBirth(Calendar.getInstance().getTime());
		user.setEmail("User@company.com");
		user.setGender("male");
		user.setPassword("pwd");
		user.setUsername("user");
		user.setState(StateEnum.CREATED);
		user = userDAO.save(user);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getIdentifier());
		
		user.setUsername("johndoe");
		userDAO.update(user);
		
		userDAO.delete(user);
		
	}
	
	@Test
	public void testLoad() {
		User user  = new User();
		user.setDateOfBirth(Calendar.getInstance().getTime());
		user.setEmail("user@company.com");
		user.setGender("male");
		user.setPassword("pwd");
		user.setUsername("user");
		user.setState(StateEnum.CREATED);
		user = userDAO.save(user);
		Assert.assertNotNull(user);
		
		User userFound = userDAO.load("user@company.com");
		Assert.assertNotNull(userFound);
		Assert.assertNotNull(user.getIdentifier());
		Assert.assertEquals(user.getUsername(), userFound.getUsername());
		
		User user2  = new User();
		user2.setDateOfBirth(Calendar.getInstance().getTime());
		user2.setEmail("john@company.com");
		user2.setGender("male");
		user2.setPassword("pwd");
		user2.setUsername("john");
		user2.setState(StateEnum.CREATED);
		user2 = userDAO.save(user2);
		Assert.assertNotNull(user2.getIdentifier());
		
		List<User> users = userDAO.list();
		Assert.assertNotNull(users);
		Assert.assertEquals(users.size(), 2);
		
		
		userDAO.delete(user2);
		userDAO.delete(user);
	}
}
