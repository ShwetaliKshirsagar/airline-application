package com.airline.infyAirline.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airline.infyAirline.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

}
