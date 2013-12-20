package net.sf.mpango.common.test;

import net.sf.mpango.common.directory.entity.User;

/**
 * This is a utility class bringing static methods for testing purposes.
 *
 * @author etux
 */
public class CommonTestUtils {

    public static final String VALID_EMAIL = "email@domain.com";
    public static final String VALID_USERNAME = "username1999";
    public static final String VALID_PASSWORD = "password";

    private CommonTestUtils() {}

    public static User createRandomUser() {
        return createRandomUser(VALID_EMAIL);
    }

	public static User createRandomUser(final String email) {
        return createUser(email, VALID_USERNAME, VALID_PASSWORD, null, null);
	}

    public static User createUser(final String email, final String username, final String password) {
        return createUser(email, username, password, null, null);
    }

    public static User createUser(final String email, final String username, final String password, final User.Gender gender, final User.State state) {
        return User.UserBuilder.createUser(email, username, password)
                .setGender(gender != null ? gender : User.Gender.UNDEFINED)
                .setState(state != null ? state : User.State.CREATED)
                .build();
    }

    public static User createValidUser() {
        return createUser(VALID_EMAIL, VALID_USERNAME, VALID_PASSWORD);
    }
}
