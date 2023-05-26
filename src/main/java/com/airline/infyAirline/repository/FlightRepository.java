package com.airline.infyAirline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airline.infyAirline.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
	List<Flight> findByFromLocationAndToLocation(String fromLocation, String toLocation);
}
