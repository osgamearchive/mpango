package net.sourceforge.mpango.web.directory;

import java.util.Locale;

import net.sf.mpango.common.directory.dto.UserDTO;
import net.sf.mpango.common.directory.facade.AuthenticationFacade;
import net.sourceforge.mpango.web.directory.jms.ForgotPasswordMessageCreator;
import org.easymock.classextension.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.jms.JMSException;

/**
 * @author etux
 */
public class ResetPasswordBackingBeanTest {

    private AuthenticationFacade authFacade;
    private JmsTemplate jmsTemplate;
    private FacesContext facesContext;
    private UIViewRoot viewRoot;
    private ResetPasswordBackingBean testing;
    private static final String TEST_EMAIL = "email@domain.com";
    private static final String TEST_DESTINATION_QUEUE = "test.queue";


    @Before
    public void setUp() {
        authFacade = EasyMock.createMock(AuthenticationFacade.class);
        jmsTemplate = EasyMock.createMock(JmsTemplate.class);
        facesContext = EasyMock.createMock(FacesContext.class);
        viewRoot = EasyMock.createMock(UIViewRoot.class);
        testing = new ResetPasswordBackingBean();
        testing.setQueueName(TEST_DESTINATION_QUEUE);
        testing.setEmail(TEST_EMAIL);
        testing.setJmsTemplate(jmsTemplate);
        testing.setAuthenticationFacade(authFacade);
        testing.setFacesContext(facesContext);
    }

    @Test
    public void testSendMessageSuccessfull() throws JMSException {
    	EasyMock.expect(facesContext.getViewRoot()).andReturn(viewRoot);
    	EasyMock.expect(viewRoot.getLocale()).andReturn(Locale.getDefault());
        EasyMock.expect(authFacade.load(TEST_EMAIL)).andReturn(new UserDTO());
        jmsTemplate.send(EasyMock.eq(TEST_DESTINATION_QUEUE), EasyMock.isA(ForgotPasswordMessageCreator.class));
        EasyMock.replay(jmsTemplate);
        EasyMock.replay(authFacade);
        EasyMock.replay(facesContext);
        EasyMock.replay(viewRoot);
        String result = testing.sendMessage();
        org.junit.Assert.assertEquals("Result should be expected", ResetPasswordBackingBean.RESULT_EMAIL_SENT, result);
        EasyMock.verify(jmsTemplate);
        EasyMock.verify(authFacade);
        EasyMock.verify(facesContext);
        EasyMock.verify(viewRoot);
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
