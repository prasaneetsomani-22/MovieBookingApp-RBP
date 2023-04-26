package com.cts.MovieBookingApp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.MovieBookingApp.models.Movie;

@SpringBootTest
public class MovieTest {

	@Test
	public void testMobie() {
		Movie movie = new Movie();
		assertThat(movie).isNotNull();
	}
	
	@Test
	public void testGettersAndSetters() {
		Movie movie = new Movie();
		Movie.MovieKey movieKey = new Movie.MovieKey();
		movieKey.setMovieName("Pathan");
		movieKey.setTheatreName("PVR");
		
		movie.setId(movieKey);
		movie.setAllotedSeats(200);
		
		assertEquals(movie.getId().getMovieName(), "Pathan");
		assertEquals(movie.getId().getTheatreName(), "PVR");
		assertEquals(movie.getAllotedSeats(), 200);
	}
	
	@Test
	public void testConstructor() {
		Movie movie = new Movie(new Movie.MovieKey("Pathan","PVR"),200);
		
		assertEquals(movie.getId().getMovieName(), "Pathan");
		assertEquals(movie.getId().getTheatreName(), "PVR");
		assertEquals(movie.getAllotedSeats(), 200);
	}
	
}
