package com.stars.user.request_response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GenerateTemporaryPasswordRequest")
public class GenerateTemporaryPasswordRequest{
	private String screenName;
	private String password;
	
	@XmlElement(name = "screenName")
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
}