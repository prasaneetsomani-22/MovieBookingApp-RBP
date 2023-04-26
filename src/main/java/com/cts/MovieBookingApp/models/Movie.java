package com.cts.MovieBookingApp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Movie {
	
	public static class MovieKey{
		private String movieName;
		private String theatreName;
		
		public MovieKey() {
			super();
			// TODO Auto-generated constructor stub
		}

		public MovieKey(String movieName, String theatreName) {
			super();
			this.movieName = movieName;
			this.theatreName = theatreName;
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
	}
	
	@Id
	private MovieKey id;
	private int allotedSeats;
	public Movie() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Movie(MovieKey id, int allotedSeats) {
		super();
		this.id = id;
		this.allotedSeats = allotedSeats;
	}
	
	public MovieKey getId() {
		return id;
	}
	public void setId(MovieKey id) {
		this.id = id;
	}
	public int getAllotedSeats() {
		return allotedSeats;
	}
	public void setAllotedSeats(int allotedSeats) {
		this.allotedSeats = allotedSeats;
	}
	
	

}
