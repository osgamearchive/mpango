package net.sourceforge.mpango.web.directory;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import net.sf.mpango.directory.service.AuthenticationException;
import net.sf.mpango.directory.service.IAuthenticationService;
import net.sf.mpango.common.utils.LocalizedMessageBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Class that listens to the forgot password queue and proceeds sending the password recovery email to the end user.
 * @author etux
 */
public class ForgotPasswordMessageListener implements MessageListener, ApplicationContextAware {

    private static final Logger LOGGER = Logger.getLogger(ForgotPasswordMessageListener.class.getName());

	private MailSender mailSender;
	private ApplicationContext applicationContext;
	private String fromAddress;
	private IAuthenticationService authenticationService;

	public void onMessage(final Message message) {
		LOGGER.log(Level.FINEST, LocalizedMessageBuilder.getSystemMessage(this, MessageConstants.USER_MESSAGE_RECEIVED));
        if (message instanceof TextMessage) {
            final TextMessage textMessage = (TextMessage) message;
            String to = null;
            try {
                final Locale locale = new Locale(textMessage.getStringProperty(ForgotPasswordMessageCreator.JMS_STRING_PROPERTY_LOCALE));
                final String subject = applicationContext.getMessage("email.forgotPassword.subject", new Object[0], locale);
                to = textMessage.getStringProperty(ForgotPasswordMessageCreator.JMS_STRING_PROPERTY_RECIPIENTS);

                final String resetKey = authenticationService.generateResetKey(to);
                final String url = textMessage.getStringProperty(ForgotPasswordMessageCreator.JMS_STRING_PROPERTY_URL)+resetKey;
                final String text = applicationContext.getMessage("email.forgotPassword.text", new Object [] {url}, locale);
                LOGGER.log(
                        Level.FINEST,
                        LocalizedMessageBuilder.getSystemMessage(
                                this,
                                MessageConstants.USER_SEND_EMAIL_SUCCESSFUL,
                                fromAddress,
                                to,
                                subject,
                                text,
                                locale.toString(),
                                resetKey,
                                url));
                sendEmail(to, subject, text);
				LOGGER.log(
                        Level.INFO,
                        LocalizedMessageBuilder.getSystemMessage(
                                this,
                                MessageConstants.USER_EMAIL_RECOVER_PASSWORD_SUCCESSFUL,
                                to));
			} catch (final JMSException e) {
				LOGGER.log(
                        Level.SEVERE,
                        LocalizedMessageBuilder.getSystemMessage(
                                this,
                                MessageConstants.USER_EMAIL_RECOVER_PASSWORD_FAILURE_RECEIVE_EMAIL),
                        e);
			} catch (final AuthenticationException e) {
                LOGGER.log(
                        Level.WARNING,
                        LocalizedMessageBuilder.getSystemMessage(
                                this,
                                MessageConstants.USER_EMAIL_RECOVER_PASSWORD_FAILURE_USER_NOT_FOUND,
                                to),
                        e);
            }
        } else {
        	LOGGER.log(
                    Level.WARNING,
                    LocalizedMessageBuilder.getSystemMessage(
                            this,
                            MessageConstants.USER_EMAIL_RECOVER_PASSWORD_FAILURE_MESSAGE_NOT_SUPPORTED,
                            message));
            throw new IllegalArgumentException();
        }
    }

    private void sendEmail(final String to, final String subject, final String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailMessage.setFrom(fromAddress);
        mailSender.send(mailMessage);
    }

    public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
    public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public IAuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	public void setAuthenticationService(IAuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

}
