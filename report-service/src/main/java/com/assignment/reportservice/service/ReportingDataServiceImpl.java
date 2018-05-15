package com.assignment.reportservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ReportingDataServiceImpl implements ReportingDataService {

	private static final Logger LOG = LoggerFactory.getLogger(ReportingDataServiceImpl.class);

	@Override
	public String testService() {
		LOG.info("Entered in to testService() ");
		return "Service is Up";
	}

}
