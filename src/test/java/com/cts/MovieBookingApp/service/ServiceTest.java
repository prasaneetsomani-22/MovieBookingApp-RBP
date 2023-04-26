package com.cts.MovieBookingApp.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cts.MovieBookingApp.exception.MovieNotFoundException;
import com.cts.MovieBookingApp.models.Movie;
import com.cts.MovieBookingApp.models.Movie.MovieKey;
import com.cts.MovieBookingApp.models.RequestedUser;
import com.cts.MovieBookingApp.models.Ticket;
import com.cts.MovieBookingApp.models.User;
import com.cts.MovieBookingApp.repository.MovieRepository;
import com.cts.MovieBookingApp.repository.UserRepository;

import net.bytebuddy.NamingStrategy.Suffixing.BaseNameResolver.ForGivenType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class ServiceTest {
	
	@Autowired
	private MainService service;
	
	@MockBean
	private MovieRepository movieRepository;
	
	@MockBean
	private UserRepository userRepository;
	
	@Test
	public void testServiceClass() {
		assertThat(service).isNotNull();
	}
	
	
	@Test
	public void testauthorizeUser() {
		RequestedUser user = new RequestedUser("srk@gmail.com", "srk@123");
		User user1 = new User(103,"srk@gmail.com","Sharukh","Khan","srk@123","1234567890");
		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user1));
		assertEquals(service.authorizeUser(user), true);
		assertEquals(service.authorizeUser(new RequestedUser("sr@gmail.com", "srk@123")), false);
	}
	
	@Test
	public void testForgotPassword() {
		RequestedUser user = new RequestedUser("srk@gmail.com", "abc@123");
		User user1 = new User(102,"srk@gmail.com","Sharukh","Khan","srk@123","1234567890");
		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user1));
		//doNothing().when(userRepository.save(user1));
		assertEquals(service.forgotPassword(user), true);
		assertEquals(service.forgotPassword(new RequestedUser("sr@gmail.com", "srk@123")), false);
	}
	
	@Test
	public void testSearchByMovieName() {
		List<Movie> movies = new ArrayList<>();
		movies.add(new Movie(new Movie.MovieKey("Pathan","PVR"),100));
		movies.add(new Movie(new Movie.MovieKey("Pathan","INOX"),200));
		
		when(movieRepository.findAll()).thenReturn(movies);
		assertEquals(service.searchByMovieName("pat"), movies);

	}
	
	@Test
	public void testUpdateTicketCount() throws MovieNotFoundException {
		List<String> seatsList = new ArrayList<>();
		seatsList.add("h1");
		seatsList.add("h2");
		seatsList.add("h3");
		Ticket ticket = new Ticket(101,"Pathan","PVR",3,seatsList);
		//Movie movie = new Movie(new Movie.MovieKey("Pathan","PVR"),5);
		Movie.MovieKey movieKey = new Movie.MovieKey("Pathan", "PVR");
		Optional<Movie> movie = Optional.of(new Movie(movieKey,5));
		//System.out.println(movie.isPresent());
		when(movieRepository.findById(movieKey)).thenReturn(movie);
		Optional<Movie> result = movieRepository.findById(movieKey);
		assertNotNull(result);
		assertEquals(service.updateTicketCount(movieKey,3),true);
		//Mockito.verify(movieRepository).findById(movieKey);
		
		assertEquals(service.updateTicketCount(movieKey, 6), false);
		//assertThrows(service.updateTicketCount(new Movie.MovieKey("Pathan", "PVR"),5),new MovieNotFoundException("Movie not found"));
	}

}
