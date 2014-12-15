package com.stars.processor;

import java.util.logging.Logger;

import javax.persistence.NoResultException;

import com.stars.exception.InvalidRequestException;
import com.stars.persistence.dao.Profiles;
import com.stars.persistence.enums.ProfileStatus;
import com.stars.profile.request_response.DeleteProfileRequest;

public class DeleteProfileProcessor {

	private static Logger log = Logger.getLogger(DeleteProfileProcessor.class
			.getName());
	private DeleteProfileRequest request;
	private Profiles profile;

	public DeleteProfileProcessor(DeleteProfileRequest request) {
		this.request = request;
	}

	public void processRequest() throws InvalidRequestException, Exception {

		isRequestValid();
		deleteProfile();
	}

	private void deleteProfile() throws Exception {

		log.info("deleting profile with profile id: " + request.getProfileId());
		profile.setProfileStatus(ProfileStatus.INACTIVE);
		profile.save();
		log.info("profile with profile id: " + request.getProfileId()
				+ " set to INACTIVE STATE");
	}

	private void isRequestValid() throws InvalidRequestException, Exception {

		log.info("validating delete profile request");
		try {
			profile = Profiles.load(request.getProfileId());
		} catch (NoResultException ex) {

			throw new InvalidRequestException(
					"Profile not found for profile id: "
							+ request.getProfileId());
		}

		log.info("Delete profile request validation successful");
	}

}
