package com.airline.infyAirline.DTO;

import java.time.LocalDateTime;

import com.airline.infyAirline.entity.Flight;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;



public class BookingDTO {
	
	private int bookingNo;
	@Pattern(regexp="[A-Za-z+]+[ A-Za-z]*")
	private String passangerName;
	@Email (message="Plese enter valid email Id.")
	@NotNull (message="Plese enter email Id.")
	@NotEmpty (message="Plese enter email Id.")
	private String email;
	@FutureOrPresent(message="Plese enter valid email Id.")
	@NotNull(message="Plese enter departure date.")
	@NotEmpty(message="Plese enter departure date.")
	private LocalDateTime departureDate;
	@Future
	private LocalDateTime returnDate;
	private String seatNo;
	private TicketType ticketType;
	private Flight flight;
	@Pattern(regexp = "(DEBIT_CARD|CREDIT_CARD)", message = "{order.paymentthrough.invalid}")
	private String paymentThrough;
	private double price;
	@NotNull(message="Plese enter number of passengers.")
	@NotEmpty(message="Plese enter number of passengers.")
	private int noOfPassengers;

	public int getNoOfPassengers() {
		return noOfPassengers;
	}
	public void setNoOfPassengers(int noOfPassengers) {
		this.noOfPassengers = noOfPassengers;
	}

	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getPaymentThrough() {
		return paymentThrough;
	}
	public void setPaymentThrough(String paymentThrough) {
		this.paymentThrough = paymentThrough;
	}
	public int getBookingNo() {
		return bookingNo;
	}
	public void setBookingNo(int bookingNo) {
		this.bookingNo = bookingNo;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	public String getPassangerName() {
		return passangerName;
	}
	public void setPassangerName(String passangerName) {
		this.passangerName = passangerName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(LocalDateTime departureDate) {
		this.departureDate = departureDate;
	}
	public LocalDateTime getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(LocalDateTime returnDate) {
		this.returnDate = returnDate;
	}
	public String getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}
	public TicketType getTicketType() {
		return ticketType;
	}
	public void setTicketType(TicketType ticketType) {
		this.ticketType = ticketType;
	}
	
}
