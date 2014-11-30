package com.stars.request_response;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AddUserRequest")
public class AddUserRequest{
	private String screenName;
	private String firstName;
	private String lastName;
	private String email;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String addressCity;
	private String addressState;
	private String addressCountry;
	private String addressZip;
	private String password;
	
	@XmlElement(name = "ScreenName")
	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	@XmlElement(name = "FirstName")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@XmlElement(name = "LastName")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@XmlElement(name = "Email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement(name = "AddressLine1")
	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	@XmlElement(name = "AddressLine2")
	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	@XmlElement(name = "AddressLine3")
	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	@XmlElement(name = "AddressCity")
	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String city) {
		this.addressCity = city;
	}

	@XmlElement(name = "AddressState")
	public String getAddressState() {
		return addressState;
	}

	public void setAddressState(String state) {
		this.addressState = state;
	}

	@XmlElement(name = "AddressCountry")
	public String getAddressCountry() {
		return addressCountry;
	}

	public void setAddressCountry(String country) {
		this.addressCountry = country;
	}

	@XmlElement(name = "AddressZip")
	public String getAddressZip() {
		return addressZip;
	}

	public void setAddressZip(String zip) {
		this.addressZip = zip;
	}
	
	@XmlElement(name = "Password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String marshal() throws Exception{
		JAXBContext jaxbContext = JAXBContext.newInstance(AddUserRequest.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(this, sw);
        return sw.toString();
	}
}
