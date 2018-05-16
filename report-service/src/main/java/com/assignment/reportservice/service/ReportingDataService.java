package com.assignment.reportservice.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.assignment.reportservice.model.ReportDataModel;

@Service
public interface ReportingDataService {

	public String testService();

	public String saveReportData(Resource januaryReportFile, Resource februaryReportFile);

	public List<ReportDataModel> getAllReportData();
	
	public ReportDataModel getReportDataByMonthAndSite(String month, String site);

}
