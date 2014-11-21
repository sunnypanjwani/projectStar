package com.stars.processor;
import java.util.logging.Logger;

import com.stars.persistence.dao.*;
import com.stars.request_response.AddUserRequest;
import com.stars.request_response.AddUserResponse;


public class UserProcessor{
        private static Logger log = Logger.getLogger(UserProcessor.class.getName());
        
        public UserProcessor(){
        }
        
        public AddUserResponse processAddRequest(AddUserRequest request) throws Exception{
        	AddUserResponse response = new AddUserResponse();
        	
        	try{
        		Users user = createUser(request);
        		user.save();
        		log.info("User Created successfully with userId: " +user.getUserId());
        	}
        	catch (Exception e) {
        		e.printStackTrace();
        		throw e;
        	}
            
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
}

