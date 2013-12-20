package net.sourceforge.mpango.web.directory.jms;

import java.util.Locale;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import junit.framework.Assert;
import net.sf.mpango.common.directory.service.AuthenticationException;
import net.sf.mpango.common.directory.service.IAuthenticationService;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class ForgotPasswordMessageListenerTest {

	private ForgotPasswordMessageListener testing;
	private MailSender mailSender;
	private ApplicationContext context;
	private IAuthenticationService authService;
	
	 @Before
	 public void setUp() {
		testing = new ForgotPasswordMessageListener();
		mailSender = EasyMock.createMock(MailSender.class);
		context = EasyMock.createMock(ApplicationContext.class);
		authService = EasyMock.createMock(IAuthenticationService.class);
		testing.setMailSender(mailSender);
		testing.setApplicationContext(context);
		testing.setAuthenticationService(authService);
		testing.setFromAddress("from@domain.com");
	 }
	 
	 @Test
	 public void testOnTextMessage() throws JMSException, AuthenticationException {
		 // Data needed for the test
		 String to = "email@domain.com";
		 String sLocale = "en_US";
		 String url = "http://localhost:8080/mpango-web/directory/setNewPassword.jsf?resetKey=";
		 String subject = "Subject";
		 String resetKey = "resetKey";
		 String text = "Click here "+url+resetKey;
		 Locale locale = new Locale(sLocale);
		 //JMS Message related behavior
		 TextMessage message = EasyMock.createMock(TextMessage.class);
		 EasyMock.expect(message.getStringProperty(ForgotPasswordMessageCreator.JMS_STRING_PROPERTY_RECIPIENTS)).andReturn(to);
		 EasyMock.expect(message.getStringProperty(ForgotPasswordMessageCreator.JMS_STRING_PROPERTY_LOCALE)).andReturn(sLocale);
		 EasyMock.expect(message.getStringProperty(ForgotPasswordMessageCreator.JMS_STRING_PROPERTY_URL)).andReturn(url);
		 //Internationalization related behavior
		 EasyMock.expect(context.getMessage(EasyMock.eq("email.forgotPassword.subject"), EasyMock.aryEq(new Object [0]), EasyMock.eq(locale))).andReturn(subject);
		 EasyMock.expect(context.getMessage(EasyMock.eq("email.forgotPassword.text"), EasyMock.aryEq(new Object [] {url+resetKey}), EasyMock.eq(locale))).andReturn(text);
		 //Generating the reset key
		 EasyMock.expect(authService.generateResetKey(to)).andReturn(resetKey);
		 //Email message creation
		 SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		 simpleMailMessage.setTo(to);
		 simpleMailMessage.setSubject(subject);
		 simpleMailMessage.setText(text);
		 simpleMailMessage.setFrom(testing.getFromAddress());
		 //Email send behavior
		 mailSender.send(simpleMailMessage);
		 //Replay mocks
		 EasyMock.replay(mailSender, context, authService, message);
		 testing.onMessage(message);
		 //Verify mocks
		 EasyMock.verify(mailSender, context, authService, message);
	 }
	 
	 @Test
	 public void testOnWrongMessage() {
		 Message message = EasyMock.createMock(Message.class);
		 try {
			 testing.onMessage(message);
			 Assert.fail("Expected exception not raised");
		 } catch (IllegalArgumentException expected) {
			 //Do nothing as the exception was expected
		 }
	 }
}
