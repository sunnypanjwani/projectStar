package com.stars.utils;

import java.security.MessageDigest;
import java.util.logging.Logger;

import com.stars.exception.InvalidPasswordException;

public class PasswordUtils {
	private static Logger log = Logger.getLogger(PasswordUtils.class.getName());
	
	public static String generatePasswordHash(String password) throws Exception {
		return generateEncryptedValue(password);
	}

	private static String generateEncryptedValue(String password)
			throws Exception {
		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(password.getBytes());
        
        StringBuffer sbuffer = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
        	sbuffer.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sbuffer.toString();
	}

	public static void validatePassword(String userPassword, String passwordToValidate) throws Exception {
		String passwordHashPassed = generateEncryptedValue(passwordToValidate);
		
		if(!userPassword.equals(passwordHashPassed)){
			log.info("Password Invalid");
			throw new InvalidPasswordException("Invalid password passed in request");
		}
		log.info("Password valid");
	}

}
