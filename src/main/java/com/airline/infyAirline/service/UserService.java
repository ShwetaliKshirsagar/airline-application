package com.airline.infyAirline.service;

import com.airline.infyAirline.DTO.UserDTO;
import com.airline.infyAirline.exception.InfyAirlineException;

public interface UserService {
	public UserDTO authenticateUser(String email,String password ) throws InfyAirlineException;
	public String registerUser(UserDTO userDTO) throws InfyAirlineException;
}
