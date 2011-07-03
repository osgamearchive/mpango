package net.sourceforge.mpango.web.directory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import net.sourceforge.mpango.email.SendMail;
import net.sourceforge.mpango.jms.ActiveMQ;

@ManagedBean
@SessionScoped
public class ResetPasswordBackingBean {

	private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static void main(String[] args) {
		ResetPasswordBackingBean resetPasswordBackingBean = new ResetPasswordBackingBean();
		resetPasswordBackingBean.sendMessage();
	}
	
	public void sendMessage() {

		try {
			String sender = "mpango@gmail.com";
			String recipients = email;
			String subject = "mPango password reset confirmation";
			String content = "<p>Hi there,</P>" +
					"<p>There was recently a request to change the password on your account.</P>" +
					"<p>If you requested this password change, please set a new password by following the link below:</P>" +
					"<a href='http://localhost:8080/mpango-web/directory/recover.jsf'>http://localhost:8080/mpango-web/directory/recover.jsf</a>" +
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
}