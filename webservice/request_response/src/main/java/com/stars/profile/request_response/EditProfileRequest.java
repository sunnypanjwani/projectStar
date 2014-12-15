package com.stars.profile.request_response;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "EditProfileRequest")
public class EditProfileRequest {

	private long userProfilesId;
	private String talentType;
	private String profileName;

	@XmlElement(name = "profileName")
	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	@XmlElement(name = "talentType")
	public String getTalentType() {
		return talentType;
	}

	public void setTalentType(String talentType) {
		this.talentType = talentType;
	}

	@XmlElement(name = "userProfilesId")
	public long getUserProfilesId() {
		return userProfilesId;
	}

	public void setUserProfilesId(long userProfilesId) {
		this.userProfilesId = userProfilesId;
	}

	public String marshal() throws Exception {
		JAXBContext jaxbContext = JAXBContext
				.newInstance(EditProfileRequest.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(this, sw);
		return sw.toString();
	}
}
