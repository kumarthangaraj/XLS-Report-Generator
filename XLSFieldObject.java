package com.krkh.xls.util;

import java.util.ArrayList;
import java.util.List;

public class XLSFieldObject {
	public XLSFieldObject(String fieldName, String fieldValue, int rowNo,
			int columnNo, String dataType, String prefix, String suffix,
			int listStartRow, char dbFieldFlag) {
		super();
		this.fieldName = fieldName;
		this.fieldValue = fieldValue; 
		this.rowNo = rowNo; 
		this.columnNo = columnNo; 
		this.dataType = dataType;
		this.prefix = prefix;
		this.suffix = suffix;
		this.listStartRow = listStartRow;
		this.dbFieldFlag = dbFieldFlag;
	}

	private String fieldName = "";
	private String fieldValue = "";
	private int 	rowNo = 0;
	private int 	columnNo = 0;
	private String	dataType = "";
	private String  prefix = "";
	private String suffix = "";
	private int listStartRow = 0;
	private char dbFieldFlag = 'N';
	private List valueList = new ArrayList();
	
	public XLSFieldObject(String fieldValue){
		this.fieldValue = fieldValue;
	}
	
	public XLSFieldObject(){
		
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public int getRowNo() {
		return rowNo;
	}

	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}

	public int getColumnNo() {
		return columnNo;
	}

	public void setColumnNo(int columnNo) {
		this.columnNo = columnNo;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public int getListStartRow() {
		return listStartRow;
	}

	public void setListStartRow(int listStartRow) {
		this.listStartRow = listStartRow;
	}
	
	public char getDbFieldFlage() {
		return dbFieldFlag;
	}

	public char getDbFieldFlag() {
		return dbFieldFlag;
	}

	public void setDbFieldFlag(char dbFieldFlag) {
		this.dbFieldFlag = dbFieldFlag;
	}

	public List getValueList() {
		return valueList;
	}

	public void setValueList(List valueList) {
		this.valueList = valueList;
	}

	public void setDbFieldFlage(char dbFieldFlag) {
		this.dbFieldFlag = dbFieldFlag;
	}
	
	@Override
	public String toString() {
		return "XLSFieldObject [fieldName=" + fieldName + ", fieldValue="
				+ fieldValue + ", rowNo=" + rowNo + ", columnNo=" + columnNo
				+ ", dataType=" + dataType + ", prefix=" + prefix + ", suffix="
				+ suffix + ", listStartRow=" + listStartRow + ", dbFieldFlag="
				+ dbFieldFlag + ", valueList=" + valueList + "]";
	}
}
