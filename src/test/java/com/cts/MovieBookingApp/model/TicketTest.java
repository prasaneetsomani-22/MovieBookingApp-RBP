package com.cts.MovieBookingApp.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.MovieBookingApp.models.Ticket;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TicketTest {
	
	
	
	
	@Test
	public void testTicket() {
		Ticket ticket = new Ticket();
		assertThat(ticket).isNotNull();
	}
	
	@Test
	public void testGettersAndSetters() {
		List<String> seatsList = new ArrayList<>();
		seatsList.add("h1");
		seatsList.add("h2");
		seatsList.add("h3");
		
		Ticket ticket = new Ticket();
		ticket.setTicketId(101);
		ticket.setMovieName("Pathan");
		ticket.setTheatreName("PVR");
		ticket.setNoOfTickets(3);
		ticket.setSeats(seatsList);
		
		assertEquals(ticket.getMovieName(), "Pathan");
		assertEquals(ticket.getSeats(), seatsList);
		assertEquals(ticket.getNoOfTickets(),3);
		assertEquals(ticket.getTheatreName(), "PVR");
		assertEquals(ticket.getTicketId(), 101);
		
	}
	
	@Test
	public void testConstructors() {
		List<String> seatsList = new ArrayList<>();
		seatsList.add("h1");
		seatsList.add("h2");
		seatsList.add("h2");
		
		Ticket ticket = new Ticket(101,"Pathan","PVR",3,seatsList);
		
		assertEquals(ticket.getMovieName(), "Pathan");
		assertEquals(ticket.getSeats(), seatsList);
		assertEquals(ticket.getNoOfTickets(),3);
		assertEquals(ticket.getTheatreName(), "PVR");
		assertEquals(ticket.getTicketId(), 101);
	}

}
