package com.technicaltest.services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technicaltest.objects.Client;
import com.technicaltest.objects.ClientAccount;
import com.technicaltest.objects.Product;
import com.technicaltest.objects.Transaction;
import com.technicaltest.repositories.ClientAccountRepository;
import com.technicaltest.repositories.ClientRepository;
import com.technicaltest.repositories.ProductRepository;
import com.technicaltest.repositories.TransactionRepository;

@Service
public class TransactionProcessorServiceImpl implements TransactionProcessorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProcessorServiceImpl.class);
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientAccountRepository clientAccountRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	/**
	 * Creates a client object using the fields in given line Adds it to the clients
	 * list If the client is already in the list use it
	 * 
	 * @param clients
	 * @param line
	 * @return
	 */
	private Client createClient(List<Client> clients, String line) {
		String clientType = line.substring(3, 7).trim();
		String clientNumber = line.substring(7, 11).trim();

		List<Client> existingClients = clientRepository.findByClientTypeAndClientNumber(clientType, clientNumber);
		Client client = null;
		if (!existingClients.isEmpty()) {
			client = existingClients.get(0);
		} else {
			client = new Client();
			client.setClientType(clientType);
			client.setClientNumber(clientNumber);
			if (clients.contains(client)) {
				client = clients.get(clients.indexOf(client));
			} else {
				clients.add(client);
			}
		}
		return client;
	}

	/**
	 * Creates a ClientAccount object using the fields in given line Adds it to the
	 * clientAccounts list If the clientAccount is already in the list use it
	 * 
	 * @param clientAccounts
	 * @param line
	 * @param client
	 * @return
	 */
	private ClientAccount createClientAccount(List<ClientAccount> clientAccounts, String line, Client client) {
		String accountNumber = line.substring(11, 15).trim();
		String subAccountNumber = line.substring(15, 19).trim();

		List<ClientAccount> existingAccounts = null;
		if (client.getId() != null) {
			existingAccounts = clientAccountRepository.findByClientAndAccountNumberAndSubAccountNumber(client,
					accountNumber, subAccountNumber);
		}
		ClientAccount account = null;
		if (existingAccounts != null && !existingAccounts.isEmpty()) {
			account = clientAccounts.get(0);
		} else {
			account = new ClientAccount();
			account.setClient(client);
			account.setAccountNumber(accountNumber);
			account.setSubAccountNumber(subAccountNumber);
			if (clientAccounts.contains(account)) {
				account = clientAccounts.get(clientAccounts.indexOf(account));
			} else {
				clientAccounts.add(account);
			}
		}
		return account;
	}

	/**
	 * 
	 * Creates a Product object using the fields in given line Adds it to the
	 * products list If the product is already in the list use it
	 * 
	 * @param products
	 * @param line
	 * @return
	 */
	private Product createProduct(List<Product> products, String line) {
		String exchangeCode = line.substring(27, 31).trim();
		String productGroupCode = line.substring(25, 27).trim();
		String symbol = line.substring(31, 37).trim();
		LocalDate expirationDate = LocalDate.parse(line.substring(37, 45), FORMATTER);

		List<Product> existingProducts = productRepository
				.findByExchangeCodeAndExpirationDateAndProductGroupCodeAndSymbol(exchangeCode, expirationDate,
						productGroupCode, symbol);
		Product product = null;
		if (!existingProducts.isEmpty()) {
			product = existingProducts.get(0);
		} else {
			product = new Product();
			product.setExchangeCode(exchangeCode);
			product.setExpirationDate(expirationDate);
			product.setProductGroupCode(productGroupCode);
			product.setSymbol(symbol);
			if (products.contains(product)) {
				product = products.get(products.indexOf(product));
			} else {
				products.add(product);
			}
		}
		return product;
	}

	/**
	 * Create a transaction using the fields in the given line
	 * 
	 * @param transactions
	 * @param line
	 * @param client
	 * @param account
	 * @param product
	 */
	private void createTransaction(List<Transaction> transactions, String line, Client client, ClientAccount account,
			Product product) {
		long longQuantity = Long.valueOf(line.substring(52, 62));
		long shortQuantity = Long.valueOf(line.substring(63, 73));
		LocalDate transactionDate = LocalDate.parse(line.substring(121, 129), FORMATTER);

		Transaction transaction = new Transaction();
		transaction.setClient(client);
		transaction.setClientAccount(account);
		transaction.setProduct(product);
		transaction.setTransactionAmount(longQuantity - shortQuantity);
		transaction.setTransactionDate(transactionDate);
		transactions.add(transaction);
	}

	/**
	 * 
	 * Read the input file line by line and add records to the database
	 * 
	 * @param inputFile input text file
	 * @throws IOException
	 */
	public void processInputFile(byte[] inputFile) throws IOException {
		LOGGER.debug("in method processInputFile()");

		// Keep clients, clientAccounts, products and transactions in memory and save as
		// batches
		List<Client> clients = new ArrayList<>();
		List<ClientAccount> clientAccounts = new ArrayList<>();
		List<Product> products = new ArrayList<>();
		List<Transaction> transactions = new ArrayList<>();

		String line = null;
		try (InputStream is = new ByteArrayInputStream(inputFile);
				BufferedReader bf = new BufferedReader(new InputStreamReader(is))) {
			while ((line = bf.readLine()) != null) {

				Client client = createClient(clients, line);
				ClientAccount account = createClientAccount(clientAccounts, line, client);
				Product product = createProduct(products, line);
				createTransaction(transactions, line, client, account, product);

			}
			clientRepository.saveAll(clients);
			clientAccountRepository.saveAll(clientAccounts);
			productRepository.saveAll(products);
			transactionRepository.saveAll(transactions);
		}
	}

}
