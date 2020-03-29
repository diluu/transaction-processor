package com.technicaltest.services;

import java.io.IOException;
import java.util.List;

import com.technicaltest.objects.Transaction;

public interface TransactionProcessorService {
	
	/**
	 * 
	 * Read the input file line by line and add records to the database
	 * @param inputFile input text file
	 * @return
	 * @throws IOException
	 */
	List<Transaction> processInputFile(byte[] inputFile) throws IOException;

}
