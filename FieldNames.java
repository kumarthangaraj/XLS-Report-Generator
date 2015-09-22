package com.krkh.xls.util;

public enum FieldNames {
	fieldName("fieldName"),
	fieldValue("fieldValue"),
	rowNo("rowNo"),
	columnNo("columnNo"),
	dataType("dataType"),
	prefix("prefix"),
	suffix("suffix"), 
	listStartRow("listStartRow"), 
	dbFieldFlag("dbFieldFlag"),
	blank("");
	private String name;
	
	FieldNames(String name){
		this.name = name;
	}
	
	public String getName(String names){
		 
		return name;
	}
	
}
