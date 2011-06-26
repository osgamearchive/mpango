package net.sourceforge.mpango.web.directory;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

/**
 * Author jaywellings
 */
public class InvitationBackingBean 
{

	//TODO: change from a Quick and dirty implementation, (Create email service, sort out a legit SMTP & domains, add some proper error handling) 
	public Boolean sendInvitation()
	{
		try
		{
			Email email = new SimpleEmail();
			//This is not a proper solution as this SMTP will only send too @mailinator.com domains. (or so says the interwebs) will work as a POC however. 
			email.setHostName("smtp.mailinator.com");
			email.setSmtpPort(25);
			email.setAuthenticator(new DefaultAuthenticator("", ""));
			email.setTLS(false);
			email.setFrom("invite@mpango.com");
			email.setSubject("Invite to the world of mpango");
			email.setMsg("This is a test mail ... :-)");
			
			//hardcoded as per the comment above
			//TODO: pass the value from the input box to this
			email.addTo("mpango@mailinator.com");
			email.send();
			
			return true;
		}
		catch(Exception e)
		{
			String bob = "dd";
			return false;
			
		}

		
	}
	
}
