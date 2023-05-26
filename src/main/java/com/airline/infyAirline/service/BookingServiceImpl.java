package com.airline.infyAirline.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.airline.infyAirline.DTO.BookingDTO;
import com.airline.infyAirline.DTO.PaymentThrough;
import com.airline.infyAirline.entity.Booking;
import com.airline.infyAirline.entity.Flight;
import com.airline.infyAirline.entity.User;
import com.airline.infyAirline.exception.InfyAirlineException;
import com.airline.infyAirline.repository.BookingRepository;
import com.airline.infyAirline.repository.UserRepository;

@Service
public class BookingServiceImpl implements BookingService {
	@Autowired  
	private BookingRepository bookingRepository;
	@Autowired
	private Environment env;
	@Autowired
	private FlightServiceImpl flightService;

	//TO book the flight
	
	@Override
	public void flightBooking(BookingDTO bookingDTO) throws InfyAirlineException {
		// TODO Auto-generated method stub
		
		Booking booking= new Booking();
		booking.setDepartureDate(bookingDTO.getDepartureDate());
		booking.setEmail(bookingDTO.getEmail());
		booking.setPassangerName(bookingDTO.getPassangerName());
		booking.setReturnDate(bookingDTO.getReturnDate());
		booking.setPaymentThrough(PaymentThrough.valueOf(bookingDTO.getPaymentThrough()));
		booking.setTicketType(bookingDTO.getTicketType());
		booking.setFlight(bookingDTO.getFlight());
		booking.setNoOfPassengers(bookingDTO.getNoOfPassengers());
//		booking.setStatus("CONFIRMED");
		booking.setTicketType(bookingDTO.getTicketType());

		switch(bookingDTO.getTicketType()) {
		case BUSINESS:
			for(int row=1;row<=10;row++) {
				for(char seat='A';seat<='F';seat++) {
					String seatNumber= String.valueOf(seat)+row;
					if(!bookingRepository.existsByTicketTypeAndSeatNo(bookingDTO.getTicketType(), seatNumber)) {
						booking.setSeatNo(seatNumber);
						bookingRepository.save(booking);
					}
				}	
			}
			break;
		case ECONOMY:
			for(int row=11;row<=30;row++) {
				for(char seat='A';seat<='F';seat++) {
					String seatNumber= String.valueOf(seat)+ row;
					if(!bookingRepository.existsByTicketTypeAndSeatNo(bookingDTO.getTicketType(), seatNumber)) {
						booking.setSeatNo(seatNumber);
						bookingRepository.save(booking);
					}
				}	
			}
			break;
		default:
			throw new InfyAirlineException(env.getProperty("BookingSERVICE.TicketType_Invalid"));

		}
		//To calculate total ticket price
		double totalPrice=0.0;
		totalPrice=(bookingDTO.getFlight().getPrice())* bookingDTO.getNoOfPassengers();
		booking.setPrice(totalPrice);
		//To reduce no of seats in flight
		flightService.reduceNoOfSeatsAvailable(bookingDTO.getFlight().getFlightNo(), bookingDTO.getNoOfPassengers());
		bookingRepository.save(booking);

	}

	//To get booking details by booking No

	@Override
	public BookingDTO getBookingDetails(Integer bookingNo) throws InfyAirlineException {
		// TODO Auto-generated method stub
		Optional<Booking> findById = bookingRepository.findById(bookingNo);
		findById.orElseThrow(()->new InfyAirlineException(env.getProperty("BookingSERVICE.Incorrect_BookingNo")));
		Booking booking = findById.get();
		BookingDTO bookingDTO= new BookingDTO();
		bookingDTO.setBookingNo(booking.getBookingNo());
		bookingDTO.setDepartureDate(booking.getDepartureDate());
		bookingDTO.setEmail(booking.getEmail());
		bookingDTO.setFlight(booking.getFlight());
		bookingDTO.setPassangerName(booking.getPassangerName());
		bookingDTO.setReturnDate(booking.getReturnDate());
		bookingDTO.getSeatNo();
		return bookingDTO ;
	}

	//To cancel booking

	@Override
	public void cancelBooking(BookingDTO bookingDTO) throws InfyAirlineException {
		// TODO Auto-generated method stub
		Optional<Booking> findById = bookingRepository.findById(bookingDTO.getBookingNo());
		findById.orElseThrow(()-> new InfyAirlineException("BookingSERVICE.Incorrect_BookingNo"));
		bookingRepository.deleteById(bookingDTO.getBookingNo());
		Booking booking = findById.get();
//		booking.setStatus("CANCELLED");
		flightService.increaseNoOfSeatsAvailable(bookingDTO.getFlight().getFlightNo(), bookingDTO.getNoOfPassengers());
		bookingRepository.save(booking);

	}

	//To find customer booking details with email id

	@Override
	public List<BookingDTO> findBookingsByCustomerEmailId(String emailId) throws InfyAirlineException {
		// TODO Auto-generated method stub
		List<Booking> findByEmail = bookingRepository.findByEmail(emailId);
		if(findByEmail.isEmpty()) {
			throw new InfyAirlineException(env.getProperty("BookingSERVICE.NO_BOOKING_FOUND"));
		}
		List<BookingDTO> dtoList= new ArrayList<>();
		for(Booking list:findByEmail) {
			BookingDTO bookingDTO= new BookingDTO();
			bookingDTO.setBookingNo(list.getBookingNo());
			bookingDTO.setDepartureDate(list.getDepartureDate());
			bookingDTO.setReturnDate(list.getReturnDate());
			bookingDTO.setPassangerName(list.getPassangerName());
			bookingDTO.setPrice(list.getPrice());
			bookingDTO.setTicketType(list.getTicketType());
			bookingDTO.setSeatNo(null);
			bookingDTO.setNoOfPassengers(list.getNoOfPassengers());
			dtoList.add(bookingDTO);

		}

		return dtoList;
	}

	
	


}
