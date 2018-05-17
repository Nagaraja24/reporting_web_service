package com.assignment.reportservice.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assignment.reportservice.constants.ReportingDataConstants;
import com.assignment.reportservice.model.ReportDataModel;
import com.opencsv.CSVReader;

/**
 * 
 * @author Nagaraja R
 * 
 * This is an utility class
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
		LOG.info("File path is : {}", filePath);

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
			throw new RuntimeException("CSV File not found, please check the file paths");
		} catch (IOException e) {
			throw new RuntimeException("CSV File parse exception, please check the file format");
		} finally {
			try {
				if (csvReader != null)
					csvReader.close();
			} catch (IOException e) {
				LOG.error("Exception occured while closing reader: {}", e.getLocalizedMessage());
			}
		}

		LOG.info("Exit from parseCSVFile()");
		return dataList;
	}

	/**
	 * This method extracts the csv data of two files. This returns the
	 * consolidated data with all the calculated metrics
	 * 
	 * @param janFilePath
	 * @param febFilePath
	 * @return report data with all the metrics
	 */
	public static List<ReportDataModel> extractCSVData(String janFile, String febFile) {
		LOG.info("Entred in to extractCSVData()");

		List<String> january_csv_data = parseCSVFile(janFile);
		List<String> february_csv_data = parseCSVFile(febFile);

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
	 * {@value 1} and {@value jan} for JANUARY {@value 2} and {@value feb} for
	 * FEBRUARY
	 * 
	 * 
	 * @param monthId
	 * @return month name
	 */
	public static String getMonthName(String monthId) {

		Objects.requireNonNull(monthId, "Month Id cannot be null");

		if ("1".equals(monthId) || "jan".equalsIgnoreCase(monthId))
			return ReportingDataConstants.JANUARY;
		else if ("2".equals(monthId) || "feb".equalsIgnoreCase(monthId))
			return ReportingDataConstants.FEBRUARY;
		else
			return monthId;

	}

	/**
	 * Returns the site name for valid site id
	 * 
	 * @param siteName
	 * @return Site Name
	 */
	public static String getSiteName(String siteId) {

		Objects.requireNonNull(siteId, "Site Id cannot be null");

		if ("desktop_web".equalsIgnoreCase(siteId))
			return ReportingDataConstants.DESKTOP_WEB_SITE_NAME;
		else if ("mobile_web".equalsIgnoreCase(siteId))
			return ReportingDataConstants.MOBILE_WEB_SITE_NAME;
		else
			return siteId;

	}

}
