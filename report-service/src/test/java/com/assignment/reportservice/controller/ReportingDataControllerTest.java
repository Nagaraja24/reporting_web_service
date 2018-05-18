package com.assignment.reportservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.assignment.reportservice.model.ReportDataModel;
import com.assignment.reportservice.service.ReportingDataService;

/**
 * 
 * Test cases fro the controller.
 * 
 * Have written for two end points.
 * 
 * Need to write for remaining end points.
 * 
 * @author Nagaraja R
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ReportingDataControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private ReportingDataController reportingDataController;

	@Mock
	private ReportingDataService reportingDataService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(reportingDataController).build();
	}

	@Test
	public void testChecStatus() throws Exception {
		
		Mockito.when(reportingDataService.testService()).thenReturn("Service is Up");
		
		mockMvc.perform(get("/reportservice/test"))
		.andExpect(status().isOk())
		.andExpect(content().string("Service is Up"));
	}
	
	@Test
	public void testJson() throws Exception{
		
		ReportDataModel reportData = new ReportDataModel();
		reportData.setMonth("january");
		reportData.setSite("android");
		
		Mockito.when(reportingDataService.getReportDataByMonthAndSite("jan", "android")).thenReturn(reportData);
		
		mockMvc.perform(get("/reportservice/reports/jan/android")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.month",Matchers.is("january")))
		.andExpect(jsonPath("$.site",Matchers.is("android")));
		
	}

}
