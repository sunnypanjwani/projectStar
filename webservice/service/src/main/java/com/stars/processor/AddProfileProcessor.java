package com.stars.processor;

import java.util.List;
import java.util.logging.Logger;

import com.stars.exception.InvalidRequestException;
import com.stars.persistence.dao.Profiles;
import com.stars.persistence.dao.Talents;
import com.stars.persistence.dao.UserProfiles;
import com.stars.persistence.dao.Users;
import com.stars.persistence.enums.ProfileStatus;
import com.stars.profile.request_response.AddProfileRequest;
import com.stars.profile.request_response.AddProfileResponse;

public class AddProfileProcessor {

	private static Logger log = Logger.getLogger(AddProfileProcessor.class
			.getName());
	private AddProfileRequest request;

	public AddProfileProcessor(AddProfileRequest request) {
		this.request = request;
	}

	public AddProfileResponse processAddProfile()
			throws InvalidRequestException, Exception {

		isRequestValid();
		return addProfile();
	}

	private AddProfileResponse addProfile() throws Exception {
		AddProfileResponse response = new AddProfileResponse();
		Talents talent = createTalent();
		Profiles profile = createProfile(talent);
		createUserProfiles(profile);
		log.info("Profile Created successfully with profileId: "
				+ profile.getProfileId());
		response.setMessage("Profile Created Successfully");
		return response;
	}

	private void isRequestValid() throws InvalidRequestException, Exception {

		List<UserProfiles> usersProfilesList = UserProfiles
				.loadWithUserId(request.getUserId());

		for (UserProfiles usersProfile : usersProfilesList) {

			if (usersProfile.getProfile().getName()
					.equalsIgnoreCase(request.getProfileName())) {
				throw new InvalidRequestException(
						"Profile name alreay exists. Please choose a unique name");
			}
		}
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
		profile.setProfileStatus(ProfileStatus.ACTIVE);
		profile.save();
		log.info("Saving Profile");
		return profile;
	}
}
