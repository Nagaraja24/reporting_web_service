package com.assignment.reportservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.reportservice.model.ReportDataModel;
import com.assignment.reportservice.service.ReportingDataService;

/**
 * 
 * @author Nagaraja R
 * 
 * This is controller class
 *
 */
@RestController
@RequestMapping("/reports")
public class ReportingDataController {

	@Autowired
	private ReportingDataService reportDataService;

	@GetMapping("/test")
	public String checkStatus() {
		return reportDataService.testService();
	}

	@GetMapping("/savedata")
	public String saveDataFromCSVToDatabase() {
		return reportDataService.saveReportData();
	}

	@GetMapping("/getAllReports")
	public List<ReportDataModel> getAllReports() {
		return reportDataService.getAllReportData();
	}

	@GetMapping("/getReport/{month}/{site}")
	public ReportDataModel getReportByMonthAndSite(@PathVariable("month") String month,	@PathVariable("site") String site) {
		return reportDataService.getReportDataByMonthAndSite(month, site);
	}

}
