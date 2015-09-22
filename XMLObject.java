package com.krkh.xls.util;

public class XMLObject {
	private int xlsType;
	private SummaryDtlsInterface summaryDtls;
	private DetalsDtlsInterface listDtls;
	public int getXlsType() {
		return xlsType;
	}
	public void setXlsType(int xlsType) {
		this.xlsType = xlsType;
	}
	public SummaryDtlsInterface getSummaryDtls() {
		return summaryDtls;
	}
	public void setSummaryDtls(SummaryDtlsInterface summaryDtls) {
		this.summaryDtls = summaryDtls;
	}
	public DetalsDtlsInterface getListDtls() {
		return listDtls;
	}
	public void setListDtls(DetalsDtlsInterface listDtls) {
		this.listDtls = listDtls;
	}
}
