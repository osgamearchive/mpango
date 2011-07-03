package net.sourceforge.mpango.email;

import java.io.Serializable;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Send an Email via Gmail SMTP server using SSL connection.
 * 
 * @author devdlee
 * 
 */
public class SendMail implements Serializable {
	
	private static final String username = ""; // gmail account username
	private static final String password = ""; // gmail account password

	private String sender;
	private String recipient;
	private String subject;
	private String content;
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public SendMail() {
		
	}
	
	public SendMail(String sender, String recipient, String subject, String content) {
		this.sender = sender;
		this.recipient = recipient;
		this.subject = subject;
		this.content = content;
	}
	
//	public static void send(String sender, String recipient, String subject, String content) {
	public void send() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sender));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject(subject);
			message.setContent(content, "text/html");
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		
		String sender = "dlee0113@gmail.com";
		String recipients = "dlee0113@gmail.com";
		String subject = "Email Class Test";
		String content = "<h1>HEADER 1</h1>";
		
		SendMail sendMail = new SendMail(sender, recipients, subject, content);
		sendMail.send();
	}
}
