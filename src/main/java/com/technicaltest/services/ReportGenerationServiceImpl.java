package com.technicaltest.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technicaltest.objects.ClientAccount;
import com.technicaltest.objects.Product;
import com.technicaltest.objects.Transaction;
import com.technicaltest.repositories.TransactionRepository;

@Service
public class ReportGenerationServiceImpl implements ReportGenerationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportGenerationServiceImpl.class);
	private static final String[] CSV_HEADERS = { "Client_Information", "Product_information",
			"Total_Transaction_Amount" };

	@Autowired
	private TransactionRepository transactionRepository;

	/**
	 * 
	 * Generate Daily Summary Report as a CSV file
	 * 
	 * @param writer PrintWriter of the HttpServeltResponse
	 * @param date   the LocalDate to which the report needs to be generated
	 * @throws IOException
	 */
	@Override
	public void generateDailySummaryReport(PrintWriter writer, LocalDate date) throws IOException {
		//Find all transactions for the given date
		List<Transaction> transactions = transactionRepository.findByTransactionDate(date);

		//Group them by product and clientAccount and get the sum of transctionAmount
		Map<Product, Map<ClientAccount, Long>> summaryMap = transactions.stream().collect(
				Collectors.groupingBy(Transaction::getProduct, Collectors.groupingBy(Transaction::getClientAccount,
						Collectors.summingLong(Transaction::getTransactionAmount))));

		// Write to the PrintWriter using CSVPrinter
		try (CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(CSV_HEADERS))) {
			summaryMap.forEach((product, clients) -> {
				clients.forEach((client, amount) -> {
					try {
						printer.printRecord(client, product, amount);
					} catch (IOException e) {
						LOGGER.warn("Error writing record", e);
					}
				});
			});
		}

	}

}
