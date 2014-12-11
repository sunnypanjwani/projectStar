package com.stars.utils;

import java.util.logging.Logger;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import com.stars.persistence.dao.Users;

public class  EmailHandler{

	private static Logger log = Logger.getLogger(EmailHandler.class.getName());
	private static final String FROM_EMAIL = "stars@starstories.com";
	
	public static void sendEmailForTemporaryPassword(String temporaryPassword,
			Users user) throws Exception {
		log.info("Sending email with temporary password for user: " +user.getScreenName());
		
		String userEmail = user.getEmail();
		String host = "localhost";
		
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		
		Session session = Session.getDefaultInstance(properties);
		try{
	         MimeMessage message = new MimeMessage(session);
	         message.setFrom(new InternetAddress(FROM_EMAIL));

	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(userEmail));

	         message.setSubject("This is the Subject Line!");
	         message.setText("This is actual message");

	         Transport.send(message);
	         log.info("Sent message successfully");
	      }catch (Exception ex) {
	    	  ex.printStackTrace();
	    	  throw ex;
	      }
	}

	public static void sendEmailForChangedPassword(Users user) {
		// TODO Auto-generated method stub
		
	}

}
