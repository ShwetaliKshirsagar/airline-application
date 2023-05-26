package com.airline.infyAirline.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.airline.infyAirline.DTO.UserDTO;
import com.airline.infyAirline.entity.User;
import com.airline.infyAirline.exception.InfyAirlineException;
import com.airline.infyAirline.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private Environment environment;

	@Override
	public UserDTO authenticateUser(String email, String password) throws InfyAirlineException {
		// TODO Auto-generated method stub
		Optional<User> findById = userRepository.findById(email);
		User user = findById.get();
		if (findById.isEmpty() | !password.equals(user.getPassword())) {
			throw new InfyAirlineException(environment.getProperty("Service.Incorrect_Credentials"));
		}
		UserDTO userdto = new UserDTO();
		userdto.setEmail(user.getEmail());
		userdto.setMobileNo(user.getMobileNo());
		userdto.setUserName(user.getUserName());
		userdto.setDateOfBirth(user.getDateOfBirth());

		return userdto;
	}

	@Override
	public String registerUser(UserDTO userDTO) throws InfyAirlineException {
		// TODO Auto-generated method stub
		String regesteredEmailId = null;

		Optional<User> findById = userRepository.findById(userDTO.getEmail().toLowerCase());
		List<User> findByMobileNo = userRepository.findByMobileNo(userDTO.getMobileNo());
		if (findById.isEmpty()){
			if (findByMobileNo.isEmpty()) {
				User user = new User();
				user.setEmail(userDTO.getEmail());
				user.setMobileNo(userDTO.getMobileNo());
				user.setPassword(userDTO.getPassword());
				user.setUserName(userDTO.getUserName());
				user.setDateOfBirth(userDTO.getDateOfBirth());
				userRepository.save(user);
				regesteredEmailId = userDTO.getEmail();
			} else {
				throw new InfyAirlineException(environment.getProperty("Service.MobileNo_Already_Exists"));
			}
		} else {
			throw new InfyAirlineException(environment.getProperty("Service.Emailid_Already_Exists"));
		}

		return regesteredEmailId;
	}

	
	

}
