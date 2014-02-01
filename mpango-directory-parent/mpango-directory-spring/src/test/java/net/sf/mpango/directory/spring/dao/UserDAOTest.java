/**
 * 
 */
package net.sf.mpango.directory.spring.dao;

import net.sf.mpango.common.dao.AlreadyExistsException;
import net.sf.mpango.common.dao.NotFoundException;
import net.sf.mpango.directory.dao.UserDAO;
import net.sf.mpango.directory.dao.UserNotFoundException;
import net.sf.mpango.directory.entity.User;
import net.sf.mpango.directory.test.DirectoryTestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author aplause
 * @author eduardo.devera@gmail.com
 *
 */
@ContextConfiguration("classpath:spring-directory.xml")
public class UserDAOTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private UserDAO userDAO;

    private User user;

    @Before
    public void setUp() throws AlreadyExistsException {
        user = DirectoryTestUtils.createRandomUser();
        userDAO.save(user);
    }

    @After
    public void tearDown() throws NotFoundException {
        userDAO.delete(user);
    }
	
	@Test
	public void testSave_successful() throws AlreadyExistsException {
		User user  = DirectoryTestUtils.createUser("another_email@domain.com", "myusername", "mypassword");
		user = userDAO.save(user);
		Assert.assertNotNull(user);
		Assert.assertNotNull(user.getId());
	}

    @Test
    public void testLoad_successful() throws NotFoundException {
        User loadedUser = userDAO.loadByEmail(DirectoryTestUtils.VALID_EMAIL);
        Assert.assertEquals(user, loadedUser);
        assertThat(user.getId(), is(notNullValue()));
    }

    @Test
    public void testUpdate_successful() throws NotFoundException {
        user.setUsername("test_username");
        userDAO.update(user);

        final User loadedUser = userDAO.loadByEmail(DirectoryTestUtils.VALID_EMAIL);

        Assert.assertEquals(user.getUsername(), loadedUser.getUsername());
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteUser_successful() throws NotFoundException {
        userDAO.delete(user);
        userDAO.loadByEmail(DirectoryTestUtils.VALID_EMAIL);
    }
}
