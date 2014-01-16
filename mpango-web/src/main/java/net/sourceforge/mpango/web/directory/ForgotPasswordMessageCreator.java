package net.sourceforge.mpango.web.directory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.jms.core.MessageCreator;

/**
 * @author etux
 */
public class ForgotPasswordMessageCreator implements MessageCreator {

	private Logger logger = Logger.getLogger(ForgotPasswordMessageCreator.class);
	
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
    public ForgotPasswordMessageCreator(String recipient, String url, String locale) {
    	if (logger.isDebugEnabled()) {
    		logger.debug("locale: "+locale);
    		logger.debug("recipient: "+recipient);
    		logger.debug("url: "+url);
    	}
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
    public Message createMessage(Session session) throws JMSException {
        TextMessage message = session.createTextMessage();
        message.setStringProperty(JMS_STRING_PROPERTY_RECIPIENTS, recipient);
        message.setStringProperty(JMS_STRING_PROPERTY_LOCALE, locale);
        message.setStringProperty(JMS_STRING_PROPERTY_URL, url);
    	logger.debug("Message created:"+message);
        return message;
    }
}
