package net.sourceforge.mpango.web.directory;

import java.util.Locale;
import java.util.UUID;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import net.sf.mpango.common.directory.service.AuthenticationException;
import net.sf.mpango.common.directory.service.IAuthenticationService;
import net.sourceforge.mpango.web.directory.jms.ForgotPasswordMessageCreator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * @author etux
 */
public class ResetPasswordBackingBeanTest {

    private static final String TEST_EMAIL = "email@domain.com";
    private static final String TEST_DESTINATION_QUEUE = "test.queue";

    @Mock
    private IAuthenticationService authentitationService;
    @Mock
    private JmsTemplate jmsTemplate;
    @Mock
    private FacesContext facesContext;
    @Mock
    private UIViewRoot viewRoot;

    private ResetPasswordBackingBean testing;


    @Before
    public void setUp() throws AuthenticationException {
        MockitoAnnotations.initMocks(this);
        testing = new ResetPasswordBackingBean();
        testing.setQueueName(TEST_DESTINATION_QUEUE);
        testing.setEmail(TEST_EMAIL);
        testing.setJmsTemplate(jmsTemplate);
        testing.setAuthService(authentitationService);
        testing.setFacesContext(facesContext);
        when(facesContext.getViewRoot()).thenReturn(viewRoot);
        when(viewRoot.getLocale()).thenReturn(Locale.getDefault());
        when(authentitationService.generateResetKey(TEST_EMAIL)).thenReturn(UUID.randomUUID().toString());
        doNothing().when(jmsTemplate).send(eq(TEST_DESTINATION_QUEUE), isA(ForgotPasswordMessageCreator.class));
    }

    @Test
    public void testSendMessageSuccessfull() throws AuthenticationException {
        String result = testing.sendMessage();
        org.junit.Assert.assertEquals("Result should be expected", ResetPasswordBackingBean.RESULT_EMAIL_SENT, result);
        verify(facesContext).getViewRoot();
        verify(viewRoot).getLocale();
        verify(jmsTemplate).send(eq(TEST_DESTINATION_QUEUE), isA(ForgotPasswordMessageCreator.class));
        verify(authentitationService).generateResetKey(TEST_EMAIL);
    }

    @Test
     public void testSendMessageUnsuccessfull() throws AuthenticationException {
        when(authentitationService.generateResetKey(TEST_EMAIL)).thenThrow(new AuthenticationException("authentication exception"));
        String result = testing.sendMessage();
        assertThat("Result should be expected", result, is(equalTo(ResetPasswordBackingBean.RESULT_USER_NOT_FOUND)));
        verify(authentitationService).generateResetKey(TEST_EMAIL);
        verifyZeroInteractions(jmsTemplate);
    }
}
