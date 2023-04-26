package com.cts.MovieBookingApp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.MovieBookingApp.models.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserTest {
	
	@Test
	public void testUser() {
		User user  = new User();
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testGetterandSetters() {
		User user = new User();
		user.setContactNumber("1234567890");
		user.setPassword("password");
		user.setEmail("xyz@gmail.com");
		user.setFirstName("xyz");
		user.setLastName("abc");
		user.setLoginId(123);
		
		assertEquals(user.getLoginId(), 123);
		assertEquals(user.getFirstName(), "xyz");
		assertEquals(user.getEmail(), "xyz@gmail.com");
		assertEquals(user.getLastName(), "abc");
		assertEquals(user.getPassword(), "password");
		assertEquals(user.getContactNumber(), "1234567890");
		
	}
	
	@Test
	public void testConstructor() {
		User user = new User(123,"xyz@gmail.com","xyz","abc","password","1234567890");
		
		assertEquals(user.getLoginId(), 123);
		assertEquals(user.getFirstName(), "xyz");
		assertEquals(user.getEmail(), "xyz@gmail.com");
		assertEquals(user.getLastName(), "abc");
		assertEquals(user.getPassword(), "password");
		assertEquals(user.getContactNumber(), "1234567890");
	}

}
