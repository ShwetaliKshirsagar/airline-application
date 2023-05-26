package com.airline.infyAirline.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airline.infyAirline.entity.Card;

public interface CardRepository extends JpaRepository<Card, Integer>{

	List<Card> findByCustomerEmailId(String customerEmailId);
	List<Card> findByCustomerEmailIdAndCardType(String customerEmailId,String cardType);
	
}
