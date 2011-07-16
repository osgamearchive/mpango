package net.sourceforge.mpango.web.directory.jms;

import java.util.Locale;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import net.sourceforge.mpango.directory.service.IAuthenticationService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Class that listens to the forgot password queue and proceeds sending the password recovery email to the end user.
 * @author etux
 */
public class ForgotPasswordMessageListener implements MessageListener, ApplicationContextAware {

	private MailSender mailSender;
	private ApplicationContext applicationContext;
	private String fromAddress;
	private IAuthenticationService authenticationService;

	public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            System.out.println("Received message "+message);
            TextMessage textMessage = (TextMessage) message;
            try {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                String to = textMessage.getStringProperty(ForgotPasswordMessageCreator.JMS_STRING_PROPERTY_RECIPIENTS);
            	String resetKey = authenticationService.generateResetKey(to);
                Locale locale = new Locale(textMessage.getStringProperty(ForgotPasswordMessageCreator.JMS_STRING_PROPERTY_LOCALE));
                String url = textMessage.getStringProperty(ForgotPasswordMessageCreator.JMS_STRING_PROPERTY_URL)+resetKey;
                String subject = applicationContext.getMessage("email.forgotPassword.subject", new Object [0], locale);
                String text = applicationContext.getMessage("email.forgotPassword.text", new Object [] {url}, locale);
				mailMessage.setTo(to);
				mailMessage.setSubject(subject);
				mailMessage.setText(text);
				mailMessage.setFrom(fromAddress);
				mailSender.send(mailMessage);
				System.out.println("Email sent");
			} catch (JMSException e) {
				e.printStackTrace();
			}
            
        } else {
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
