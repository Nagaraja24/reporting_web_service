package com.assignment.reportservice.constants;

public interface ReportingDataConstants {

	public String CSV_DATA_SPERATOR = "|";
	public int SITE = 0;
	public int REQUESTS = 1;
	public int IMPRESSIONS = 2;
	public int CLICKS = 3;
	public int CONVERSIONS = 4;
	public int REVENUE = 5;

	public String GET_ALL_REPORTS_QUERY = "select report from ReportDataModel report";
	public String GET_REPORT_BY_MONTH_AND_PARAM_QUERY = "select reportData from ReportDataModel reportData WHERE LOWER(reportData.month) = LOWER(:month) AND LOWER(reportData.site) = LOWER(:site)";

}
