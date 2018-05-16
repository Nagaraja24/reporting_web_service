package com.assignment.reportservice.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "report_data")
public class ReportDataModel {

	@Id
	@GeneratedValue
	private Integer id;
	private String month;
	private String site;
	@Type(type = "org.hibernate.type.BigIntegerType")
	private BigInteger requests;
	@Type(type = "org.hibernate.type.BigIntegerType")
	private BigInteger impressions;
	@Type(type = "org.hibernate.type.IntegerType")
	private Integer clicks;
	@Type(type = "org.hibernate.type.IntegerType")
	private Integer conversions;
	private BigDecimal revenue;
	@JsonProperty(value = "CTR")
	private Double ctr;
	@JsonProperty(value = "CR")
	private Double cr;
	private Double fill_rate;
	@JsonProperty(value = "eCPM")
	private BigDecimal ecpm;

	public ReportDataModel() {
		// Empty Constructor
	}

	public ReportDataModel(String month, String site, BigInteger requests, BigInteger impressions, Integer clicks,
			Integer conversions, BigDecimal revenue) {
		super();
		this.month = month;
		this.site = site;
		this.requests = requests;
		this.impressions = impressions;
		this.clicks = clicks;
		this.conversions = conversions;
		this.revenue = revenue;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public BigInteger getRequests() {
		return requests;
	}

	public void setRequests(BigInteger requests) {
		this.requests = requests;
	}

	public BigInteger getImpressions() {
		return impressions;
	}

	public void setImpressions(BigInteger impressions) {
		this.impressions = impressions;
	}

	public Integer getClicks() {
		return clicks;
	}

	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}

	public Integer getConversions() {
		return conversions;
	}

	public void setConversions(Integer conversions) {
		this.conversions = conversions;
	}

	public BigDecimal getRevenue() {
		return revenue;
	}

	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}

	public Double getCtr() {
		return ctr;
	}

	public void setCtr(Double ctr) {
		this.ctr = ctr;
	}

	public Double getCr() {
		return cr;
	}

	public void setCr(Double cr) {
		this.cr = cr;
	}

	public Double getFill_rate() {
		return fill_rate;
	}

	public void setFill_rate(Double fill_rate) {
		this.fill_rate = fill_rate;
	}

	public BigDecimal getEcpm() {
		return ecpm;
	}

	public void setEcpm(BigDecimal ecpm) {
		this.ecpm = ecpm;
	}

}
