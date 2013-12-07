package net.sourceforge.mpango.web.directory.jms;

import java.util.Locale;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import net.sf.mpango.common.directory.service.IAuthenticationService;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Class that listens to the forgot password queue and proceeds sending the password recovery email to the end user.
 * @author etux
 */
public class ForgotPasswordMessageListener implements MessageListener, ApplicationContextAware {

	private static final Logger logger = Logger.getLogger(ForgotPasswordMessageListener.class);
	private MailSender mailSender;
	private ApplicationContext applicationContext;
	private String fromAddress;
	private IAuthenticationService authenticationService;

	public void onMessage(Message message) {
		logger.debug("Message received");
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                String to = textMessage.getStringProperty(ForgotPasswordMessageCreator.JMS_STRING_PROPERTY_RECIPIENTS);
            	String resetKey = authenticationService.generateResetKey(to);
                Locale locale = new Locale(textMessage.getStringProperty(ForgotPasswordMessageCreator.JMS_STRING_PROPERTY_LOCALE));
                String url = textMessage.getStringProperty(ForgotPasswordMessageCreator.JMS_STRING_PROPERTY_URL)+resetKey;
                String subject = applicationContext.getMessage("email.forgotPassword.subject", new Object [0], locale);
                String text = applicationContext.getMessage("email.forgotPassword.text", new Object [] {url}, locale);
                if (logger.isDebugEnabled()) {
                	logger.debug("Sending Message with the following values");
                	logger.debug("From: "+fromAddress);
                	logger.debug("To: "+to);
                	logger.debug("Subject: "+subject);
                	logger.debug("text: "+text);
                	logger.debug("Locale: "+locale.toString());
                	logger.debug("resetKey: "+ resetKey);
                	logger.debug("url: "+url);
                }
                SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setTo(to);
				mailMessage.setSubject(subject);
				mailMessage.setText(text);
				mailMessage.setFrom(fromAddress);
				mailSender.send(mailMessage);
				logger.info("Email sent to address:"+to);
			} catch (JMSException e) {
				logger.error("Error sending email", e);
			}
        } else {
        	logger.warn("Messages different from TextMessage are not intented for this queue at the moment."+message);
            throw new IllegalArgumentException("The message expected must be a TextMessage instance");
        }
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
