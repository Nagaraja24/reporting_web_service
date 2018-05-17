package com.assignment.reportservice.util;
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * 
 * Test cases for the utility methods.
 * 
 * Need to write the test cases for all the methods
 * 
 * Have written the sample test case for two of the methods
 * 
 * @author Nagaraja R
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ReportingDataUtilityTest {
	
	@Test
	public void testGetMonthName(){
		assertSame("JANUARY", ReportingDataUtility.getMonthName("jan"));
		assertSame("JANUARY", ReportingDataUtility.getMonthName("JAN"));
		assertSame("JANUARY", ReportingDataUtility.getMonthName("1"));
		assertSame("FEBRUARY", ReportingDataUtility.getMonthName("2"));
		assertSame("FEBRUARY", ReportingDataUtility.getMonthName("FEB"));
		assertSame("AnyOther", ReportingDataUtility.getMonthName("AnyOther"));
	}
	
	@Test
	public void testGetSiteName(){
		assertSame("mobile web", ReportingDataUtility.getSiteName("mobile_web"));
		assertSame("mobile web", ReportingDataUtility.getSiteName("MOBILE_web"));
		assertSame("desktop web", ReportingDataUtility.getSiteName("desktop_web"));
		assertSame("desktop web", ReportingDataUtility.getSiteName("desktop_WEB"));
		assertSame("AnyOther", ReportingDataUtility.getSiteName("AnyOther"));
	}

}
