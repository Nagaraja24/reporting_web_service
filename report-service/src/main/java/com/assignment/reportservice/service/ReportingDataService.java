package com.assignment.reportservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assignment.reportservice.model.ReportDataModel;
/**
 * 
 * @author Nagaraja R
 * 
 * This is a service endpoints interface
 *
 */
@Service
public interface ReportingDataService {

	public String testService();

	public String saveReportData();

	public List<ReportDataModel> getAllReportData();
	
	public ReportDataModel getReportDataByMonthAndSite(String month, String site);

}
