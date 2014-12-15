package com.stars.profile.request_response;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DeleteProfileRequest")
public class DeleteProfileRequest {

	private long profileId;

	@XmlElement(name = "profileId")
	public long getProfileId() {
		return profileId;
	}

	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}
	
	public String marshal() throws Exception {
		JAXBContext jaxbContext = JAXBContext
				.newInstance(DeleteProfileRequest.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(this, sw);
		return sw.toString();
	}
}
