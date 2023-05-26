package com.airline.infyAirline.api;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.airline.infyAirline.DTO.BookingDTO;
import com.airline.infyAirline.DTO.CardDTO;
import com.airline.infyAirline.DTO.TransactionDTO;
import com.airline.infyAirline.exception.InfyAirlineException;
import com.airline.infyAirline.service.PaymentServiceImpl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/payment-api")
public class PaymentAPI {
	@Autowired
	private PaymentServiceImpl paymentServiceImpl;
	@Autowired
	private Environment environment;
	@Autowired
	private RestTemplate template;

	Log logger = LogFactory.getLog(PaymentAPI.class);

	@PostMapping("/addCard/{customerEmailId}")
	public ResponseEntity<String> addCustomerCard(
			@Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-Z]{2,}\\.[a-zA-Z][a-zA-Z.]+", message = "{invalid.email.format}") @PathVariable String customerEmailId,
			@RequestBody CardDTO cardDTO) throws InfyAirlineException, NoSuchAlgorithmException {
		logger.info("Recieved request to add new  card for customer : " + cardDTO.getUserEmailId());
		int cardId;
		cardId = paymentServiceImpl.addCustomerCard(customerEmailId, cardDTO);
		String property = environment.getProperty("PaymentAPI.NEW_CARD_ADDED_SUCCESS") + cardId;
		return new ResponseEntity<>(property, HttpStatus.CREATED);
	}

	@PutMapping("/update/card")
	public ResponseEntity<String> updateCustomerCard(@RequestBody CardDTO cardDTO)
			throws InfyAirlineException, NoSuchAlgorithmException {
		paymentServiceImpl.updateCustomerCard(cardDTO);
		String property = environment.getProperty("PaymentAPI.UPDATE_CARD_SUCCESS");
		return new ResponseEntity<>(property, HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/customer/{customerEmailId:.+}/card/{cardID}/delete")
	public ResponseEntity<String> deleteCustomerCard(@PathVariable("cardID") Integer cardID,
			@Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-Z]{2,}\\.[a-zA-Z][a-zA-Z.]+", message = "{invalid.email.format}") @PathVariable("customerEmailId") String customerEmailId)
					throws InfyAirlineException {
		logger.info("Recieved request to delete  card :" + cardID + " of customer : " + customerEmailId);

		paymentServiceImpl.deleteCustomerCard(customerEmailId, cardID);
		String modificationSuccessMsg = environment.getProperty("PaymentAPI.CUSTOMER_CARD_DELETED_SUCCESS");
		return new ResponseEntity<>(modificationSuccessMsg, HttpStatus.OK);

	}
	@PostMapping(value = "/customer/{customerEmailId}/booking/{bookingNo}")
	public ResponseEntity<String> payForOrder(
			@Pattern(regexp = "[a-zA-Z0-9._]+@[a-zA-Z]{2,}\\.[a-zA-Z][a-zA-Z.]+", message = "{invalid.email.format}")
			@PathVariable("customerEmailId") String customerEmailId,
			@NotNull(message = "{bookingNO.absent") @PathVariable("bookingNo") Integer bookingNo,
			@Valid @RequestBody CardDTO cardDTO) throws NoSuchAlgorithmException, InfyAirlineException {
		
		ResponseEntity<BookingDTO> productResponse = template.getForEntity
				("http://localhost:3600/booking-api/booking/"+bookingNo, BookingDTO.class);
		BookingDTO bookingDTO = productResponse.getBody();
		TransactionDTO tDTO = new TransactionDTO();
		tDTO.setBooking(bookingDTO);
		//List<CardDTO> card = paymentService.getCardsOfCustomer(customerEmailId, orderDTO.getPaymentThrough());
		tDTO.setCard(cardDTO);
		tDTO.setTotalPrice(bookingDTO.getPrice());		
		tDTO.setTransactionDate(LocalDateTime.now());
		//tDTO.setTransactionStatus(TransactionStatus.TRANSACTION_SUCCESS);
		paymentServiceImpl.authenticatePayment(customerEmailId, tDTO);
		int id=paymentServiceImpl.addTransaction(tDTO);
//		template.put("http://localhost:3600/booking-api/booking/"+bookingDTO.getBookingNo()+"/update/booking-status", tDTO);
		String message = environment.getProperty("PaymentAPI.TRANSACTION_SUCCESSFULL_ONE")+bookingDTO.getPrice()+environment.getProperty("PaymentAPI.TRANSACTION_SUCCESSFULL_TWO")+bookingDTO.getBookingNo()+environment.getProperty("PaymentAPI.TRANSACTION_SUCCESSFULL_THREE")+id;
		return new ResponseEntity<String>(message,HttpStatus.OK);

	}
}
