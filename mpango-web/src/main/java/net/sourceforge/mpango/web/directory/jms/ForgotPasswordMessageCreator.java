package net.sourceforge.mpango.web.directory.jms;

import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author etux
 */
public class ForgotPasswordMessageCreator implements MessageCreator {

    public static final String DEFAULT_SENDER = "mpango@gmail.com";
    public static final String DEFAULT_SUBJECT = "mPango password reset confirmation";
    public static final String DEFAULT_TEXT =
            "<p>Hi there,</P>" +
            "<p>There was recently a request to change the password on your account.</P>" +
            "<p>If you requested this password change, please set a new password by following the link below:</P>" +
            "<a href='http://localhost:8080/mpango-web/directory/changePassword.jsf?reset_key=123'>http://localhost:8080/mpango-web/directory/recover.jsf</a>" +
            "<p>If you don't want to change your password, just ignore this message.</P>" +
            "<p>Thanks</p>";

    private String sender;
    private String recipient;
    private String subject;
    private String text;
    private String url;

    /**
     * Message Creator for the Forgot Password use case.
     * @param recipient Email address to send the link.
     * @param url URL on which to introduce the secret key (depending on the environment ir will be different).
     */
    public ForgotPasswordMessageCreator(String recipient, String url) {
        this.sender = DEFAULT_SENDER;
        this.subject = DEFAULT_SUBJECT;
        this.text = DEFAULT_TEXT;
        this.recipient = recipient;
        this.url = url;
    }

    /**
     * Implementation of the MessageCreator interface.
     * @param session JMS Session passed by the JmsTemplate
     * @return Constructed message.
     * @throws JMSException In case there was an error creating the message.
     */
    public Message createMessage(Session session) throws JMSException {
        TextMessage message = session.createTextMessage();
        message.setStringProperty("sender", sender);
        message.setStringProperty("recipients", recipient);
        message.setStringProperty("subject", subject);
        message.setStringProperty("url", url);
        message.setText(text);
        return message;
    }
}
