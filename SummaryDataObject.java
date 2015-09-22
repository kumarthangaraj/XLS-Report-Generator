package com.krkh.xls.util;

import java.util.LinkedList;
import java.util.List;

import com.krkh.xls.util.SummaryDtlsInterface;

public class SummaryDataObject implements SummaryDtlsInterface{
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setSummaryLL(List summaryLL) {
		this.summaryLL = summaryLL;
	}

	String query;
	List summaryLL = new LinkedList();

	@Override
	public List getSummaryLL() {
		// TODO Auto-generated method stub
		return summaryLL;
	}
	
}
