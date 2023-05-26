package com.airline.infyAirline.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.airline.infyAirline.DTO.CardDTO;
import com.airline.infyAirline.DTO.TransactionDTO;
import com.airline.infyAirline.exception.InfyAirlineException;

public interface PaymentService {
	Integer addCustomerCard(String customerEmailId, CardDTO cardDTO) throws InfyAirlineException, NoSuchAlgorithmException;
	void updateCustomerCard(CardDTO cardDTO) throws InfyAirlineException, NoSuchAlgorithmException;
	void deleteCustomerCard(String customerEmailId, Integer cardId) throws InfyAirlineException;
	CardDTO getCard(Integer cardId) throws InfyAirlineException;
	List<CardDTO> getCustomerCardOfCardType(String customerEmailId , String cardType) throws InfyAirlineException;
	Integer addTransaction (TransactionDTO transactionDTO) throws InfyAirlineException ;
	TransactionDTO authenticatePayment(String customerEmailId , TransactionDTO transactionDTO) throws InfyAirlineException , NoSuchAlgorithmException;
	List<CardDTO> getCardsOfCustomer(String customerEmailId, String cardType) throws InfyAirlineException;

}
