package com.krkh.xls.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.krkh.xls.util.XLSFieldObject;

public class OracleSQLExecutor implements SQLExecutorInterface{

	@Override
	public void executeSimpleQueryForSummary(XMLObject xmlObject, Connection con) {
		try {
			Statement stmt = con.createStatement();
			String query = xmlObject.getSummaryDtls().getQuery();
			LinkedList summaryLL = (LinkedList) xmlObject.getSummaryDtls().getSummaryLL();
			System.out.println("summaryLL = "+summaryLL);
			ResultSet rs=stmt.executeQuery(query);
			if(rs.next()){
    			//System.out.println("inside if");
    			for(int i=0; i<summaryLL.size(); i++){
    				XLSFieldObject locObj = (XLSFieldObject) summaryLL.get(i);
    				if(locObj.getDataType().equals("String"))
    					locObj.setFieldValue(rs.getString(locObj.getFieldName()));
    				else if(locObj.getDataType().equals("int"))
    					locObj.setFieldValue(String.valueOf(rs.getInt(locObj.getFieldName())));
    				else if(locObj.getDataType().equals("float"))
    					locObj.setFieldValue(String.valueOf(rs.getFloat(locObj.getFieldName())));
    				else if(locObj.getDataType().equals("double"))
    					locObj.setFieldValue(String.valueOf(rs.getDouble(locObj.getFieldName())));
    			}
    			//System.out.println("leaving agency name");
    		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void executeSimpleQueryForList(XMLObject xmlObject, Connection con) {
		try {
			Statement stmt = con.createStatement();
			String query = xmlObject.getListDtls().getQuery();
			LinkedList listLL = (LinkedList) xmlObject.getListDtls().getDetailLL();
			System.out.println("listLL = "+listLL);
			ResultSet rs=stmt.executeQuery(query);
			while(rs.next()){
				for(int i=0; i<listLL.size(); i++){
					
    				XLSFieldObject locObj = (XLSFieldObject) listLL.get(i);
    				System.out.println("locObj.getFieldName = "+locObj.getFieldName());
    				if(locObj.getDataType().equals("String"))
    					locObj.getValueList().add(rs.getString(locObj.getFieldName()));
    				else if(locObj.getDataType().equals("int"))
    					locObj.getValueList().add(String.valueOf(rs.getInt(locObj.getFieldName())));
    				else if(locObj.getDataType().equals("float"))
    					locObj.getValueList().add(String.valueOf(rs.getFloat(locObj.getFieldName())));
    				else if(locObj.getDataType().equals("double"))
    					locObj.getValueList().add(String.valueOf(rs.getDouble(locObj.getFieldName())));
    			}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
