package com.krkh.xls.util;

import java.sql.Connection;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;

public class SQLExecutor {
	private Connection con;
	private SQLExecutorInterface sqlExecutor;
	
	public SQLExecutor(){
		Encrptor encryptor = new Encrptor();
		String inputString = encryptor.decryptFileContent("./encrypted");
		String[] inputArray = inputString.split("\\|");
		String addtionalInfo = "";
		for(int i=1; i<inputArray.length;i++){
			if(i != 1)
				addtionalInfo = addtionalInfo+"|";
			addtionalInfo = addtionalInfo+inputArray[i];
		}
		
		DBConnect dbConnect = new DBConnect(inputArray[0],addtionalInfo);
		this.con = dbConnect.getConnection();
		System.out.println("con = "+con);
		String className = "com.krkh.xls.util."+inputArray[0]+"SQLExecutor";
		try {
			Class cls = Class.forName(className);
			sqlExecutor = (SQLExecutorInterface)cls.newInstance();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}
	
	public void fillXMLObjValues(XMLObject xmlObject){
		if(xmlObject.getXlsType() == 1)
			sqlExecutor.executeSimpleQueryForSummary(xmlObject, this.con);
		else if(xmlObject.getXlsType() == 2)
			sqlExecutor.executeSimpleQueryForList(xmlObject,this.con);
	}

}
