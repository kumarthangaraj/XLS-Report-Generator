package com.krkh.xls.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	private String dbType = null;
	private String dbConnectString = null;
	
	public DBConnect(String dbType, String dbConnectString){
		System.out.println("dbType = "+dbType);
		System.out.println("dbConnectString = "+dbConnectString);
		this.dbType = dbType;
		this.dbConnectString = dbConnectString;
	}
	
	public Connection getConnection(){
		Connection dbConnection = null;
		System.out.println("get Connection");
		try {
			/* Oracle DB */
			if(dbType.equals("Oracle")){
				Class.forName("oracle.jdbc.driver.OracleDriver");
				String[] dbDtls = dbConnectString.split("\\|");
				String dbString = "jdbc:oracle:thin:@";
				//IP address
				dbString = dbString+dbDtls[0]+":";
				
				//Port Num
				dbString = dbString+dbDtls[1]+":";
				
				String pass = new String(dbDtls[4]);
				System.out.println("pass = "+pass);
				//SID
				dbString = dbString+dbDtls[2];
				System.out.println("dbString = "+dbString);
				System.out.println("dbDtls[3] = "+dbDtls[3]);
				System.out.println("dbDtls[4] = "+dbDtls[4]);
				dbConnection=DriverManager.getConnection(dbString,dbDtls[3],pass);
				//dbConnection=DriverManager.getConnection("jdbc:oracle:thin:@10.10.1.44:1521:DEVDB","custom","custom");
				//dbConnection=DriverManager.getConnection("jdbc:oracle:thin:@10.10.1.102:1521:UAT2","dbread","dbread");
			}
			/* SQLite DB */
			else if(dbType.equals("SQLite")){
				Class.forName("org.sqlite.JDBC");
				String host = "jdbc:sqlite:"+dbConnectString;
				//String host = "jdbc:sqlite:D\\:/Kumar/Personal/Mobile-App/MyFinance.s3db";
				dbConnection = DriverManager.getConnection(host);
			}
			else 
				System.out.println("Invalid DB Type found");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbConnection;
	}
}
