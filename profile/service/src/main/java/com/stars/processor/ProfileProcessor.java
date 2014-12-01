package com.stars.processor;

import java.util.logging.Logger;

import com.stars.persistence.dao.Profiles;
import com.stars.persistence.dao.Talents;
import com.stars.persistence.dao.UserProfiles;
import com.stars.persistence.dao.Users;
import com.stars.request_response.AddProfileRequest;
import com.stars.request_response.AddProfileResponse;

public class ProfileProcessor {

	private static Logger log = Logger.getLogger(ProfileProcessor.class
			.getName());
	private AddProfileRequest request;

	public ProfileProcessor(AddProfileRequest request) {
		this.request = request;
	}

	public AddProfileResponse processAddProfile() throws Exception {

		AddProfileResponse response = new AddProfileResponse();
		Talents talent = createTalent();
		Profiles profile = createProfile(talent);
		createUserProfiles(profile);
		log.info("Profile Created successfully with profileId: "
				+ profile.getProfileId());
		response.setMessage("Profile Created Successfully");
		return response;
	}

	private Talents createTalent() throws Exception {

		Talents talent = new Talents();
		talent.setType(request.getTalentType());
		talent.save();
		log.info("Saving talent");
		return talent;
	}

	private void createUserProfiles(Profiles profile) throws Exception {

		UserProfiles userProfiles = new UserProfiles();
		userProfiles.setProfile(profile);
		userProfiles.setUser(Users.loadUserByUserId(request.getUserId()));
		userProfiles.save();
		log.info("Saving UserProfiles");
	}

	private Profiles createProfile(Talents talent) throws Exception {

		Profiles profile = new Profiles();
		profile.setName(request.getProfileName());
		profile.setTalent(talent);
		profile.save();
		log.info("Saving Profile");
		return profile;
	}
}
