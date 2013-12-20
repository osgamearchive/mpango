/**
 * 
 */
package net.sf.mpango.common.directory.dao;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.test.BaseSpringTest;
import net.sf.mpango.common.test.CommonTestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author aplause
 * @author eduardo.devera@gmail.com
 *
 */
public class UserDAOTest extends BaseSpringTest {

	@Autowired
	private UserDAO userDAO;

    private User user;

    @Before
    public void setUp() throws UserAlreadyExistsException {
        user = CommonTestUtils.createRandomUser();
        userDAO.save(user);
    }

    @After
    public void tearDown() throws UserNotFoundException {
        userDAO.delete(user);
    }
	
	@Test
	public void testSave_successful() throws UserAlreadyExistsException {
		User user  = CommonTestUtils.createRandomUser("email@domain.com");
		user = userDAO.save(user);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());
	}

    @Test
    public void testLoad_successful() throws UserNotFoundException {
        User loadedUser = userDAO.load(CommonTestUtils.VALID_EMAIL);
        Assert.assertEquals(user, loadedUser);
    }

    @Test
    public void testUpdate_successful() throws UserNotFoundException {
        user.setUsername("test_username");
        userDAO.update(user);

        final User loadedUser = userDAO.load(CommonTestUtils.VALID_EMAIL);

        Assert.assertEquals(user.getUsername(), loadedUser.getUsername());
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteUser_successful() throws UserNotFoundException {
        userDAO.delete(user);
        userDAO.load(CommonTestUtils.VALID_EMAIL);
    }
}
