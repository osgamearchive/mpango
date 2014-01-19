package net.sourceforge.mpango.web.directory;

/**
 * @author <a href="mailto:eduardo.devera@gmail.com">Eduardo de Vera</a>
 *         Date: 01/01/14
 *         Time: 21:03
 */
public class MessageConstants {

    static final String USER_CHANGE_PASSWORD_FAILURE_DIFFERENT_PASSWORDS = "logging.user.different_passwords";
    static final String USER_CHANGE_PASSWORD_FAILURE = "logging.user.failure_change_password";
    static final String USER_LOGIN_SUCCESSFUL = "logging.net.sourceforge.mpango.web.directory.successful_login";
    static final String USER_LOGIN_FAILURE_WRONG_CREDENTIALS = "logging.net.sourceforge.mpango.web.directory.wrong_credentials";
    static final String USER_SEND_MESSAGE_SUCCESS = "logging.user.send_message";
    static final String USER_SEND_MESSAGSE_FAILURE_USER_NOT_FOUND = "logging.user.send_message.failure.user_not_found";
    static final String USER_MESSAGE_RECEIVED = "logging.net.sourceforge.mpango.web.directory.jms.message_received";
    static final String USER_SEND_EMAIL_SUCCESSFUL = "logging.net.sourceforge.mpango.web.directory.email_to_be_sent";
    static final String USER_EMAIL_RECOVER_PASSWORD_SUCCESSFUL = "logging.net.sourceforge.mpango.web.directory.successful_email_recover_password";
    static final String USER_EMAIL_RECOVER_PASSWORD_FAILURE_RECEIVE_EMAIL = "logging.net.sourceforge.mpango.web.directory.error_receiving_message";
    static final String USER_EMAIL_RECOVER_PASSWORD_FAILURE_USER_NOT_FOUND = "logging.net.sourceforge.mpango.web.directory.user_not_found";
    static final String USER_EMAIL_RECOVER_PASSWORD_FAILURE_MESSAGE_NOT_SUPPORTED = "logging.net.sourceforge.mpango.web.directory.message_not_supported";

    static final String FORGOT_MSG_CREATOR_INSTANTIATION = "logging.net.sourceforge.mpango.web.directory.msg_creator_instantiation";
    static final String FORGOT_MSG_CREATOR_MSG_CREATED = "logging.net.sourceforge.mpango.web.directory.msg_creator_message_created";
    static final String USER_REGISTRATION_SUCCESSFUL = "logging.net.sourceforge.mpango.web.directory.usr_registration_successful";
    static final String USER_REGISTRATION_UNSUCCESFUL = "logging.net.sourceforge.mpango.web.directory.usr_registration_unsuccessful";

    /**
     * This class should not be instantiated.
     */
    private MessageConstants() {}
}
