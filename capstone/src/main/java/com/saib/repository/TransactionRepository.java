package com.saib.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.saib.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
	
	List<Transaction> findByTransactionType(String transactionType);
}
