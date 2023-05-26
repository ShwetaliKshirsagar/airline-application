package com.airline.infyAirline.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.infyAirline.DTO.CardDTO;
import com.airline.infyAirline.DTO.TransactionDTO;
import com.airline.infyAirline.DTO.TransactionStatus;
import com.airline.infyAirline.entity.Card;
import com.airline.infyAirline.entity.Transaction;
import com.airline.infyAirline.entity.User;
import com.airline.infyAirline.exception.InfyAirlineException;
import com.airline.infyAirline.repository.CardRepository;
import com.airline.infyAirline.repository.TransactionRepository;
import com.airline.infyAirline.repository.UserRepository;
import com.airline.infyAirline.utility.HashingUtility;
@Service
public class PaymentServiceImpl implements PaymentService{

	@Autowired
	private CardRepository cardRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TransactionRepository transactionRepository;

	public Integer addCustomerCard(String customerEmailId, CardDTO cardDTO)
			throws InfyAirlineException, NoSuchAlgorithmException {

		List<Card> listOfCustomerCards = cardRepository.findByCustomerEmailId(customerEmailId);
		if (listOfCustomerCards.isEmpty()) {
			//			throw new EKartException("PaymentService.CUSTOMER_NOT_FOUND");
			Optional<User> optional = userRepository.findById(customerEmailId);
			 optional.orElseThrow(()-> new InfyAirlineException("PaymentService.CUSTOMER_NOT_FOUND"));
		
				
			
		}
		
		cardDTO.setHashCvv(HashingUtility.getHashValue(cardDTO.getCvv().toString()));
		Card newCard = new Card();
		newCard.setCardId(cardDTO.getCardId());
		newCard.setNameOnCard(cardDTO.getNameOnCard());
		
		newCard.setCardNumber(cardDTO.getCardNumber());
		newCard.setCardType(cardDTO.getCardType());
		newCard.setExpiryDate(cardDTO.getExpiryDate());
		newCard.setCvv(cardDTO.getHashCvv());
		newCard.setCustomerEmailId(customerEmailId);

		cardRepository.save(newCard);
		return newCard.getCardID();
	}

	@Override
	public void updateCustomerCard(CardDTO cardDTO) throws InfyAirlineException, NoSuchAlgorithmException {

		Optional<Card> optionalCard = cardRepository.findById(cardDTO.getCardId());
		Card card = optionalCard.orElseThrow(() -> new InfyAirlineException("PaymentService.CARD_NOT_FOUND"));
		cardDTO.setHashCvv(HashingUtility.getHashValue(cardDTO.getCvv().toString()));
		card.setCardId(cardDTO.getCardId());
		card.setNameOnCard(cardDTO.getNameOnCard());
		card.setCardNumber(cardDTO.getCardNumber());
		card.setCardType(cardDTO.getCardType());
		card.setCvv(cardDTO.getHashCvv());
		card.setExpiryDate(cardDTO.getExpiryDate());
		card.setCustomerEmailId(cardDTO.getUserEmailId());

	}

	@Override
	public void deleteCustomerCard(String customerEmailId, Integer cardId) throws InfyAirlineException {

		List<Card> listOfCustomerCards = cardRepository.findByCustomerEmailId(customerEmailId);
		if (listOfCustomerCards.isEmpty())
			throw new InfyAirlineException("PaymentService.CUSTOMER_NOT_FOUND");

		Optional<Card> optionalCards = cardRepository.findById(cardId);
		Card card = optionalCards.orElseThrow(() -> new InfyAirlineException("PaymentService.CARD_NOT_FOUND"));
		cardRepository.delete(card);

	}

	@Override
	public CardDTO getCard(Integer cardId) throws InfyAirlineException {

		Optional<Card> optionalCards = cardRepository.findById(cardId);
		Card card = optionalCards.orElseThrow(()->new InfyAirlineException("PaymentService.CARD_NOT_FOUND"));
		
		CardDTO cardDTO = new CardDTO();
		cardDTO.setCardId(card.getCardID());
		cardDTO.setNameOnCard(card.getNameOnCard());
		cardDTO.setCardNumber(card.getCardNumber());
		cardDTO.setCardType(card.getCardType());
		cardDTO.setHashCvv(card.getCvv());
		cardDTO.setExpiryDate(card.getExpiryDate());
		cardDTO.setUserEmailId(card.getCustomerEmailId());
		return cardDTO;
	}

