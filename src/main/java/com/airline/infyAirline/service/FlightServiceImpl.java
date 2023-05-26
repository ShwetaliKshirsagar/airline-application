package com.airline.infyAirline.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.airline.infyAirline.DTO.FlightDTO;
import com.airline.infyAirline.entity.Flight;
import com.airline.infyAirline.exception.InfyAirlineException;
import com.airline.infyAirline.repository.FlightRepository;

@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
	private FlightRepository flightRepository;
	@Autowired
	private Environment environment;
	@Override
	public List<FlightDTO> searchFlights(String fromLocation, String toLocation) throws InfyAirlineException {
		// TODO Auto-generated method stub
		List<Flight> fromLocationAndToLocation = flightRepository.findByFromLocationAndToLocation( fromLocation,  toLocation);
		if(fromLocationAndToLocation.isEmpty()) {
			throw new InfyAirlineException(environment.getProperty("Service.NO_FLIGHTS_AVAILABLE"));
		}
		List<FlightDTO> flightDtoList= new ArrayList<>();
		for(Flight list: fromLocationAndToLocation) {
			FlightDTO flightDTO= new FlightDTO();
			flightDTO.setArrivalTime(list.getArrivalTime());
			flightDTO.setDepartureTime(list.getDepartureTime());
			flightDTO.setFlightNo(list.getFlightNo());
			flightDTO.setFromLocation(list.getFromLocation());
			flightDTO.setToLocation(list.getToLocation());
			flightDTO.setPrice(list.getPrice());
			flightDtoList.add(flightDTO);
		}
		return flightDtoList;
	}
	@Override
	public List<FlightDTO> getAllFlightDetails() throws InfyAirlineException {
		// TODO Auto-generated method stub
		List<Flight> findAll = flightRepository.findAll();
		if(findAll.isEmpty()) {
			throw new InfyAirlineException(environment.getProperty("FlightService.NO_FLIGHTS_AVAILABLE"));
		}
		List<FlightDTO> flightDtoList= new ArrayList<>();
		for(Flight list: findAll) {
			FlightDTO flightDTO= new FlightDTO();
			flightDTO.setArrivalTime(list.getArrivalTime());
			flightDTO.setDepartureTime(list.getDepartureTime());
			flightDTO.setFlightNo(list.getFlightNo());
			flightDTO.setFromLocation(list.getFromLocation());
			flightDTO.setToLocation(list.getToLocation());
			flightDTO.setPrice(list.getPrice());
			flightDtoList.add(flightDTO);
		}
		return flightDtoList;
	}
	@Override
	public void reduceNoOfSeatsAvailable(Integer flightNo, Integer noOfpassengers) throws InfyAirlineException {
		// TODO Auto-generated method stub
		Optional<Flight> flightOpt = flightRepository.findById(flightNo);
        Flight flight = flightOpt.orElseThrow(()->new InfyAirlineException(environment.getProperty("BookingService.BOOKING_NOT_AVAILABLE")));
        flight.setAvailableSeats(flight.getAvailableSeats()-noOfpassengers);
        flightRepository.save(flight);
	}
	
	@Override
	public void increaseNoOfSeatsAvailable(Integer flightNo, Integer noOfpassengers) throws InfyAirlineException {
		// TODO Auto-generated method stub
		Optional<Flight> flightOpt = flightRepository.findById(flightNo);
        Flight flight = flightOpt.orElseThrow(()->new InfyAirlineException(environment.getProperty("BookingService.BOOKING_NOT_AVAILABLE")));
        flight.setAvailableSeats(flight.getAvailableSeats()+noOfpassengers);
        flightRepository.save(flight);
	}


}
