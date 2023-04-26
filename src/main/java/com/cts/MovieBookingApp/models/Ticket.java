package com.cts.MovieBookingApp.models;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Ticket {
	
	@Id
	private int ticketId;
	

	private String movieName;
	private String theatreName;
	
	private int noOfTickets;
	private List<String> seats;
	
	public Ticket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ticket(int ticketId, String movieName, String theatreName, int noOfTickets, List<String> seats) {
		super();
		this.ticketId = ticketId;
		this.movieName = movieName;
		this.theatreName = theatreName;
		this.noOfTickets = noOfTickets;
		this.seats = seats;
	}

	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getTheatreName() {
		return theatreName;
	}

	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}

	public int getNoOfTickets() {
		return noOfTickets;
	}

	public void setNoOfTickets(int noOfTickets) {
		this.noOfTickets = noOfTickets;
	}

	public List<String> getSeats() {
		return seats;
	}

	public void setSeats(List<String> seats) {
		this.seats = seats;
	}
	
	
	
	

}
