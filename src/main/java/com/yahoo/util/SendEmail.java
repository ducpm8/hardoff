package com.yahoo.util;

import java.util.Formatter;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.jsoup.helper.StringUtil;

public class SendEmail {
	public static String username = "";
	
	public static void sendMail(String title, String emailBody) {
		
		//final String username = "tool.hardoff@gmail.com";
		
		if (System.getenv("EMAIL_SPAM") != null && !System.getenv("EMAIL_SPAM").isEmpty()) {
			username = System.getenv("EMAIL_SPAM");
		} else {
			username = "tool.hardoff@gmail.com";
		}
			
		final String username2 = "tool.yahooauction@gmail.com";
		
		final String password = "minhduc1";
		
//		email.send.adress=tool.rakuten@gmail.com
//		email.send.password=minhduc1
//		email.recipient=phaminhduc@gmail.com,ika21jp@yahoo.co.jp
//		email.title=Amazon\u30C4\u30FC\u30EB-[%s]

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session;
		
		if (title.contains("Hardoff")) {
			session = Session.getInstance(props,
					  new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, password);
						}
					  });
		} else {
			session = Session.getInstance(props,
					  new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username2, password);
						}
					  });
		}

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			
			String recipientEmail = "";
			
			if (System.getenv("EMAIL_RECIPIENT") != null && !System.getenv("EMAIL_RECIPIENT").isEmpty()) {
				recipientEmail = System.getenv("EMAIL_RECIPIENT");
			} else {
				recipientEmail = "phaminhduc@gmail.com";
			}
			
			//SEND TO
			//message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse("phaminhduc@gmail.com,0ag66b37388064u@ezweb.ne.jp"));
			
			message.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(recipientEmail));
			message.setSubject(title);

			// Create a multipar message
	        Multipart multipart = new MimeMultipart();
			// Create the message part
	        BodyPart messageBodyPart = new MimeBodyPart();

	        messageBodyPart.setText(emailBody);
	        multipart.addBodyPart(messageBodyPart);
	         
	         // Send the complete message parts
	         message.setContent(multipart);
			
			Transport.send(message);

		} catch (MessagingException e) {
			System.out.println("Exception in sendmail" + e.getMessage());
			throw new RuntimeException(e);
		}
	}
}