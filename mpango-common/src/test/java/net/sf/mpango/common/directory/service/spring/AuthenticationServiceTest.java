/**
 * 
 */
package net.sf.mpango.common.directory.service.spring;

import java.util.Calendar;
import java.util.UUID;

import net.sf.mpango.common.dao.NotFoundException;
import net.sf.mpango.common.directory.dao.UserNotFoundException;
import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.service.AuthenticationException;
import net.sf.mpango.common.test.BaseSpringTest;
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
public class AuthenticationServiceTest extends BaseSpringTest {

    protected static final String TEST_USER_EMAIL = "user@company.com";
    protected static final String TEST_USER_USERNAME = "username";
    protected static final String TEST_USER_PASSWORD = "password";

    @Autowired
	private AuthenticationService authService;

    private User user;

    @Before
    public void setUp() throws AuthenticationException {
        user = User.UserBuilder.createUser(TEST_USER_EMAIL, TEST_USER_USERNAME, TEST_USER_PASSWORD)
                .setGender(User.Gender.MALE)
                .setDateOfBirth(Calendar.getInstance().getTime())
                .build();
        user = authService.register(user);
    }

    @After
    public void tearDown() throws NotFoundException {
        authService.getUserDAO().delete(user);
    }


	@Test
	public void login_sucessful() throws AuthenticationException {
        User userFound = authService.login(user.getEmail(), user.getPassword());
        Assert.assertNotNull(userFound);
        Assert.assertEquals("The user must be equal to the registered one", user, userFound);
	}

    @Test(expected = AuthenticationException.class)
    public void login_unsuccessful() throws AuthenticationException {
        User userFound = authService.login("nonexisted@email.com", "password");
        Assert.assertNull("THe user does not exist, hence it should be null", userFound);
    }
	
	@Test
	public void generateResetKey_successful() throws UserNotFoundException, AuthenticationException {
        final String resetKey = authService.generateResetKey(TEST_USER_EMAIL);
        final User loadedUser = authService.getUserByResetKey(resetKey);
        Assert.assertEquals(user, loadedUser);
        Assert.assertEquals(resetKey, user.getResetKey());
	}

    @Test(expected = AuthenticationException.class)
    public void getUserByResetKey_unsuccessful() throws AuthenticationException {
        authService.getUserByResetKey(UUID.randomUUID().toString());
    }

}
