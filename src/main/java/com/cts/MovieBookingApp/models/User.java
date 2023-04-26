package com.cts.MovieBookingApp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

@Document
public class User {
	
	
	@Id
	private int loginId;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String contactNumber;
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int loginId, String email, String firstName, String lastName, String password, String contactNumber) {
		super();
		this.loginId = loginId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.contactNumber = contactNumber;
	}
	public int getLoginId() {
		return loginId;
	}
	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	@Override
	public String toString() {
		return "{\"loginId\":"+loginId+",\"email\":"+ email + ",\"firstName\":" + firstName + ",\"lastName\":" + lastName
				+ ",\"password\":" + password + ",\"contactNumber\":" + contactNumber + "}";
	}
	

}
