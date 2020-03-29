package com.technicaltest.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.technicaltest.services.ReportGenerationService;
import com.technicaltest.services.TransactionProcessorService;

@Controller
public class TransactionProcessController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionProcessController.class);

	@Autowired
	private TransactionProcessorService transactionService;

	@Autowired
	private ReportGenerationService reportGenerationService;

	@RequestMapping("/transactions")
	public ModelAndView showTransactions() {
		LOGGER.debug("in request transactions()");
		return new ModelAndView("transactions");
	}

	/**
	 * 
	 * Request method handling upload of input text file
	 * 
	 * @param file inputfile
	 */
	@PostMapping("/upload")
	public ModelAndView uploadFile(@RequestParam("file") MultipartFile file) {
		LOGGER.debug("in post request uploadFile()");
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				transactionService.processInputFile(bytes);
			} catch (IOException e) {
				LOGGER.error("Error processing file", e);
			}
		}
		return new ModelAndView("transactions");

	}

	/**
	 * 
	 * Request method handling download of summary report csv file
	 * 
	 * @param date     the date to which the report needs to be generated
	 * @param response HTTPSeervletResponse to write the file to
	 */
	@PostMapping("/download/Output.csv")
	public void downloadDailySummaryReport(@RequestParam("date") String date, HttpServletResponse response) {
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; file=Output.csv");

		if (!date.isEmpty()) {
			LocalDate selectedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
			try {
				reportGenerationService.generateDailySummaryReport(response.getWriter(), selectedDate);
			} catch (IOException e) {
				LOGGER.error("Error processing output file", e);
			} 
		}
	}

}
