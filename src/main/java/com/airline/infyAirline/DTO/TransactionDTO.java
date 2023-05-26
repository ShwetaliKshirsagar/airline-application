package com.airline.infyAirline.DTO;

import java.time.LocalDateTime;

import jakarta.validation.Valid;

public class TransactionDTO {
	@Override
	public String toString() {
		return "TransactionDTO [transactionId=" + transactionId + ", order=" + booking + ", card=" + card
				+ ", totalPrice=" + totalPrice + ", transactionDate=" + transactionDate + ", transactionStatus="
				+ transactionStatus + "]";
	}
	private Integer transactionId;
	@Valid
	private BookingDTO booking;
	@Valid
	private CardDTO card;
	private Double totalPrice;
	private LocalDateTime transactionDate;
	private TransactionStatus transactionStatus;
	
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}
	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public BookingDTO getBooking() {
		return booking;
	}
	public void setBooking(BookingDTO booking) {
		this.booking = booking;
	}
	public CardDTO getCard() {
		return card;
	}
	public void setCard(CardDTO card) {
		this.card = card;
	}
	
}
