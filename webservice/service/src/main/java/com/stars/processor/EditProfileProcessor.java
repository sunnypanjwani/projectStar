package com.stars.processor;

import java.util.logging.Logger;

import javax.persistence.NoResultException;

import com.stars.exception.InvalidRequestException;
import com.stars.persistence.dao.UserProfiles;
import com.stars.profile.request_response.EditProfileRequest;

public class EditProfileProcessor {

	private static Logger log = Logger.getLogger(EditProfileProcessor.class
			.getName());
	private EditProfileRequest request;
	private UserProfiles userProfiles;

	public EditProfileProcessor(EditProfileRequest request) {
		this.request = request;
	}

	public void editProfile() throws InvalidRequestException, Exception {

		isRequestValid();
		updateProfile();
	}

	private void updateProfile() throws Exception {

		log.info("Updating profile");
		userProfiles.getProfile().setName(request.getProfileName());
		userProfiles.getProfile().getTalent().setType(request.getTalentType());
		userProfiles.save();
		log.info("Profile updated successfully");
	}

	private void isRequestValid() throws InvalidRequestException, Exception {

		log.info("validating editProfile request");
		try {
			userProfiles = UserProfiles.load(request.getUserProfilesId());
		} catch (NoResultException ex) {
			throw new InvalidRequestException(
					"UserProfiles not found for userProfilesId: "
							+ request.getUserProfilesId());
		}

		log.info("editProfile request validation successful");
	}
}
