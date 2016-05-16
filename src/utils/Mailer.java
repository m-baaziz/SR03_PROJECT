package utils;

import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {
	private static Session session = null;
	
	public static Session getSession() {
		if (session == null) {
			try {
				Properties props = new Properties();  
	            InputStream inputStream = Db.class.getClassLoader().getResourceAsStream("/mailer.properties");  
	            props.load(inputStream);

				session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(props.getProperty("username"), props.getProperty("password"));
					}
				  });
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return session;
	}
	
	public static void sendEmail(String to, String subject, String body) {
		try {
			Session session = Mailer.getSession();
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("sr03project@outlook.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(body);

			Transport.send(message);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
