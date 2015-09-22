package com.krkh.xls.util;

import java.util.List;

public class ListDtlsObject implements DetalsDtlsInterface{
	private String query;
	private List fieldDtlsLL;
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public List getFieldDtlsLL() {
		return fieldDtlsLL;
	}
	public void setFieldDtlsLL(List fieldDtlsLL) {
		this.fieldDtlsLL = fieldDtlsLL;
	}
	@Override
	public String toString() {
		return "ListDtlsObject [query=" + query + ", fieldDtlsLL="
				+ fieldDtlsLL + "]";
	}
	@Override
	public List getDetailLL() {
		// TODO Auto-generated method stub
		return fieldDtlsLL;
	}
}
