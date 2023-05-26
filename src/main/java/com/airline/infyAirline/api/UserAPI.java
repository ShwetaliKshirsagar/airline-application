package com.airline.infyAirline.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airline.infyAirline.DTO.UserDTO;
import com.airline.infyAirline.exception.InfyAirlineException;
import com.airline.infyAirline.service.UserServiceImpl;



@RestController
@RequestMapping("/user")
public class UserAPI {
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private Environment environment;
	
	static Log logger = LogFactory.getLog(UserAPI.class);

	@PostMapping("/login")
	public ResponseEntity<UserDTO> authenticateUser( @RequestBody UserDTO userDTO) throws InfyAirlineException{
		logger.info("USER TRYING TO LOGIN, VALIDATING CREDENTIALS. USER EMAIL ID: " +userDTO.getEmail());
		UserDTO authenticateUser = userService.authenticateUser(userDTO.getEmail(), userDTO.getPassword());
		logger.info("USER LOGIN SUCCESS, USER EMAIL : " + userDTO.getEmail());
		return new ResponseEntity<>(authenticateUser,HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody  UserDTO userDTO) throws InfyAirlineException{
		logger.info("USER TRYING TO REGISTER. USER EMAIL ID: " + userDTO.getEmail());
		String registerUser = userService.registerUser(userDTO);
		String registerSuccess=environment.getProperty("API.Register_Success")+registerUser;
		return new ResponseEntity<>(registerSuccess,HttpStatus.OK);
		
	}
}
