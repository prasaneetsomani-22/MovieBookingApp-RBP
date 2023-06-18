package com.cts.MovieBookingApp.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.io.BasicOutputBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.MovieBookingApp.exception.MovieNotFoundException;
import com.cts.MovieBookingApp.models.Movie;
import com.cts.MovieBookingApp.models.RequestedUser;
import com.cts.MovieBookingApp.models.Ticket;
import com.cts.MovieBookingApp.models.User;
import com.cts.MovieBookingApp.repository.MovieRepository;
import com.cts.MovieBookingApp.repository.UserRepository;

@Service
public class MainServiceImpl implements MainService {
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User authorizeUser(RequestedUser user) {
		Optional<User> dbuser = userRepository.findByEmail(user.getEmail());
		if(dbuser.isPresent() && user.getPassword().equals(dbuser.get().getPassword())) {
			return dbuser.get();
		}
		return null;
	}
	
	@Override
	public boolean forgotPassword(RequestedUser user) {
		Optional<User> dbuser = userRepository.findByEmail(user.getEmail());
		if(dbuser.isPresent()) {
			User user1 = dbuser.get();
			user1.setPassword(user.getPassword());
			userRepository.save(user1);
			return true;
		}
		return false;
	}

	@Override
	public List<Movie> searchByMovieName(String query) {
		List<Movie> movies = movieRepository.findAll();
		String finalquery = query.toLowerCase();
		movies = movies.stream().filter((movie)->movie.getId().getMovieName()
				.contains(finalquery)).collect(Collectors.toList());
		return movies;
	}


//	@Override
//	public List<Movie> displayMovieTheatres(String moviename) {
//		List<Movie> movies = movieRepository.findAll();
//		movies = movies.stream().filter((movie)->movie.getId().getMovieName().
//				equals(moviename)).collect(Collectors.toList());
//		return movies;
//		
//	}

	@Override
	public boolean updateTicketCount(Movie.MovieKey key,int noOfTickets) throws MovieNotFoundException{
		//System.out.println(key.getMovieName());
		Optional<Movie> movie = movieRepository.findById(key);
		//System.out.println(movie.isPresent());
		if(!movie.isPresent()) {
			throw new MovieNotFoundException("Movie Not Found");
		}
		
			if(noOfTickets<movie.get().getAllotedSeats()) {
				int totalcount = movie.get().getAllotedSeats() - noOfTickets;
				movie.get().setAllotedSeats(totalcount);
				movieRepository.save(movie.get());
				return true;
			}
		
		
		return false;
		
	}

	


	

}
