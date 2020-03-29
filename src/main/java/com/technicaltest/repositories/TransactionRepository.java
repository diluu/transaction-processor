package com.technicaltest.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.technicaltest.objects.Transaction;

public interface TransactionRepository  extends CrudRepository<Transaction, Long>{
	
	/**
	 * 
	 * Finds transactions by transactionDate
	 * @param transactionDate a LocalDate object as the query parameter
	 * @return a list of Transactions
	 */
	List<Transaction> findByTransactionDate(LocalDate transactionDate);

}
