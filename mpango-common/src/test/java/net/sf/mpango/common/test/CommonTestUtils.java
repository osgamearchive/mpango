package net.sf.mpango.common.test;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import net.sf.mpango.common.directory.entity.User;
import net.sf.mpango.common.directory.enums.StateEnum;
import net.sf.mpango.common.directory.adapter.UserAdapter;
import net.sf.mpango.common.directory.dto.UserDTO;

/**
 * This is a utility class bringing static methods for testing purposes.
 *
 * @author etux
 */
public class CommonTestUtils {


    protected static final String TEST_DEFAULT_EMAIL_ADDRESS = "name@domain.com";

    private CommonTestUtils() {}

    public static User createRandomUser() {
        return createRandomUser(TEST_DEFAULT_EMAIL_ADDRESS);
    }

	public static User createRandomUser(final String email) {
		User user = new User();
        user.setEmail(email);
		user.setGender(User.Gender.MALE);
		user.setId(RandomUtils.nextLong());
		user.setNonceToken(RandomStringUtils.random(16));
		user.setPassword(RandomStringUtils.random(8));
		user.setResetKey(RandomStringUtils.random(16));
		user.setState(RandomUtils.nextBoolean() ? StateEnum.ACTIVE : StateEnum.DELETED);
		user.setUsername(RandomStringUtils.random(8));
		return user;
	}
	
	public static UserDTO getUserDTO() {
		return UserAdapter.getInstance().toDTO(createRandomUser());
	}

    public static UserDTO getUserDTO(final String email) {
        return UserAdapter.getInstance().toDTO(createRandomUser(email));
    }
}
