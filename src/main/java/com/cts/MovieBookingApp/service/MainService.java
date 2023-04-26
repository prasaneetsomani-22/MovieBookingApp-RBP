package com.cts.MovieBookingApp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cts.MovieBookingApp.exception.MovieNotFoundException;
import com.cts.MovieBookingApp.models.Movie;
import com.cts.MovieBookingApp.models.RequestedUser;
import com.cts.MovieBookingApp.models.Ticket;

@Service
public interface MainService {
	
	public boolean authorizeUser(RequestedUser user);
	public boolean forgotPassword(RequestedUser user);
	public List<Movie> searchByMovieName(String query) ;
	//public List<Movie> displayMovieTheatres(String moviename);
	public boolean updateTicketCount(Movie.MovieKey key,int noOfTickets) throws MovieNotFoundException;

}
