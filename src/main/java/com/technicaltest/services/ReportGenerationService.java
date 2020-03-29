package com.technicaltest.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public interface ReportGenerationService {
	
	/**
	 * 
	 * Generate Daily Summary Report as a CSV file
	 * @param writer PrintWriter of the HttpServeltResponse
	 * @param date the LocalDate to which the report needs to be generated
	 * @throws IOException
	 */
	void generateDailySummaryReport(PrintWriter writer, LocalDate date) throws IOException;

}
