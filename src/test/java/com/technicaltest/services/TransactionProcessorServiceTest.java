package com.technicaltest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.technicaltest.objects.Client;
import com.technicaltest.objects.ClientAccount;
import com.technicaltest.objects.Product;
import com.technicaltest.objects.Transaction;
import com.technicaltest.repositories.ClientAccountRepository;
import com.technicaltest.repositories.ClientRepository;
import com.technicaltest.repositories.ProductRepository;
import com.technicaltest.repositories.TransactionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionProcessorServiceTest {

	@Autowired
	private TransactionProcessorService transactionProcessorService;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientAccountRepository clientAccountRepository;

	@Autowired
	private ProductRepository productRepository;

	/**
	 * 
	 * This test tests the processInputFile() method in TransactionProcessorService
	 * It checks the fields are correctly parsed from the text file and correct
	 * number of objects saved to the database
	 * 
	 * @throws IOException
	 */
	@Test
	public void testProcessInputFile() throws IOException {

		try (InputStream testInput = getClass().getClassLoader().getResourceAsStream("TestInput.txt")) {
			byte[] inputBytes = new byte[testInput.available()];
			testInput.read(inputBytes);

			transactionProcessorService.processInputFile(inputBytes);

			Iterable<Transaction> itrTransactions = transactionRepository.findAll();
			List<Transaction> transactions = new ArrayList<>();
			itrTransactions.forEach(transactions::add);
			assertEquals(3, transactions.size(), "There are 3 transactions in the file");
			LocalDate transactionDate = LocalDate.of(2010, 8, 20);
			assertEquals(transactionDate, transactions.get(0).getTransactionDate(),
					"Transaction date should be 2018/08/20");
			assertEquals(7L, transactions.get(1).getTransactionAmount(),
					"Transaction amount is 7 for this transaction");

			Iterable<Client> itrClients = clientRepository.findAll();
			List<Client> clients = new ArrayList<>();
			itrClients.forEach(clients::add);
			assertEquals(2, clients.size(), "There are 2 clients in the file");
			assertEquals("CL", clients.get(0).getClientType(), "Client type is 'CL'");
			assertEquals("1234", clients.get(1).getClientNumber(), "Client number is 1234");

			Iterable<ClientAccount> itrClientAccounts = clientAccountRepository.findAll();
			List<ClientAccount> clientAccounts = new ArrayList<>();
			itrClientAccounts.forEach(clientAccounts::add);
			assertEquals(3, clientAccounts.size(), "There are 3 client accounts in the file");
			assertEquals("0002", clientAccounts.get(0).getAccountNumber(), "Account number is 0002");
			assertEquals("0001", clientAccounts.get(1).getSubAccountNumber(), "Sub Account number is 0001");

			Iterable<Product> itrProducts = productRepository.findAll();
			List<Product> products = new ArrayList<>();
			itrProducts.forEach(products::add);
			assertEquals(3, products.size(), "There are 3 products in the file");
			assertEquals("SGX", products.get(0).getExchangeCode(), "Exchange code is SGX");
			assertEquals("FU", products.get(0).getProductGroupCode(), "Product group code is FU");
			LocalDate expirationDate = LocalDate.of(2010, 9, 20);
			assertEquals(expirationDate, products.get(1).getExpirationDate(), "Expiration date is 2010/09/20");
			assertEquals("N1", products.get(2).getSymbol(), "Symbol is N1");
		}

	}
	
	@After
	public void cleanUp() {
		transactionRepository.deleteAll();
		productRepository.deleteAll();
		clientAccountRepository.deleteAll();
		clientRepository.deleteAll();
	}


}
