package net.sourceforge.mpango.web.directory;

import java.util.Locale;

import javax.faces.context.FacesContext;

import net.sourceforge.mpango.directory.facade.IAuthenticationFacade;

import org.springframework.jms.core.JmsTemplate;

import net.sourceforge.mpango.web.directory.jms.ForgotPasswordMessageCreator;

public class ResetPasswordBackingBean {

    /** Result for the sending of the new password */
    protected static final String RESULT_EMAIL_SENT = "email_sent";
    /** Result for user not found when trying to recover the password */
    protected static final String RESULT_USER_NOT_FOUND = "user_not_found";

    private JmsTemplate jmsTemplate;
    private IAuthenticationFacade authenticationFacade;

	private String email;
    private String queueName;
    private String url;
	private FacesContext facesContext;

    /**
     * Method that sends the JMS message to the back end in order to send the email message for recovering the password.
     * @return String Outcome of the situation (email_sent = The email has been sent correctly, user_not_found = No user with that email found).
     */
	public String sendMessage() {
        String result;
        if ((email != null) && (authenticationFacade.load(email) != null)) {
        	System.out.println("Sending message to queue");
        	Locale locale = getFacesContext().getViewRoot().getLocale();
            jmsTemplate.send(queueName, new ForgotPasswordMessageCreator(email, url, locale.toString()));
            result = RESULT_EMAIL_SENT;
        } else {
        	System.out.println("User not found");
            result = RESULT_USER_NOT_FOUND;

        }
        return result;
	}
	
	public FacesContext getFacesContext() {
		if (facesContext == null) {
			facesContext = FacesContext.getCurrentInstance();
		}
		return facesContext;
	}
	
	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public IAuthenticationFacade getAuthenticationFacade() {
        return authenticationFacade;
    }

    public void setAuthenticationFacade(IAuthenticationFacade authenticationFacade) {
        this.authenticationFacade = authenticationFacade;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}