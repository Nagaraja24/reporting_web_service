package com.assignment.reportservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.reportservice.service.ReportingDataService;

/**
 * 
 * @author Nagaraja R
 *
 */
@RestController
@RequestMapping("/reports")
public class ReportingDataController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ReportingDataController.class);
	
	@Autowired
	private ReportingDataService reportDataService;
	
	@GetMapping("/test")
	public String testEndPoint(){
		LOG.info("Entered into testEndPoint()");
		
		return reportDataService.testService();
		
	}

}
