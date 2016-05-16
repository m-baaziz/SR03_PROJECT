package utils;

import java.io.InputStream;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

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

}
