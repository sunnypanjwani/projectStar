package com.stars.processor;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang.RandomStringUtils;

import com.stars.exception.InvalidSubscriptionException;
import com.stars.persistence.dao.*;
import com.stars.user.request_response.AddUserRequest;
import com.stars.user.request_response.AddUserResponse;
import com.stars.user.request_response.ChangePasswordRequest;
import com.stars.user.request_response.GetUserResponse;
import com.stars.user.request_response.ValidateUserResponse;
import com.stars.user.request_response.ValidateUserRequest;
import com.stars.utils.EmailHandler;
import com.stars.utils.PasswordUtils;
import com.stars.exception.InvalidPasswordException;

public class UserProcessor{
	private static Logger log = Logger.getLogger(UserProcessor.class.getName());
	
    private static final String MSG_VALID = "Valid";
	private static final String MSG_INVALID = "Invalid";
	private static final String CODE_ERR = "ERR";
	private static final String CODE_OK = "OK";
	private static final String PASSWORD_SALT = "salt";	
    private static final String CHANGE_NOT_REQUIRED = "N";
    private static final String CHANGE_REQUIRED = "Y";
    
    public UserProcessor(){
    }
    
    public AddUserResponse processAddRequest(AddUserRequest request) throws Exception{
    	AddUserResponse response = new AddUserResponse();
    	
    	Users user = createUser(request);
		user.save();
		log.info("User Created successfully with userId: " +user.getUserId());
		createPasswordEntry(user, request);
		EmailHandler.sendWelcomeEmail(user);
		response.setMessage("User Created Successfully");
    	
    	return response;
    }

	private void createPasswordEntry(Users user, AddUserRequest request) throws Exception{
		log.info("Creating password for user: " +user.getUserId());
		Passwords password = new Passwords();
		password.setUser(user);
		String userPassword = PasswordUtils.generatePasswordHash(request.getPassword());
		password.setPasswordHash(userPassword);
		password.setPasswordSalt(PASSWORD_SALT);
		password.setChangeRequired(CHANGE_NOT_REQUIRED);
		password.save();		
	}

	private Users createUser(AddUserRequest request) throws Exception {
		Users user = new Users();
		user.setScreenName(request.getScreenName());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		user.setSex(request.getSex());
		
		log.info("Saving user");
		
		return user;
	}

	public void validateUserSubscription(String screenName, String email) throws InvalidSubscriptionException, Exception{
		log.info("Fetching user with screen Name: " +screenName + " and email: " +email);
		List<Users> users = Users.loadUserByScreenNameOrEmail(screenName, email);
		if(users.size() > 0){
			log.info("User found with screen Name: " +screenName + " and email: " +email +". Subscription Invalid.");
			throw new InvalidSubscriptionException("User Id or Email already in use");
		}
		log.info("No user found with screen Name: " +screenName + " and email: " +email +". Subscription Valid.");
		
	}

	public GetUserResponse getUserByScreenName(String screenName) throws Exception {
		List<Users> user = Users.loadUserByScreenNameOrEmail(screenName, null);
		
		if(user.size() != 1){
			throw new Exception("Error. One user expected with screenName: " +screenName +", Found more or less.");
		}
		
		GetUserResponse response = getUserResponseFromUserDao(user.get(0));
		return response;
	}

	private GetUserResponse getUserResponseFromUserDao(Users user) {
		GetUserResponse responseObj = new GetUserResponse();
		
		responseObj.setUserId(user.getUserId());
		responseObj.setScreenName(user.getScreenName());
		responseObj.setFirstName(user.getFirstName());
		responseObj.setLastName(user.getLastName());
		responseObj.setEmail(user.getEmail());
		
		return responseObj;
	}

	public GetUserResponse getUserByEmail(String email) throws Exception {
		List<Users> user = Users.loadUserByScreenNameOrEmail(null, email);
		
		if(user.size() != 1){
			throw new Exception("Error. One user expected with email: " + email +", Found more or less.");
		}
		
		GetUserResponse response = getUserResponseFromUserDao(user.get(0));
		return response;
	}

	public GetUserResponse getUser(String screenName, String email) throws Exception {
		GetUserResponse responseObj = null;
		
		if(screenName != null){
			responseObj = getUserByScreenName(screenName);
		}else if(email != null){
			responseObj = getUserByEmail(email);
		}else{
			throw new Exception("Atleast one, screenName or email required for getUser");
		}
		return responseObj;
	}

	public ValidateUserResponse validateUser(ValidateUserRequest request){
		ValidateUserResponse response = new ValidateUserResponse();
		
		try{
			Users user = Users.loadUserByScreenName(request.getScreenName());
			log.info("Got user with id: " +user.getUserId());
			Passwords password = Passwords.loadPasswordByUserId(user.getUserId());
			log.info("Got password with id: " +password.getPasswordId());
			
			try{
				PasswordUtils.validatePassword(password.getPasswordHash(), request.getPassword());
				response.setCode(CODE_OK);
				response.setMessage(MSG_VALID);
				if(CHANGE_REQUIRED.equals(password.getChangeRequired())){
					response.setChangeRequired(true);
				}else{
					response.setChangeRequired(false);
				}
			}catch( InvalidPasswordException ex){
				response.setCode(CODE_ERR);
				response.setMessage(MSG_INVALID);
			}
		}catch(Exception ex){
			response.setCode(CODE_ERR);
			response.setMessage(MSG_INVALID);
		}
		
		return response;
	}

	public void createTempPassword(String screenName) throws Exception {
		Users user = Users.loadUserByScreenName(screenName);
		String temporaryPassword = RandomStringUtils.randomAlphanumeric(10);
		log.info("Generating temporary password for user as: " +temporaryPassword);
		
		Passwords password = Passwords.loadPasswordByUserId(user.getUserId());
		password.setPasswordHash(PasswordUtils.generatePasswordHash(temporaryPassword));
		password.setChangeRequired(CHANGE_REQUIRED);
		password.save();
		
		EmailHandler.sendEmailForTemporaryPassword(temporaryPassword, user);
	}

	public void changePassword(ChangePasswordRequest request) throws Exception {
		Users user = Users.loadUserByScreenName(request.getScreenName());
		
		Passwords password = Passwords.loadPasswordByUserId(user.getUserId());
		password.setPasswordHash(PasswordUtils.generatePasswordHash(request.getPassword()));
		password.setChangeRequired(CHANGE_NOT_REQUIRED);
		password.save();
		
		EmailHandler.sendEmailForChangedPassword(user);		
	}
}

