package com.assignment.reportservice.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.assignment.reportservice.model.ReportDataModel;
import com.assignment.reportservice.repository.ReportingDataRepository;
import com.assignment.reportservice.util.ReportingDataUtility;
/**
 * 
 * @author Nagaraja R
 * 
 * This is service implementation class
 *
 */
@Service
public class ReportingDataServiceImpl implements ReportingDataService {

	private static final Logger LOG = LoggerFactory.getLogger(ReportingDataServiceImpl.class);

	@Autowired
	private ReportingDataRepository reportingDataRepository;

	@Autowired
	private Environment env;

	@Override
	public String testService() {
		return "Service is Up";
	}

	@Override
	public String saveReportData() {
		LOG.info("Entered in to saveReportData() ");

		String januaryReportFile = env.getProperty("jan.filepath");
		Objects.requireNonNull(januaryReportFile, "January File Path must not be null");
		String februaryReportFile = env.getProperty("feb.filepath");
		Objects.requireNonNull(februaryReportFile, "February File Path must not be null");
		
		List<ReportDataModel> reportDataList = ReportingDataUtility.extractCSVData(januaryReportFile,
				februaryReportFile);

		reportingDataRepository.saveAll(reportDataList);

		LOG.info("Exit from saveReportData() ");

		return "Data loaded in to database successfully from CSV files";
	}

	@Override
	public List<ReportDataModel> getAllReportData() {
		return reportingDataRepository.getAllReportData();
	}

	@Override
	public ReportDataModel getReportDataByMonthAndSite(String month, String site) {
		LOG.info("Entered in to getReportDataByMonthAndSite()");
		LOG.info("Month Name Or Id: {} , Site Name Or Id: {}", month, site);

		String monthName = ReportingDataUtility.getMonthName(month);
		LOG.debug("Month Name is : {}", monthName);
		String siteName = ReportingDataUtility.getSiteName(site);
		LOG.debug("Site Name is : {}", siteName);
		Optional<ReportDataModel> reportDataOptional = reportingDataRepository.getReportByMonthAndSite(monthName, siteName);
		
		if(!reportDataOptional.isPresent())
			return new ReportDataModel();
		
		ReportDataModel reportData = reportDataOptional.get();

		LOG.info("Exit from getReportDataByMonthAndSite()");
		return reportData;
	}

}
