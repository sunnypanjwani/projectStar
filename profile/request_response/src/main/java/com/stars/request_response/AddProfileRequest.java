package com.stars.request_response;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AddProfileRequest")
public class AddProfileRequest {

	private long userId;
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

	@XmlElement(name = "userId")
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String marshal() throws Exception {
		JAXBContext jaxbContext = JAXBContext
				.newInstance(AddProfileRequest.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(this, sw);
		return sw.toString();
	}
}
