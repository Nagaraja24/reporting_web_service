package com.assignment.reportservice.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assignment.reportservice.constants.ReportingDataConstants;
import com.assignment.reportservice.model.ReportDataModel;
import com.opencsv.CSVReader;

/**
 * 
 * @author Nagaraja R
 * 
 */
public class ReportingDataUtility {

	private static final Logger LOG = LoggerFactory.getLogger(ReportingDataUtility.class);

	private ReportingDataUtility() {
		// Do not allow to create an object
	}

	/**
	 * This method parse the csv file and store the data(rows) into list with a
	 * delimiter "|"
	 * 
	 * @param filePath
	 * @return list of rows
	 */
	public static List<String> parseCSVFile(final String filePath) {
		LOG.info("Entred in to parseCSVFile()");
		LOG.debug("File path is : {}", filePath);

		List<String> dataList = new ArrayList<>();
		CSVReader csvReader = null;
		try {
			csvReader = new CSVReader(new FileReader(filePath));
			String[] nextLine;
			int lineNumber = 0;
			while ((nextLine = csvReader.readNext()) != null) {
				lineNumber++;
				if (lineNumber == 1)
					continue;
				String data = String.join(ReportingDataConstants.CSV_DATA_SPERATOR, nextLine);
				LOG.info("The data is {}", data);
				dataList.add(data);
			}
		} catch (FileNotFoundException e) {
			LOG.error("Exception occured while parsing CSV: {}", e.getMessage());
		} catch (IOException e) {
			LOG.error("Exception occured while parsing CSV: {}", e.getMessage());
		} finally {
			try {
				if (csvReader != null)
					csvReader.close();
			} catch (IOException e) {
				LOG.error("Exception occured while closing reader: {}", e.getMessage());
			}
		}

		LOG.info("Exit from parseCSVFile()");
		return dataList;
	}

	/**
	 * This method extracts the csv data of two files. This returns the
	 * consolidated data with all the calculated metrics
	 * 
	 * @param janFile
	 * @param febFile
	 * @return report data with all the metrics
	 */
	public static List<ReportDataModel> extractCSVData(File janFile, File febFile) {
		LOG.info("Entred in to extractCSVData()");

		List<String> january_csv_data = parseCSVFile(janFile.getAbsolutePath());
		List<String> february_csv_data = parseCSVFile(febFile.getAbsolutePath());

		List<ReportDataModel> report_consolidated_data = new ArrayList<>();
		january_csv_data.forEach((data) -> {
			ReportDataModel reportData = covertCSVStringToObejct(data, "january");
			report_consolidated_data.add(setAdditionalMetrics(reportData));
		});

		february_csv_data.forEach((data) -> {
			ReportDataModel reportData = covertCSVStringToObejct(data, "february");
			report_consolidated_data.add(setAdditionalMetrics(reportData));
		});

		LOG.info("Exit from extractCSVData()");
		return report_consolidated_data;

	}

	/**
	 * This method calculates and set the additional metrics for the each report
	 * data.
	 * 
	 * @param reportData
	 * @return report data with additional metrics
	 */
	public static ReportDataModel setAdditionalMetrics(ReportDataModel reportData) {
		LOG.debug("Entred in to setAdditionalMetrics()");
		Double ctr = calculateCTR(reportData.getClicks(), reportData.getImpressions());
		Double cr = calculateCR(reportData.getConversions(), reportData.getImpressions());
		Double fill_rate = calculateFillRate(reportData.getImpressions(), reportData.getRequests());
		BigDecimal eCPM = calculateECPM(reportData.getRevenue(), reportData.getImpressions());

		reportData.setCtr(ctr);
		reportData.setCr(cr);
		reportData.setFill_rate(fill_rate);
		reportData.setEcpm(eCPM);
		LOG.debug("Exit from setAdditionalMetrics()");
		return reportData;

	}

