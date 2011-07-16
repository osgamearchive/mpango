package net.sourceforge.mpango.web.directory;

import net.sourceforge.mpango.directory.dto.UserDTO;
import net.sourceforge.mpango.directory.facade.AuthenticationFacade;
import net.sourceforge.mpango.web.directory.jms.ForgotPasswordMessageCreator;
import org.easymock.classextension.EasyMock;
import org.hibernate.jdbc.Expectation;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;

/**
 * Created by IntelliJ IDEA.
 * User: etux
 * Date: 7/15/11
 * Time: 9:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResetPasswordBackingBeanTest {

    private AuthenticationFacade authFacade;
    private JmsTemplate jmsTemplate;
    private ResetPasswordBackingBean testing;
    private static final String TEST_EMAIL = "email@domain.com";
    private static final String TEST_DESTINATION_QUEUE = "test.queue";


    @Before
    public void setUp() {
        authFacade = EasyMock.createMock(AuthenticationFacade.class);
        jmsTemplate = EasyMock.createMock(JmsTemplate.class);
        testing = new ResetPasswordBackingBean();
        testing.setQueueName(TEST_DESTINATION_QUEUE);
        testing.setEmail(TEST_EMAIL);
        testing.setJmsTemplate(jmsTemplate);
        testing.setAuthenticationFacade(authFacade);
    }

    @Test
    public void testSendMessageSuccessfull() throws JMSException {
        EasyMock.expect(authFacade.load(TEST_EMAIL)).andReturn(new UserDTO());
        jmsTemplate.send(EasyMock.eq(TEST_DESTINATION_QUEUE), EasyMock.isA(ForgotPasswordMessageCreator.class));
        EasyMock.replay(jmsTemplate);
        EasyMock.replay(authFacade);
        String result = testing.sendMessage();
        org.junit.Assert.assertEquals("Result should be expected", ResetPasswordBackingBean.RESULT_EMAIL_SENT, result);
        EasyMock.verify(jmsTemplate);
        EasyMock.verify(authFacade);
    }

    @Test
     public void testSendMessageUnsuccessfull() throws JMSException {
        EasyMock.expect(authFacade.load(TEST_EMAIL)).andReturn(null);
        EasyMock.replay(jmsTemplate);
        EasyMock.replay(authFacade);
        String result = testing.sendMessage();
        org.junit.Assert.assertEquals("Result should be expected", ResetPasswordBackingBean.RESULT_USER_NOT_FOUND, result);
        EasyMock.verify(jmsTemplate);
        EasyMock.verify(authFacade);
    }
}
