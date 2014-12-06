package com.stars.request_response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ValidateUserResponse")
public class ValidateUserResponse{
	private String code;
	private String message;
	private boolean changeRequired;
	
	@XmlElement(name = "code")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@XmlElement(name = "message")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@XmlElement(name = "changeRequired")
	public boolean getChangeRequired() {
		return changeRequired;
	}
	public void setChangeRequired(boolean changeRequired) {
		this.changeRequired = changeRequired;
	}
}
