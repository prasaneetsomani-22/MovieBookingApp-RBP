package com.cts.MovieBookingApp.controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

import org.apache.catalina.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebInputException;

import com.cts.MovieBookingApp.exception.MovieNotFoundException;
import com.cts.MovieBookingApp.models.Movie;
import com.cts.MovieBookingApp.models.RequestedUser;
import com.cts.MovieBookingApp.models.Ticket;
import com.cts.MovieBookingApp.models.User;
import com.cts.MovieBookingApp.repository.MovieRepository;
import com.cts.MovieBookingApp.repository.TicketRepository;
import com.cts.MovieBookingApp.repository.UserRepository;
import com.cts.MovieBookingApp.service.MainService;



@RestController
//@RequestMapping("/api/v1.0/MovieBooking")
public class MainController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private MainService service;
	
	
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		userRepository.save(user);
		return new ResponseEntity<String>("User Registered Successfully",HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public boolean loginUser(@RequestBody RequestedUser user) {
		//boolean isAuthorized = ;
		if(service.authorizeUser(user)) {
			//return new ResponseEntity<String>("User Logged In Successfully",HttpStatus.OK);
			return true;
		}
		//return new ResponseEntity<String>("User not found",HttpStatus.OK);
		return false;
			
	}
	
	@PostMapping("/forgot")
	public ResponseEntity<?> forgotpassword(@RequestBody RequestedUser user) {
		if(service.forgotPassword(user)) {
			return new ResponseEntity<String>("Password reseted successfully",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Something Went Wrong",HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Movie>> getAllMovies(){
		List<Movie> movies = movieRepository.findAll();
		return new ResponseEntity<List<Movie>>(movies,HttpStatus.OK);
	}
	
	@GetMapping("/movies/search/{query}")
	public ResponseEntity<?> searchMovie(@PathVariable("query") String query) {
		
		List<Movie> movie = service.searchByMovieName(query);
		if(movie!=null) {
			return new ResponseEntity<List<Movie>>(movie,HttpStatus.OK);
		}
		return new ResponseEntity<String>("No movie Found",HttpStatus.OK);
		
		
	}
	
	@PostMapping("/movieName/add")
	public ResponseEntity<?> addMovie(@RequestBody Movie movie){
		movieRepository.save(movie);
		return new ResponseEntity<Movie>(movie,HttpStatus.OK);
	}
	
//	@GetMapping("/{moviename}/theatres")
//	public List<Movie> getThreatreList(@PathVariable("moviename") String moviename){
//		return service.displayMovieTheatres(moviename);
//		//return "Pathan theatres";
//	}
	
	@PostMapping("/bookTicket")
	public ResponseEntity<?> bookTicket(@RequestBody Ticket ticket) throws MovieNotFoundException{
		System.out.println(ticket.getMovieName());
		if(service.updateTicketCount(new Movie.MovieKey(ticket.getMovieName(),ticket.getTheatreName()),ticket.getNoOfTickets())) {
		ticketRepository.save(ticket);
		
		return new ResponseEntity<String>("Ticket Booked",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Tickets unavailable",HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{moviename}/{theatrename}")
	public ResponseEntity<?> deleteMovie(@PathVariable(value = "moviename") String moviename,@PathVariable(value = "theatrename") String theatrename){
		movieRepository.deleteById(new Movie.MovieKey(moviename, theatrename));
		return new ResponseEntity<String>("Movie Delted Successfully",HttpStatus.OK);
	}
	
	
	@GetMapping("/check")
	public String check() {	
		return "Hello World";
	}
	
	
	
}
