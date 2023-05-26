package com.airline.infyAirline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airline.infyAirline.entity.Booking;
import com.airline.infyAirline.exception.InfyAirlineException;
import com.airline.infyAirline.DTO.TicketType;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
	List<Booking> findByEmail(String email) throws InfyAirlineException;
	boolean existsByTicketTypeAndSeatNo(TicketType ticketType,String seatNumber) throws InfyAirlineException;
}
