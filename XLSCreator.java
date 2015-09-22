package com.krkh.xls.util;

public class XLSCreator {
	public static void main(String args[]){
		System.out.println("args[0] *** = ");
		
		if(args[0].equals("-A")){ 
			AdminUI admin = new AdminUI();
			admin.paintAdminScreen(); 
			return;
		}
		
		String xmlFileNameWithPath = args[1];
		//String xmlFileNameWithPath = "./xlsList_Oracle.xml";
		if(xmlFileNameWithPath == null || xmlFileNameWithPath.equals("")){
			System.out.println("Invalid xml file name "+xmlFileNameWithPath);
			return;
		}
		
		XMLReader xmlReader = new XMLReader();
		XMLObject xmlObj = xmlReader.readXML(xmlFileNameWithPath);
		
		if(xmlObj == null){
			System.out.println("unable to read xls");
			return;
		}
		String templateFileNameWithPath = args[2];
		//String templateFileNameWithPath = "./List_Template_Oracle.xls";
		if(templateFileNameWithPath == null || templateFileNameWithPath.equals("")){
			System.out.println("Invalid template file name "+templateFileNameWithPath);
			return;
		}
		SQLExecutor sqlExecute = new SQLExecutor();
		sqlExecute.fillXMLObjValues(xmlObj);
		GenerateXLS xls = new GenerateXLS();
		String fileNameWithPath = args[3];
		//String fileNameWithPath = "./ListDtls.xls";
		xls.generateXLS(xmlObj, templateFileNameWithPath,fileNameWithPath);
	}
}
