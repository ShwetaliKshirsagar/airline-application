package com.airline.infyAirline.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.airline.infyAirline.DTO.BookingDTO;

import com.airline.infyAirline.exception.InfyAirlineException;
import com.airline.infyAirline.service.BookingServiceImpl;

@RestController
@RequestMapping(value="/booking-api")
public class BookingAPI {
	
	@Autowired
	private BookingServiceImpl bookingServiceImpl;
//	@Autowired
//	private RestTemplate template;
	@Autowired
	private Environment environment;
	@PostMapping("/book-flight")
	public ResponseEntity<String> flightBooking(@RequestBody BookingDTO bookingDTO)throws InfyAirlineException{
//		FlightDTO flightDTO = template.getForEntity("http://localhost:3600/Infyairlines/flight-api/search/"+bookingDTO.getFlight().getFromLocation()+"/"+bookingDTO.getFlight().getToLocation(), FlightDTO.class).getBody();
		bookingServiceImpl.flightBooking(bookingDTO);
		String property = environment.getProperty("BookingAPI.Flight_Booked_Successfully")+bookingDTO.getBookingNo();
		return new ResponseEntity<>(property,HttpStatus.OK);
		
		}
	@GetMapping("/booking-details/{bookingNo}")
	public ResponseEntity<BookingDTO> getBookingDetails(@PathVariable Integer bookingNo) throws InfyAirlineException{
		BookingDTO bookingDetails = bookingServiceImpl.getBookingDetails(bookingNo);
		return new ResponseEntity<>(bookingDetails,HttpStatus.OK);
	}
	
	@DeleteMapping("/booking")
	public ResponseEntity<String> cancelBooking(@RequestBody BookingDTO bookingDTO) throws InfyAirlineException{
		bookingServiceImpl.cancelBooking(bookingDTO);
		String property = environment.getProperty("BookingAPI.Cancel_Booking");
		return new ResponseEntity<>(property,HttpStatus.OK);
	}

}