	@Override
	public List<CardDTO> getCustomerCardOfCardType(String customerEmailId, String cardType) throws InfyAirlineException {

		List<Card> cards = cardRepository.findByCustomerEmailIdAndCardType(customerEmailId, cardType);

		if (cards.isEmpty()) {
			throw new InfyAirlineException("PaymentService.CARD_NOT_FOUND");
		}
		List<CardDTO> cardDTOs = new ArrayList<CardDTO>();
		for (Card card : cards) {
			CardDTO cardDTO = new CardDTO();
			cardDTO.setCardId(card.getCardID());
			cardDTO.setNameOnCard(card.getNameOnCard());
			cardDTO.setCardNumber(card.getCardNumber());
			cardDTO.setCardType(card.getCardType());
			cardDTO.setHashCvv("XXX");
			cardDTO.setExpiryDate(card.getExpiryDate());
			cardDTO.setUserEmailId(card.getCustomerEmailId());

			cardDTOs.add(cardDTO);
		}
		return cardDTOs;
	}
	
	
	// Get the list of card details by using the customerEmailId and cardType
    // If the obtained list is empty throw EKartException with message
    // PaymentService.CARD_NOT_FOUND
    // Else populate the obtained card details and return

	@Override
	public List<CardDTO> getCardsOfCustomer(String customerEmailId, String cardType) throws InfyAirlineException {

		// write your logic here
		List<Card> cards = cardRepository.findByCustomerEmailIdAndCardType(customerEmailId, cardType);
		if(cards.isEmpty()) {
			throw new InfyAirlineException("PaymentService.CARD_NOT_FOUND");
		}
		List<CardDTO> list = new ArrayList<>();
		cards.forEach(c->{
			CardDTO cardDTO = new CardDTO();
			cardDTO.setCardId(c.getCardID());
			cardDTO.setCardNumber(c.getCardNumber());
			cardDTO.setCardType(c.getCardType());
			cardDTO.setUserEmailId(c.getCustomerEmailId());
			cardDTO.setHashCvv("XXX");
			cardDTO.setExpiryDate(c.getExpiryDate());
			cardDTO.setNameOnCard(c.getNameOnCard());
			
			list.add(cardDTO);
			
			
		});
			
		return list;
		
	}

	@Override
	public Integer addTransaction(TransactionDTO transactionDTO) throws InfyAirlineException {
		if (transactionDTO.getTransactionStatus().equals(TransactionStatus.TRANSACTION_FAILED)) {
			throw new InfyAirlineException("PaymentService.TRANSACTION_FAILED_CVV_NOT_MATCHING");
		}
		Transaction transaction = new Transaction();
		transaction.setCardId(transactionDTO.getCard().getCardId());

		transaction.setBookingNo(transactionDTO.getBooking().getBookingNo());
		transaction.setTotalPrice(transactionDTO.getTotalPrice());
		transaction.setTransactionDate(transactionDTO.getTransactionDate());
		transaction.setTransactionStatus(transactionDTO.getTransactionStatus());
		transactionRepository.save(transaction);

		return transaction.getTransactionId();
	}

	@Override
	public TransactionDTO authenticatePayment(String customerEmailId, TransactionDTO transactionDTO)
			throws InfyAirlineException, NoSuchAlgorithmException {
		if (!transactionDTO.getBooking().getEmail().equals(customerEmailId)) {
			throw new InfyAirlineException("PaymentService.BOOKING_DOES_NOT_BELONGS");

		}
		CardDTO cardDTO = getCard(transactionDTO.getCard().getCardId());
		if (!cardDTO.getUserEmailId().matches(customerEmailId)) {

			throw new InfyAirlineException("PaymentService.CARD_DOES_NOT_BELONGS");
		}
		if (!cardDTO.getCardType().equals(transactionDTO.getBooking().getPaymentThrough())) {

			throw new InfyAirlineException("PaymentService.PAYMENT_OPTION_SELECTED_NOT_MATCHING_CARD_TYPE");
		}
		if (cardDTO.getHashCvv().equals(HashingUtility.getHashValue(transactionDTO.getCard().getCvv().toString()))) {

			transactionDTO.setTransactionStatus(TransactionStatus.TRANSACTION_SUCCESS);
		} else {

			transactionDTO.setTransactionStatus(TransactionStatus.TRANSACTION_FAILED);

		}

		return transactionDTO;
	}
}
