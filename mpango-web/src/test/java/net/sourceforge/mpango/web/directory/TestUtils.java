package net.sourceforge.mpango.web.directory;

import net.sf.mpango.directory.entity.User;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 */
public class TestUtils {

    public static final String DEFAULT_USERNAME = "username";
    public static final String DEFAULT_PASSWORD = "password";

    public static User createUser(final String email, final String resetKey) {
        return User.UserBuilder.createUser(email, DEFAULT_USERNAME, DEFAULT_PASSWORD).setResetKey(resetKey).build();
    }

    /**
     * Non instantiable
     */
    private TestUtils() {}
}
