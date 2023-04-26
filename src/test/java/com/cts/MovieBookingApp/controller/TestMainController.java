package com.cts.MovieBookingApp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cts.MovieBookingApp.MovieBookingAppApplication;
import com.cts.MovieBookingApp.exception.MovieNotFoundException;
import com.cts.MovieBookingApp.models.Movie;
import com.cts.MovieBookingApp.models.RequestedUser;
import com.cts.MovieBookingApp.models.Ticket;
import com.cts.MovieBookingApp.models.User;
import com.cts.MovieBookingApp.repository.MovieRepository;
import com.cts.MovieBookingApp.repository.UserRepository;
import com.cts.MovieBookingApp.service.MainService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@WebAppConfiguration
@TestInstance(Lifecycle.PER_CLASS)
public class TestMainController {
	
	protected MockMvc mvc;
	
	@Autowired
	WebApplicationContext webApplicationContext;
	
	@MockBean
	private MovieRepository movieRepository;
	
	@MockBean
	private MainService service;
	
	@MockBean
	private UserRepository userRepository;
	
	protected void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@BeforeAll
	public void before() {
		setUp();
	}
	
	protected String mapToJson(Object object) throws JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
	protected <T> T mapFromJson(String json,Class<T> clazz) throws JsonParseException,JsonMappingException,IOException{
		
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
		
	}
	
	@Test
	public void TestRegister() throws Exception{
		String uri = "/register";
		User user = new User(103,"srk@gmail.com","Sharukh","Khan","srk@123","1234567890");
		String body = mapToJson(user);
		when(userRepository.save(user)).thenReturn(user);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).
				content(body)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals("User Registered Successfully", content);
	}
	
	@Test
	public void TestLogin() throws Exception{
		String uri = "/login";
		RequestedUser user = new RequestedUser("srk@gmail.com","srk@123");
		String body = mapToJson(user);
		when(service.authorizeUser(Mockito.any(RequestedUser.class))).thenReturn(true);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).
				content(body)).andReturn();
		
		//System.out.println(mvcResult.getResponse().getContentAsString());
		assertEquals(mvcResult.getResponse().getContentAsString(), "true");
	}
	
	@Test
	public void TestForgot() throws Exception{
		String uri = "/forgot";
		RequestedUser user = new RequestedUser("srk@gmail.com","srk@123");
		String body = mapToJson(user);
		when(service.forgotPassword(Mockito.any(RequestedUser.class))).thenReturn(true);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).
				content(body)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String msg = mvcResult.getResponse().getContentAsString();
		assertEquals("Password reseted successfully",msg);
		
		when(service.forgotPassword(Mockito.any(RequestedUser.class))).thenReturn(false);
		mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).
				content(body)).andReturn();
		msg = mvcResult.getResponse().getContentAsString();
		assertEquals("Something Went Wrong",msg);
		
		
	}
	
	@Test
	public void TestGetAllMovies() throws Exception{
		String uri = "/all";
		when(movieRepository.findAll()).thenReturn(List.of(new Movie(new Movie.MovieKey("Pathan", "INOX"),20),
				new Movie(new Movie.MovieKey("Pathan", "AMC"),20)));
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		String list = mvcResult.getResponse().getContentAsString();
		Movie[] moviesList = mapFromJson(list, Movie[].class);
		assertEquals(2, moviesList.length);
		
		
	}
	
	@Test
	public void TestSearchMoviesByName() throws Exception{
		String uri ="/movies/search/R";
		List<Movie> movies = new ArrayList<>();
		movies.add(new Movie(new Movie.MovieKey("Pathan", "PVR"),10));
		when(service.searchByMovieName(Mockito.anyString())).thenReturn(movies);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		String list = mvcResult.getResponse().getContentAsString();
		Movie[] moviesList = mapFromJson(list, Movie[].class);
		System.out.println(list);
		assertEquals(1, moviesList.length);
		
	}
	
	@Test
	public void testAddMovie() throws Exception{
		String uri = "/movieName/add";
		Movie movie = new Movie(new Movie.MovieKey("Pathan", "INOX"),20);
		String body = mapToJson(movie);
		when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(movie);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).
				content(body)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Movie resMovie = mapFromJson(content, Movie.class);
		assertEquals(movie.getId().getMovieName(), resMovie.getId().getMovieName());
		
		
	}
	
	@Test
	public void testBookTicket() throws MovieNotFoundException,Exception{
		String uri = "/bookTicket";
		List<String> seatsList = new ArrayList<>();
		seatsList.add("h1");
		seatsList.add("h2");
		seatsList.add("h3");
		Ticket ticket = new Ticket(101,"Pathan","PVR",3,seatsList);
		Movie.MovieKey key = new Movie.MovieKey("Pathan","PVR");
		String body = mapToJson(ticket);
		when(service.updateTicketCount(Mockito.any(Movie.MovieKey.class),Mockito.anyInt())).thenReturn(true);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).
				content(body)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		String msg = mvcResult.getResponse().getContentAsString();
		assertEquals("Ticket Booked",msg);
		
	}
	
	@Test
	public void TestDelteMovie() throws Exception{
		String uri = "/delete/Pathan/PVR";
		//doNothing(movieRepository.deleteById(Mockito.any(Movie.MovieKey.class)));
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		String msg = mvcResult.getResponse().getContentAsString();
		assertEquals("Movie Delted Successfully",msg);
		
		
	}

}
