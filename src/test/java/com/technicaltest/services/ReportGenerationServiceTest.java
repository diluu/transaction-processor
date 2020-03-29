package com.technicaltest.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Before;
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
import com.technicaltest.services.ReportGenerationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportGenerationServiceTest {

	@Autowired
	private ReportGenerationService reportGenerationService;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientAccountRepository clientAccountRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Before
	public void setUp() {
		Client client1 = new Client();
		client1.setClientType("CL");
		client1.setClientNumber("1234");
		client1 = clientRepository.save(client1);

		ClientAccount account1 = new ClientAccount();
		account1.setAccountNumber("0002");
		account1.setSubAccountNumber("0001");
		account1.setClient(client1);
		account1 = clientAccountRepository.save(account1);

		Client client2 = new Client();
		client2.setClientType("CL");
		client2.setClientNumber("4321");
		client2 = clientRepository.save(client2);

		ClientAccount account2 = new ClientAccount();
		account2.setAccountNumber("0003");
		account2.setSubAccountNumber("0001");
		account2.setClient(client2);
		account2 = clientAccountRepository.save(account2);

		Product product = new Product();
		product.setExchangeCode("SGX");
		product.setProductGroupCode("FU");
		product.setSymbol("NK");
		LocalDate expirationDate = LocalDate.of(2010, 9, 10);
		product.setExpirationDate(expirationDate);
		product = productRepository.save(product);

		LocalDate transactionDate = LocalDate.of(2010, 8, 20);
		Transaction transaction1 = new Transaction();
		transaction1.setTransactionAmount(5L);
		transaction1.setTransactionDate(transactionDate);
		transaction1.setClient(client1);
		transaction1.setClientAccount(account1);
		transaction1.setProduct(product);
		transactionRepository.save(transaction1);

		Transaction transaction2 = new Transaction();
		transaction2.setTransactionAmount(7L);
		transaction2.setTransactionDate(transactionDate);
		transaction2.setClient(client2);
		transaction2.setClientAccount(account2);
		transaction2.setProduct(product);
		transactionRepository.save(transaction2);

	}

	/**
	 * 
	 * This test tests given the data in setUp() method inserted correct report is
	 * generated
	 * 
	 * @throws IOException
	 */
	@Test
	public void testgenerateDailySummaryReport() throws IOException {
		try (ByteArrayOutputStream stream = new ByteArrayOutputStream();
				InputStream testOutput = getClass().getClassLoader().getResourceAsStream("TestOutput.csv");) {

			PrintWriter writer = new PrintWriter(stream);
			reportGenerationService.generateDailySummaryReport(writer, LocalDate.of(2010, 8, 20));
			byte[] actual = stream.toByteArray();

			byte[] expected = new byte[testOutput.available()];
			testOutput.read(expected);

			assertTrue(Arrays.equals(actual, expected), "TestOutput.csv should be generated");
		}

	}
}
