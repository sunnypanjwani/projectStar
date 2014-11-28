package com.stars.processor;

import java.util.List;
import java.util.logging.Logger;

import com.stars.exception.InvalidSubscriptionException;
import com.stars.persistence.dao.*;
import com.stars.persistence.dbaccess.PersistenceManager;
import com.stars.persistence.dbaccess.PersistenceManagerFactory;
import com.stars.request_response.AddUserRequest;
import com.stars.request_response.AddUserResponse;
import com.stars.request_response.GetUserResponse;

public class UserProcessor {
	private static Logger log = Logger.getLogger(UserProcessor.class.getName());

	public UserProcessor() {
	}

	public AddUserResponse processAddRequest(AddUserRequest request)
			throws Exception {
		
		AddUserResponse response = new AddUserResponse();
		Users user = createUser(request);
		user.save();
		log.info("User Created successfully with userId: " + user.getUserId());
		response.setMessage("User Created Successfully");
		return response;
	}

	private Users createUser(AddUserRequest request) throws Exception {
		Users user = new Users();
		user.setScreenName(request.getScreenName());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		user.setAddressLine1(request.getAddressLine1());
		user.setAddressLine2(request.getAddressLine2());
		user.setAddressLine3(request.getAddressLine3());
		user.setCity(request.getAddressCity());
		user.setState(request.getAddressState());
		user.setCountry(request.getAddressCountry());
		user.setZip(request.getAddressZip());

		log.info("Saving user");

		return user;
	}

	public void validateUserSubscription(String screenName, String email)
			throws InvalidSubscriptionException, Exception {
		log.info("Fetching user with screen Name: " + screenName
				+ " and email: " + email);
		List<Users> users = Users
				.loadUserByScreenNameOrEmail(screenName, email);
		if (users.size() > 0) {
			log.info("User found with screen Name: " + screenName
					+ " and email: " + email + ". Subscription Invalid.");
			throw new InvalidSubscriptionException(
					"User Id or Email already in use");
		}
		log.info("No user found with screen Name: " + screenName
				+ " and email: " + email + ". Subscription Valid.");

	}

	public GetUserResponse getUserByScreenName(String screenName)
			throws Exception {
		List<Users> user = Users.loadUserByScreenNameOrEmail(screenName, null);

		if (user.size() != 1) {
			throw new Exception("Error. One user expected with screenName: "
					+ screenName + ", Found more or less.");
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
		responseObj.setAddressLine1(user.getAddressLine1());
		responseObj.setAddressLine2(user.getAddressLine2());
		responseObj.setAddressLine3(user.getAddressLine3());
		responseObj.setAddressCity(user.getCity());
		responseObj.setAddressState(user.getState());
		responseObj.setAddressCountry(user.getCountry());
		responseObj.setAddressZip(user.getZip());

		return responseObj;
	}

	public GetUserResponse getUserByEmail(String email) throws Exception {
		List<Users> user = Users.loadUserByScreenNameOrEmail(null, email);

		if (user.size() != 1) {
			throw new Exception("Error. One user expected with email: " + email
					+ ", Found more or less.");
		}

		GetUserResponse response = getUserResponseFromUserDao(user.get(0));
		return response;
	}

	public GetUserResponse getUser(String screenName, String email)
			throws Exception {
		GetUserResponse responseObj = null;

		if (screenName != null) {
			responseObj = getUserByScreenName(screenName);
		} else if (email != null) {
			responseObj = getUserByEmail(email);
		} else {
			throw new Exception(
					"Atleast one, screenName or email required for getUser");
		}
		return responseObj;
	}
}
