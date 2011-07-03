package net.sourceforge.mpango.web.directory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import net.sourceforge.mpango.directory.dto.UserDTO;
import net.sourceforge.mpango.email.SendMail;
import net.sourceforge.mpango.jms.ActiveMQ;

@ManagedBean
@RequestScoped
public class ResetPasswordBackingBean {

	private String email;
	private String newPassword;
	private String retypePassword;
	private boolean passwordChangedFlag = false;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}
	
	public boolean isPasswordChangedFlag() {
		return passwordChangedFlag;
	}

	public void setPasswordChangedFlag(boolean passwordChangedFlag) {
		this.passwordChangedFlag = passwordChangedFlag;
	}

	public void sendMessage() {

		try {
			String sender = "mpango@gmail.com";
			String recipients = email;
			String subject = "mPango password reset confirmation";
			String content = "<p>Hi there,</P>" +
					"<p>There was recently a request to change the password on your account.</P>" +
					"<p>If you requested this password change, please set a new password by following the link below:</P>" +
					"<a href='http://localhost:8080/mpango-web/directory/changePassword.jsf?reset_key=123'>http://localhost:8080/mpango-web/directory/recover.jsf</a>" +
					"<p>If you don't want to change your password, just ignore this message.</P>" +
					"<p>Thanks</p>";
			SendMail sendMail = new SendMail(sender, recipients, subject, content);
			
			ActiveMQ.setUp("TestQueue");
			Session session = ActiveMQ.getSession();
			ObjectMessage message = session.createObjectMessage();
			message.setObject(sendMail);
			ActiveMQ.createProducerAndSendAMessage(message);
			ActiveMQ.createConsumerAndReceiveAMessage();
		} catch (JMSException e) {
			e.printStackTrace();
		} 
	}
	
	public void changePassword() {
		UserDTO user = new UserDTO();
		
		if (newPassword.equalsIgnoreCase(retypePassword)) {
			user.setPassword(newPassword);
			passwordChangedFlag = true;
		}
	}
	
	public static void main(String[] args) {
		ResetPasswordBackingBean resetPasswordBackingBean = new ResetPasswordBackingBean();
		resetPasswordBackingBean.sendMessage();
	}
}