package com.assignment.reportservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.reportservice.model.ReportDataModel;
import com.assignment.reportservice.service.ReportingDataService;

/**
 * 
 * @author Nagaraja R
 * 
 * This is a controller class
 *
 */
@RestController
@RequestMapping("/reportservice")
public class ReportingDataController {

	@Autowired
	private ReportingDataService reportDataService;

	@GetMapping("/test")
	public String checkStatus() {
		return reportDataService.testService();
	}

	@PutMapping("/reports")
	public String saveDataFromCSVToDatabase() {
		return reportDataService.saveReportData();
	}

	@GetMapping("/reports")
	public List<ReportDataModel> getAllReports() {
		return reportDataService.getAllReportData();
	}

	@GetMapping("/reports/{month}/{site}")
	public ReportDataModel getReportByMonthAndSite(@PathVariable("month") String month, @PathVariable("site") String site) {
		return reportDataService.getReportDataByMonthAndSite(month, site);
	}

}
