package net.sourceforge.mpango.web.directory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.context.ApplicationContext;

import net.sourceforge.mpango.directory.dao.UserDAO;
import net.sourceforge.mpango.directory.dto.UserDTO;
import net.sourceforge.mpango.directory.entity.User;
import net.sourceforge.mpango.directory.service.ApplicationContextService;
import net.sourceforge.mpango.email.SendMail;
import net.sourceforge.mpango.enums.StateEnum;
import net.sourceforge.mpango.jms.ActiveMQ;

@ManagedBean
@RequestScoped
public class ChangePasswordRequestBackingBean {

	private static ApplicationContext context = ApplicationContextService.getApplicationContext();
	
	private String email;
	private String newPassword;
	private String retypePassword;
	private boolean passwordChangedFlag = false;
//	private boolean accountFound = false;
	private String port = "8080";
	private String queueName = "TestQueue";

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
	
	private static String getHostName() {
		String hostname = "";

		try {
			InetAddress addr = InetAddress.getLocalHost();
			// Get IP Address
			byte[] ipAddr = addr.getAddress();
			// Get hostname
			hostname = addr.getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return hostname;
	}

	public void sendMessage() {
		System.out.println("sendMessage() start");
		
		if (checkAccount(email)) {
			try {
				String sender = "mpango@gmail.com";
				String recipients = email;
				String subject = "mPango password reset confirmation";
				String content = "<p>Hi there,</P>" + "<p>There was recently a request to change the password on your account.</P>"
						+ "<p>If you requested this password change, please set a new password by following the link below:</P>"
						+ "<a href='http://" + getHostName() + ":" + port + "/mpango-web/directory/changePassword.jsf?reset_key=" + generateResetKey(email) + "'>http://" + getHostName() + ":" + port + "/mpango-web/directory/recover.jsf&#63;reset_key=" + generateResetKey(email) + "</a>"
						+ "<p>If you don't want to change your password, just ignore this message.</P>" + "<p>Thanks</p>";
				SendMail sendMail = new SendMail(sender, recipients, subject, content);
	
				ActiveMQ activeMQ = new ActiveMQ();
				Session session = activeMQ.setUp(queueName);
				ObjectMessage message = session.createObjectMessage();
				message.setObject(sendMail);
				activeMQ.createProducerAndSendAMessage(message);
				activeMQ.createConsumerAndReceiveAMessage();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean checkAccount(String email) {
		boolean found = false;
		
		UserDAO userDAO = (UserDAO)context.getBean("userDAO");
		User user = userDAO.load(email);
		if (user != null) {
			found = true;
		}
		
		return found;
	}
	
	public void changePassword() {
		UserDTO user = new UserDTO();

		if (newPassword.equalsIgnoreCase(retypePassword)) {
			user.setPassword(newPassword);
			passwordChangedFlag = true;
		}
	}
	
	private static String generateResetKey(String email) {
		String resetKey = String.valueOf(UUID.randomUUID());
		UserDAO userDAO = (UserDAO)context.getBean("userDAO");
		User user = userDAO.load(email);
		user.setResetKey(resetKey);
		
		return resetKey;
	}

	public static void main(String[] args) {
		
		ApplicationContext context = ApplicationContextService.getApplicationContext();
		UserDAO userDAO = (UserDAO)context.getBean("userDAO");
		User user  = new User();
		user.setDateOfBirth(Calendar.getInstance().getTime());
		user.setEmail("dlee0113@gmail.com");
		user.setGender("male");
		user.setPassword("pwd");
		user.setUsername("user");
		user.setState(StateEnum.CREATED);
		user = userDAO.save(user);
		
		ChangePasswordRequestBackingBean changePasswordRequestBackingBean = new ChangePasswordRequestBackingBean();
		changePasswordRequestBackingBean.setEmail("dlee0113@gmail.com");
		changePasswordRequestBackingBean.sendMessage();
	}
}