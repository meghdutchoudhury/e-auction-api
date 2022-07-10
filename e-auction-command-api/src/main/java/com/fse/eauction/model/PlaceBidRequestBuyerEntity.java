package com.fse.eauction.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

public class PlaceBidRequestBuyerEntity {
	
	@NotNull
    @Size(min=5, max=30)
	@Schema(description = "First name of the buyer", example="Johnny", required = true)
	private String firstName;
	
	@NotNull
    @Size(min=3, max=25)
	@Schema(description = "Last name of the buyer", example="Doe", required = true)
	private String lastName;
	
	@Schema(description = "Address of the buyer", example="12, New Town", required = false)
	private String address;
	
	@Schema(description = "City of the buyer", example="Kolkata", required = false)
	private String city;
	
	@Schema(description = "State of the buyer", example="WB", required = false)
	private String state;
	
	@Schema(description = "PIN of the buyer", example="700001", required = false)
	private String pin;
	
	@NotNull
	@Pattern(regexp="[\\d]{10}")
	@Schema(description = "Phone number of the buyer", example="9009001234", required = true)
	private String phone;
	
	@NotNull
	@Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", flags = Flag.UNICODE_CASE)
	@Schema(description = "Email of the buyer", example="john@doe.com", required = false)
	private String email;

	public PlaceBidRequestBuyerEntity() {
		super();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "PlaceBidRequest [firstName=" + firstName
				+ ", lastName=" + lastName + ", address=" + address + ", city=" + city + ", state=" + state + ", pin="
				+ pin + ", phone=" + phone + ", email=" + email + "]";
	}

	
}