	/**
	 * This method converts the single line of csv data to java
	 * object(ReportModel)
	 * 
	 * @param csvStringData
	 * @param month
	 * @return report data model object
	 */
	public static ReportDataModel covertCSVStringToObejct(String csvStringData, String month) {
		LOG.debug("Entred in to covertCSVStringToObejct()");
		String[] csvData = csvStringData.split("\\|");

		ReportDataModel reportData = new ReportDataModel();
		reportData.setMonth(month);
		reportData.setSite(csvData[ReportingDataConstants.SITE]);
		reportData.setRequests(new BigInteger(csvData[ReportingDataConstants.REQUESTS].trim()));
		reportData.setImpressions(new BigInteger(csvData[ReportingDataConstants.IMPRESSIONS].trim()));
		reportData.setClicks(Integer.valueOf(csvData[ReportingDataConstants.CLICKS].trim()));
		reportData.setConversions(Integer.valueOf(csvData[ReportingDataConstants.CONVERSIONS].trim()));
		reportData.setRevenue(new BigDecimal(csvData[ReportingDataConstants.REVENUE].trim()));

		LOG.debug("Exit from covertCSVStringToObejct()");
		return reportData;

	}

	/**
	 * 
	 * It calculates the CTR (Click-through rate)
	 * 
	 * CTR = (clicks ÷ impressions) × 100%
	 * 
	 * @param clicks
	 * @param impressions
	 * @return CTR value
	 */
	public static Double calculateCTR(Integer clicks, BigInteger impressions) {

		BigDecimal clicks_bigdecimal = BigDecimal.valueOf(clicks);
		BigDecimal impressions_bigdecimal = new BigDecimal(impressions);

		return clicks_bigdecimal.divide(impressions_bigdecimal, 6, RoundingMode.CEILING)
				.multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.CEILING).doubleValue();
	}

	/**
	 * It calculates the CR (Conversion rate)
	 * 
	 * CR = (conversions ÷ impressions) × 100%
	 * 
	 * @param conversions
	 * @param impressions
	 * @return CR value
	 */
	public static Double calculateCR(Integer conversions, BigInteger impressions) {

		BigDecimal conversions_bigdecimal = BigDecimal.valueOf(conversions);
		BigDecimal impressions_bigdecimal = new BigDecimal(impressions);
		return conversions_bigdecimal.divide(impressions_bigdecimal, 6, RoundingMode.CEILING)
				.multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.CEILING).doubleValue();
	}

	/**
	 * It calculates the Fill Rate
	 * 
	 * Fill Rate = (impressions ÷ requests) × 100%
	 * 
	 * @param impressions
	 * @param requests
	 * @return Fill Rate
	 */
	public static Double calculateFillRate(BigInteger impressions, BigInteger requests) {

		BigDecimal impressions_bigdecimal = new BigDecimal(impressions);
		BigDecimal requests_bigdecimal = new BigDecimal(requests);

		return impressions_bigdecimal.divide(requests_bigdecimal, 6, RoundingMode.CEILING)
				.multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.CEILING).doubleValue();

	}

	/**
	 * It calculates the eCPM(Effective Cost Per Thousand)
	 * 
	 * @param revenue
	 * @param impressions
	 * @return eCPM value
	 */
	public static BigDecimal calculateECPM(BigDecimal revenue, BigInteger impressions) {
		BigDecimal impressions_bigdecimal = new BigDecimal(impressions);
		return revenue.multiply(BigDecimal.valueOf(1000)).divide(impressions_bigdecimal, 2, RoundingMode.CEILING);

	}
	
	/**
	 * 
	 * Returns the month name for a valid id.
	 * 
	 * Valid IDS: 
	 * 
	 * {@value 1} and {@value jan} for JANUARY
	 * {@value 2} and {@value feb} for FEBRUARY
	 * 
	 * 
	 * @param monthId
	 * @return month name
	 */
	public static String getMonthName(String monthId) {

		if (monthId.equals("1") || monthId.equalsIgnoreCase("jan"))
			return "JANUARY";
		else if (monthId.equals("2") || monthId.equalsIgnoreCase("feb"))
			return "FEBRUARY";
		else
			return monthId;

	}

}
