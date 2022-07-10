package com.fse.eauction.model;

public class GetBidsResponseBuyerEntity {

	private String firstName;

	private String lastName;

	private String address;

	private String city;

	private String state;

	private String pin;

	private String phone;

	private String email;

	public GetBidsResponseBuyerEntity() {
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

	public static class Builder {

		private String firstName;
		private String lastName;
		private String address;
		private String city;
		private String state;
		private String pin;
		private String phone;
		private String email;

		public Builder() {    
		}

		Builder(String firstName, String lastName, String address, String city, String state, String pin, String phone, String email) {    
			this.firstName = firstName; 
			this.lastName = lastName; 
			this.address = address; 
			this.city = city; 
			this.state = state; 
			this.pin = pin; 
			this.phone = phone; 
			this.email = email;             
		}

		public Builder firstName(String firstName){
			this.firstName = firstName;
			return Builder.this;
		}

		public Builder lastName(String lastName){
			this.lastName = lastName;
			return Builder.this;
		}

		public Builder address(String address){
			this.address = address;
			return Builder.this;
		}

		public Builder city(String city){
			this.city = city;
			return Builder.this;
		}

		public Builder state(String state){
			this.state = state;
			return Builder.this;
		}

		public Builder pin(String pin){
			this.pin = pin;
			return Builder.this;
		}

		public Builder phone(String phone){
			this.phone = phone;
			return Builder.this;
		}

		public Builder email(String email){
			this.email = email;
			return Builder.this;
		}

		public GetBidsResponseBuyerEntity build() {

			return new GetBidsResponseBuyerEntity(this);
		}
	}

	private GetBidsResponseBuyerEntity(Builder builder) {
		this.firstName = builder.firstName; 
		this.lastName = builder.lastName; 
		this.address = builder.address; 
		this.city = builder.city; 
		this.state = builder.state; 
		this.pin = builder.pin; 
		this.phone = builder.phone; 
		this.email = builder.email;     
	}
}
