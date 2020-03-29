package com.technicaltest.services;

import java.io.IOException;

public interface TransactionProcessorService {

	/**
	 * 
	 * Read the input file line by line and add records to the database
	 * 
	 * @param inputFile input text file
	 * @throws IOException
	 */
	void processInputFile(byte[] inputFile) throws IOException;

}
