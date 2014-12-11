package com.stars.utils;

import java.util.logging.Logger;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import com.stars.persistence.dao.Users;

public class  EmailHandler{

	private static Logger log = Logger.getLogger(EmailHandler.class.getName());
	
	private static final String GMAIL_SMTP_PORT = "587";
	private static final String MAIL_SMTP_PORT = "mail.smtp.port";
	private static final String SMTP_GMAIL_COM = "smtp.gmail.com";
	private static final String MAIL_SMTP_HOST = "mail.smtp.host";
	private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	
	private static final String FROM_EMAIL = "starstoriesapp@gmail.com";
	private static final String FROM_PASSWORD = "StarStory";
	
	private static String TEMPORARY_PASSWORD_EMAIL_SUBJECT = "You temporary password for Star Stories!";
	private static String TEMPORARY_PASSWORD_EMAIL_BODY = "Hey Star! \n \n" +
						"Heard that you have lost your password. We understand. $#!T happens. " + 
						"Our trained robots are here to help. " +
						"They helped us generate a temporary password for you. \n\nPlease use following password for login: \n\n"+
						"\tPASSWORD_STRING_GENERATED" + 
						"\n\nAnd remember to change this token ASAP."+
						"\n\nHappy to help. Have a great time." +
						"\n\n -Galaxy Admin";
	
	private static String CHANGED_PASSWORD_EMAIL_SUBJECT = "Star Stories password change";
	private static String CHANGED_PASSWORD_EMAIL_BODY = "Hey Star! \n \n" +
						"Just to let you know that your Star Stories account password has been changed. " + 
						"\n\nIf it was not you who did this, please contact us ASAP."+
						"\n\nHave a great time." +
						"\n\n -Galaxy Admin";
	
	private static String WELCOME_EMAIL_SUBJECT = "Welcome to Star Stories";
	private static String WELCOME_EMAIL_BODY = "Hey Star! \n \n" +
						"Glad to see you here. Start your journey here:" + 
						"\n\n\tTell your fascinating stories. Let people know who you are, why you are."+
						"\n\tRead trending stories. Watch trending media. Meet trending Stars."+
						"\n\tConnect with best and latest talent. Meet people looking for talent. Get noticed."+
						"\n\nHave a great time." +
						"\n\n -Galaxy Admin";
						
	
	public static void sendEmailForTemporaryPassword(String temporaryPassword,
			Users user) throws Exception {
		log.info("Sending email with temporary password for user: " +user.getScreenName());
		String userEmail = user.getEmail();
		MimeMessage message = createInitMimeMessage();
        try{
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
	        message.setSubject(TEMPORARY_PASSWORD_EMAIL_SUBJECT);
	        message.setText(TEMPORARY_PASSWORD_EMAIL_BODY.replace("PASSWORD_STRING_GENERATED", temporaryPassword));
	         
	        log.info("Sending email from: " +FROM_EMAIL);
	        log.info("Sending email to: " +userEmail);

	        Transport.send(message);
	        log.info("Sent message successfully");
	      }catch (Exception ex) {
	    	  ex.printStackTrace();
	    	  throw ex;
	      }
	}

	private static MimeMessage createInitMimeMessage()
			throws MessagingException, AddressException {
		Properties properties = System.getProperties();
		properties.put(MAIL_SMTP_AUTH, "true");
		properties.put(MAIL_SMTP_STARTTLS_ENABLE, "true");
		properties.put(MAIL_SMTP_HOST, SMTP_GMAIL_COM);
		properties.put(MAIL_SMTP_PORT, GMAIL_SMTP_PORT);
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
			}
		});
		
		MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL));
		return message;
	}

	public static void sendEmailForChangedPassword(Users user) throws Exception {
		log.info("Sending email with temporary password for user: " +user.getScreenName());
		String userEmail = user.getEmail();
		MimeMessage message = createInitMimeMessage();
        try{
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
	        message.setSubject(CHANGED_PASSWORD_EMAIL_SUBJECT);
	        message.setText(CHANGED_PASSWORD_EMAIL_BODY);
	         
	        log.info("Sending email from: " +FROM_EMAIL);
	        log.info("Sending email to: " +userEmail);

	        Transport.send(message);
	        log.info("Sent message successfully");
	      }catch (Exception ex) {
	    	  ex.printStackTrace();
	    	  throw ex;
	      }	
	}

	public static void sendWelcomeEmail(Users user) throws Exception {
		log.info("Sending email with temporary password for user: " +user.getScreenName());
		String userEmail = user.getEmail();
		MimeMessage message = createInitMimeMessage();
        try{
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
	        message.setSubject(WELCOME_EMAIL_SUBJECT);
	        message.setText(WELCOME_EMAIL_BODY);
	         
	        log.info("Sending email from: " +FROM_EMAIL);
	        log.info("Sending email to: " +userEmail);

	        Transport.send(message);
	        log.info("Sent message successfully");
	      }catch (Exception ex) {
	    	  ex.printStackTrace();
	    	  throw ex;
	      }	
		
	}

}
