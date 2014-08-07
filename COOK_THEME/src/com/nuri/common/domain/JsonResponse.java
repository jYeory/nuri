package com.nuri.common.domain;

import java.util.List;

public class JsonResponse<T> {
	/**
	* Current page of the query
	*/
	private String page;
	 
	/**
	* Total pages for the query
	*/
	private String total;

	/**
	* Total number of records for the query
	*/
	private String records;
	 
	/**
	* An array that contains the actual objects
	*/
	private List<T> rows;
	 
	public JsonResponse() {
	}
	 
	public String getPage() {
		return page;
	}
	 
	public void setPage(String page) {
		this.page = page;
	}
	 
	public String getTotal() {
		return total;
	}
	 
	public void setTotal(String total) {
		this.total = total;
	}
	 
	public String getRecords() {
		return records;
	}
	 
	public void setRecords(String records) {
		this.records = records;
	}
	 
	public List<T> getRows() {
		return rows;
	}
	 
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}