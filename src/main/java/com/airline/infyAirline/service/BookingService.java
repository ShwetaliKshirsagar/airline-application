package com.airline.infyAirline.service;

import java.util.List;

import com.airline.infyAirline.DTO.BookingDTO;
import com.airline.infyAirline.exception.InfyAirlineException;

public interface BookingService {
	public void flightBooking(BookingDTO bookingDTO)throws InfyAirlineException;
	public BookingDTO getBookingDetails(Integer bookingNo) throws InfyAirlineException;
	public void cancelBooking(BookingDTO bookingDTO) throws InfyAirlineException;
	public List<BookingDTO> findBookingsByCustomerEmailId(String emailId) throws InfyAirlineException;
}
