package net.sourceforge.mpango.web.directory;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import net.sf.mpango.common.utils.LocalizedMessageBuilder;
import org.springframework.jms.core.MessageCreator;

/**
 * @author etux
 */
public class ForgotPasswordMessageCreator implements MessageCreator {

	private Logger LOGGER = Logger.getLogger(ForgotPasswordMessageCreator.class.getName());
	
	public static final String JMS_STRING_PROPERTY_RECIPIENTS = "recipients";
	public static final String JMS_STRING_PROPERTY_URL = "url";
	public static final String JMS_STRING_PROPERTY_LOCALE = "locale";

    private String recipient;
    private String locale;
    private String url;

    /**
     * Message Creator for the Forgot Password use case.
     * @param recipient Email address to registerUser the link.
     * @param url URL on which to introduce the secret key (depending on the environment ir will be different).
     * @param locale of the user.
     */
    public ForgotPasswordMessageCreator(final String recipient, final String url, final String locale) {
        LOGGER.log(Level.FINEST,
                LocalizedMessageBuilder.getSystemMessage(
                        this,
                        MessageConstants.FORGOT_MSG_CREATOR_INSTANTIATION,
                        locale,
                        recipient,
                        url));
        this.locale = locale;
        this.recipient = recipient;
        this.url = url;
    }

    /**
     * Implementation of the MessageCreator interface.
     * @param session JMS Session passed by the JmsTemplate
     * @return Constructed message.
     * @throws JMSException In case there was an error creating the message.
     */
    public Message createMessage(final Session session) throws JMSException {
        final TextMessage message = session.createTextMessage();
        message.setStringProperty(JMS_STRING_PROPERTY_RECIPIENTS, recipient);
        message.setStringProperty(JMS_STRING_PROPERTY_LOCALE, locale);
        message.setStringProperty(JMS_STRING_PROPERTY_URL, url);
    	LOGGER.log(
                Level.FINEST,
                MessageConstants.FORGOT_MSG_CREATOR_MSG_CREATED, message);
        return message;
    }
}
