package com.assignment.reportservice.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.assignment.reportservice.model.ReportDataModel;
import com.assignment.reportservice.repository.ReportingDataRepository;
import com.assignment.reportservice.util.ReportingDataUtility;

@Service
public class ReportingDataServiceImpl implements ReportingDataService {

	private static final Logger LOG = LoggerFactory.getLogger(ReportingDataServiceImpl.class);

	@Autowired
	ReportingDataRepository reportingDataRepository;

	@Override
	public String testService() {
		return "Service is Up";
	}

	@Override
	public String saveReportData(Resource januaryReportFile, Resource februaryReportFile) {
		LOG.info("Entered in to saveReportData() ");

		List<ReportDataModel> reportDataList = null;
		try {
			reportDataList = ReportingDataUtility.extractCSVData(januaryReportFile.getFile(), februaryReportFile.getFile());
		} catch (IOException e) {
			LOG.error("Exception while extracting data from csv files: {}", e.getMessage());
			throw new RuntimeException("Exception while extracting data from csv files:" + e.getMessage());
		}

		reportingDataRepository.saveAll(reportDataList);

		LOG.info("Exit from saveReportData() ");

		return "Data Extracted Successfully from CSV files";
	}

	@Override
	public List<ReportDataModel> getAllReportData() {
		return reportingDataRepository.getAllReportData();
	}

	@Override
	public ReportDataModel getReportDataByMonthAndSite(String month, String site) {
		LOG.info("Entered in to getReportDataByMonthAndSite()");
		LOG.info("Month Name/Id: {} , Site: {}", month, site);

		String monthName = ReportingDataUtility.getMonthName(month);
		LOG.debug("Month Name is : {}", monthName);

		Optional<ReportDataModel> reportDataOptional = reportingDataRepository.getReportByMonthAndSite(monthName, site);
		ReportDataModel reportData = reportDataOptional.get();

		LOG.info("Exit from getReportDataByMonthAndSite()");
		return reportData;
	}

}
