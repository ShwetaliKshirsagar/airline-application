package com.airline.infyAirline.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.airline.infyAirline.DTO.FlightDTO;
import com.airline.infyAirline.exception.InfyAirlineException;

@Service
public interface FlightService {
	
	List<FlightDTO> searchFlights(String fromLocation, String toLocation) throws InfyAirlineException;
	List<FlightDTO> getAllFlightDetails() throws InfyAirlineException;
	void reduceNoOfSeatsAvailable(Integer flightNo, Integer noOfpassengers)throws InfyAirlineException;
	void increaseNoOfSeatsAvailable(Integer flightNo, Integer noOfpassengers) throws InfyAirlineException;
}
