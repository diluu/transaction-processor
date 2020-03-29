package com.technical.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.technicaltest.objects.Transaction;
import com.technicaltest.repositories.ClientAccountRepository;
import com.technicaltest.repositories.ClientRepository;
import com.technicaltest.repositories.ProductRepository;
import com.technicaltest.repositories.TransactionRepository;
import com.technicaltest.services.TransactionProcessorService;
import com.technicaltest.services.TransactionProcessorServiceImpl;

@RunWith(SpringRunner.class)
public class TransactionProcessorServiceImplTest {
	
	@TestConfiguration
	static class TransactionProcessorServiceImplTestConxtextConfiguration{
		
		@Bean
		public TransactionProcessorService transactionProcessorService() {
			return new TransactionProcessorServiceImpl();
		}
	}

	@Autowired
	private TransactionProcessorService transactionProcessorService;
	
	@MockBean
	private ClientRepository clientRepository;
	
	@MockBean
	private ClientAccountRepository clientAccountRepository;
	
	@MockBean
	private ProductRepository productRepository;
	
	@MockBean
	private TransactionRepository transactionRepository;
	


	@Test
	public void testProcessInputFile() throws IOException {

		InputStream testInput = getClass().getClassLoader().getResourceAsStream("TestInput.txt");
		byte[] inputBytes = new byte[testInput.available()];
		testInput.read(inputBytes);
		List<Transaction> transactions = transactionProcessorService.processInputFile(inputBytes);
		assertEquals(3, transactions.size(), "There are 3 transactions in the input file");
		
	}

}
