package com.airline.infyAirline.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airline.infyAirline.DTO.FlightDTO;
import com.airline.infyAirline.exception.InfyAirlineException;
import com.airline.infyAirline.service.FlightServiceImpl;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/flight-api")
public class FlightAPI {

	@Autowired
	private FlightServiceImpl flightService;
	@Autowired
	private Environment environment;
	
	@GetMapping("/search/{fromLocation}/{toLocation}")
	public ResponseEntity<List<FlightDTO>> searchFlights(@PathVariable @NotNull (message="{enter.fromLocation}") String fromLocation,
			@PathVariable @NotNull (message="{enter.toLocation}")String toLocation) throws InfyAirlineException{
		List<FlightDTO> searchFlights = flightService.searchFlights(fromLocation, toLocation);
		return new ResponseEntity<>(searchFlights,HttpStatus.OK);
	}
	@GetMapping("/all")
	public ResponseEntity<List<FlightDTO>> getAllFlightDetails() throws InfyAirlineException{
		List<FlightDTO> allFlightDetails = flightService.getAllFlightDetails();
		return new ResponseEntity<>(allFlightDetails,HttpStatus.OK);
		
	}
	@PutMapping("/noOfSeats/{flightNo}")
	public ResponseEntity<String> reduceNoOfSeatsAvailable(@PathVariable Integer flightNo, @RequestBody String noOfpassengers) throws InfyAirlineException {
		flightService.reduceNoOfSeatsAvailable(flightNo, Integer.parseInt(noOfpassengers));
		return new ResponseEntity<>(environment.getProperty("FlightAPI.REDUCE_SEATS_SUCCESSFULL"),HttpStatus.OK);
	}
	

}
