package net.sourceforge.mpango.web.directory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
/**
 * Author jaywellings
 */

@ManagedBean(name="invitationBackingBean")
@RequestScoped
public class InvitationBackingBean
{
    private String inviteEmailAddress = "";
    private String inviteName = "";
    private boolean isEmailSent = false;

    public String sendInvitation()
    {
        try
        {
            throw new RuntimeException("TO BE IMPLEMENTED");
//			String sender = "mpango@gmail.com";
//			String recipients = this.inviteEmailAddress;
//			String subject = "mPango invite";
//			String content = "<p>Hi there,</p><p>You have just been invited to the MMORPG mpango by " + this.inviteName + "!.</p>";

//			SendMail sendMail = new SendMail(sender, recipients, subject, content);
//			sendMail.registerUser();
//			this.setIsEmailSent(true);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.setIsEmailSent(false);
        }

        return this.showSendStatus();
    }

    public String showSendStatus()
    {
        return "inviteSendMessage";
    }

    public void setInviteEmailAddress(String inviteEmailAddress)
    {
        this.inviteEmailAddress = inviteEmailAddress;
    }

    public String getInviteEmailAddress()
    {
        return inviteEmailAddress;
    }

    public void setIsEmailSent(boolean isEmailSent)
    {
        this.isEmailSent = isEmailSent;
    }

    public boolean getIsEmailSent()
    {
        return isEmailSent;
    }

    public void setInviteName(String inviteName) {
        this.inviteName = inviteName;
    }

    public String getInviteName() {
        return inviteName;
    }

}

//import net.sourceforge.mpango.email.SendMail;
