/**
 * 
 */
package net.sf.mpango.common.directory.dao.hibernate;

import net.sf.mpango.common.dao.AlreadyExistsException;
import net.sf.mpango.common.dao.NotFoundException;
import net.sf.mpango.common.directory.dao.UserDAO;
import net.sf.mpango.common.directory.dao.UserNotFoundException;
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
    public void setUp() throws AlreadyExistsException {
        user = CommonTestUtils.createRandomUser();
        userDAO.save(user);
    }

    @After
    public void tearDown() throws NotFoundException {
        userDAO.delete(user);
    }
	
	@Test
	public void testSave_successful() throws AlreadyExistsException {
		User user  = CommonTestUtils.createRandomUser("email@domain.com");
		user = userDAO.save(user);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());
	}

    @Test
    public void testLoad_successful() throws NotFoundException {
        User loadedUser = userDAO.loadByEmail(CommonTestUtils.VALID_EMAIL);
        Assert.assertEquals(user, loadedUser);
    }

    @Test
    public void testUpdate_successful() throws NotFoundException {
        user.setUsername("test_username");
        userDAO.update(user);

        final User loadedUser = userDAO.loadByEmail(CommonTestUtils.VALID_EMAIL);

        Assert.assertEquals(user.getUsername(), loadedUser.getUsername());
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteUser_successful() throws NotFoundException {
        userDAO.delete(user);
        userDAO.loadByEmail(CommonTestUtils.VALID_EMAIL);
    }
}
