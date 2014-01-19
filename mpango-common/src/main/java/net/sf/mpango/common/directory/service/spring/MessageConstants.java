package net.sf.mpango.common.directory.service.spring;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 *         Date: 01/01/14
 *         Time: 20:49
 */
public class MessageConstants {

    static final String USER_LOGIN_SUCCESSFUL = "user.login.successful";
    static final String USER_WRONG_CREDENTIALS = "user.wrong_credentials";
    static final String USER_REGISTRATION_FAILURE_USER_ALREADY_EXISTS = "user.registration.failure_user_already_exists";
    static final String USER_GENERATE_RESET_KEY_FAILURE_USER_NOT_FOUND = "user.generateResetKey.failure_user_not_found";
    static final String USER_GET_USER_BY_RESET_KEY_FAILURE_USER_NOT_FOUND = "user.getUserByResetKey.failure_user_not_found";
    static final String USER_CHANGE_PASSWORD_FAILURE_USER_NOT_FOUND = "user.changePassword.failure_user_not_found";
    static final String USER_GENERATE_NONCE_FAILURE_RNG_NOT_FOUND = "user.generateNonce.failure_RNG_not_found";

    static final String USER_SERVICE_INITIALIZED = "user.service.initialized";

    /**
     * This class should not be instantiated.
     */
    private MessageConstants() {}
}
