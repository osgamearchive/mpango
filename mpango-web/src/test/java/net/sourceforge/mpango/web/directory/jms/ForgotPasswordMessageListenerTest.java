package net.sourceforge.mpango.web.directory.jms;

import java.util.Locale;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import net.sf.mpango.common.directory.service.AuthenticationException;
import net.sf.mpango.common.directory.service.IAuthenticationService;
import net.sourceforge.mpango.web.directory.ForgotPasswordMessageCreator;
import net.sourceforge.mpango.web.directory.ForgotPasswordMessageListener;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class ForgotPasswordMessageListenerTest {

	private ForgotPasswordMessageListener testing;

    @Mock
	private MailSender mailSender;
    @Mock
	private ApplicationContext context;
    @Mock
	private IAuthenticationService authService;
    @Mock
    private TextMessage message;
	
	 @Before
	 public void setUp() throws JMSException {
         MockitoAnnotations.initMocks(this);
         testing = new ForgotPasswordMessageListener();
		 testing.setMailSender(mailSender);
		 testing.setApplicationContext(context);
		 testing.setAuthenticationService(authService);
		 testing.setFromAddress("from@domain.com");

         //Prepare the message
         String to = "email@domain.com";
         String sLocale = "en_US";
         String url = "http://localhost:8080/mpango-web/directory/setNewPassword.jsf?resetKey=";
         Mockito.when(message.getStringProperty(ForgotPasswordMessageCreator.JMS_STRING_PROPERTY_RECIPIENTS)).thenReturn(to);
         Mockito.when(message.getStringProperty(ForgotPasswordMessageCreator.JMS_STRING_PROPERTY_LOCALE)).thenReturn(sLocale);
         Mockito.when(message.getStringProperty(ForgotPasswordMessageCreator.JMS_STRING_PROPERTY_URL)).thenReturn(url);
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

		 //Internationalization related behavior
         Mockito.when(context.getMessage(Matchers.eq("email.forgotPassword.subject"), Matchers.any(Object[].class), Matchers.eq(locale))).thenReturn(subject);
         Mockito.when(context.getMessage(Matchers.eq("email.forgotPassword.text"), Matchers.eq(new Object [] {url+resetKey}), Matchers.eq(locale))).thenReturn(text);

		 //Generating the reset key
         Mockito.when(authService.generateResetKey(to)).thenReturn(resetKey);

		 //Email message creation
		 SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		 simpleMailMessage.setTo(to);
		 simpleMailMessage.setSubject(subject);
		 simpleMailMessage.setText(text);
		 simpleMailMessage.setFrom(testing.getFromAddress());

		 //EasyMock.replay(mailSender, context, authService, message);
		 testing.onMessage(message);

		 //Verify mocks
         Mockito.verify(mailSender).send(simpleMailMessage);
         Mockito.verify(authService).generateResetKey(to);
         Mockito.verify(context).getMessage(Matchers.eq("email.forgotPassword.text"), Matchers.eq(new Object[] {url+resetKey}), Matchers.eq(locale));
         Mockito.verify(context).getMessage(Matchers.eq("email.forgotPassword.subject"), Matchers.any(Object[].class), Matchers.eq(locale));
	 }
	 
	 @Test(expected = IllegalArgumentException.class)
	 public void testOnWrongMessage() {
         Message noTextMessage = Mockito.mock(Message.class);
         testing.onMessage(noTextMessage);
	 }
}
