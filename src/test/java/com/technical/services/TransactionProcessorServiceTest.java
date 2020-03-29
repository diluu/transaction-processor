package com.technical.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.technicaltest.objects.Transaction;
import com.technicaltest.repositories.ClientRepository;
import com.technicaltest.services.TransactionProcessorService;
import com.technicaltest.services.TransactionProcessorServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TransactionProcessorServiceImpl.class, ClientRepository.class})
public class TransactionProcessorServiceTest {
	
	@Autowired
	private TransactionProcessorService transactionProcessorService;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Test
	public void testProcessInputFile() throws IOException {
		InputStream testInput = getClass().getClassLoader().getResourceAsStream("TestInput.txt");
		byte[] inputBytes = new byte[testInput.available()];
		testInput.read(inputBytes);
		List<Transaction> transactions = transactionProcessorService.processInputFile(inputBytes);
	}

}
