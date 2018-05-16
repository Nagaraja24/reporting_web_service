package com.assignment.reportservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assignment.reportservice.constants.ReportingDataConstants;
import com.assignment.reportservice.model.ReportDataModel;

@Repository
public interface ReportingDataRepository extends CrudRepository<ReportDataModel, Long> {

	@Query(ReportingDataConstants.GET_ALL_REPORTS_QUERY)
	public List<ReportDataModel> getAllReportData();
	
	@Query(ReportingDataConstants.GET_REPORT_BY_MONTH_AND_PARAM_QUERY)
	public Optional<ReportDataModel> getReportByMonthAndSite(@Param("month") String month, @Param("site") String site);

}
