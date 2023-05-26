package com.airline.infyAirline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airline.infyAirline.entity.User;
import com.airline.infyAirline.exception.InfyAirlineException;

public interface UserRepository extends JpaRepository<User, String>{
	List<User> findByMobileNo(long mobileNo) throws InfyAirlineException;
}
