package com.airline.infyAirline.DTO;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class FlightDTO {
	@NotNull
	@NotEmpty
	private int flightNo;
	@NotNull
	@NotEmpty
	private String fromLocation;
	@NotNull
	@NotEmpty
	private String toLocation;
	@FutureOrPresent
	private LocalDateTime departureTime;
	@Future
	private LocalDateTime arrivalTime;
//	private int noOfPassengers;
	@NotNull @NotEmpty
	private double price;
	@NotNull @NotEmpty
	private int availableSeats;

	public int getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
	
//	public int getNoOfPassengers() {
//		return noOfPassengers;
//	}
//	public void setNoOfPassengers(int noOfPassengers) {
//		this.noOfPassengers = noOfPassengers;
//	}
	public int getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(int flightNo) {
		this.flightNo = flightNo;
	}
	public String getFromLocation() {
		return fromLocation;
	}
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	public String getToLocation() {
		return toLocation;
	}
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}
	public LocalDateTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}
	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(LocalDateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
