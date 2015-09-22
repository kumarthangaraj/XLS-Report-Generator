package com.krkh.xls.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLReader {
	
	public XMLObject readXML(String xmlName){
		try {
			XMLObject xmlObject = new XMLObject();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			File file = new File(xmlName);
			InputStream input = new FileInputStream(file);
			Document document = builder.parse(input);
			SummaryDataObject summaryObject = new SummaryDataObject();
			
			NodeList nodeList = document.getDocumentElement().getChildNodes();
			int type = 0;
			for(int i=0; i<nodeList.getLength(); i++){
				Node node = nodeList.item(i);
				if(node instanceof  Element){
					String name = node.getNodeName();
					System.out.println("name = "+name);
					if(name.equals("XLSType")){
						type = Integer.parseInt(node.getTextContent());
						xmlObject.setXlsType(type);
						System.out.println("type "+type);
						break;
					}
				}
			}
			switch(type){
			case 1:
				xmlObject.setSummaryDtls(parseXMLForSummaryXLS(nodeList));
				break;
			case 2:
				xmlObject.setListDtls(parseXMLForListXLS(nodeList));
				break;
			default:
				break;
			}
			return xmlObject;
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public SummaryDtlsInterface parseXMLForSummaryXLS(NodeList nodeList){
		SummaryDataObject summaryObj = new SummaryDataObject();
		List xlsFldDtlsList = new LinkedList();
		for(int i=0; i<nodeList.getLength(); i++){
			Node node = nodeList.item(i);
			if(node instanceof  Element){
				String name = node.getNodeName();
				if(name.equals("XLSType"))
					continue;
				if(name.equals("Query")){
					summaryObj.setQuery(node.getTextContent());
					System.out.println("query = "+node.getTextContent());
				}
				if(name.equals("XLSFieldObjects")){
					NodeList childNodes = node.getChildNodes();
					for(int j=0; j<childNodes.getLength(); j++){
						XLSFieldObject xlsFldObj = new XLSFieldObject();
						Node childNode = childNodes.item(j);
						if(childNode instanceof Element){
							NodeList grandChilds = childNode.getChildNodes();
							populateXLSFieldObj(xlsFldObj, grandChilds);
							xlsFldDtlsList.add(xlsFldObj);
						}					
					}
					summaryObj.setSummaryLL(xlsFldDtlsList);
				}
			}
		}
		return summaryObj;
		
	}
	private DetalsDtlsInterface parseXMLForListXLS(NodeList nodeList){
		ListDtlsObject listObj = new ListDtlsObject();
		List xlsFldDtlsList = new LinkedList();
		for(int i=0; i<nodeList.getLength(); i++){
			Node node = nodeList.item(i);
			if(node instanceof  Element){
				String name = node.getNodeName();
				if(name.equals("XLSType"))
					continue;
				if(name.equals("Query")){
					listObj.setQuery(node.getTextContent());
					System.out.println("query = "+node.getTextContent());
				}
				if(name.equals("XLSFieldObjects")){
					NodeList childNodes = node.getChildNodes();
					for(int j=0; j<childNodes.getLength(); j++){
						XLSFieldObject xlsFldObj = new XLSFieldObject();
						Node childNode = childNodes.item(j);
						if(childNode instanceof Element){
							NodeList grandChilds = childNode.getChildNodes();
							populateXLSFieldObj(xlsFldObj, grandChilds);
							xlsFldDtlsList.add(xlsFldObj);
						}					
					}
					listObj.setFieldDtlsLL(xlsFldDtlsList);
				}
			}
		}
		return listObj;
	}
	private static void populateXLSFieldObj(XLSFieldObject xlsFldObj, NodeList childs){
		for(int i=0; i<childs.getLength(); i++){
			Node node = childs.item(i);
			if(node instanceof Element){
				String Content = node.getTextContent();
				FieldNames fieldName;
				switch(FieldNames.valueOf(node.getNodeName())){
					case fieldName:
						xlsFldObj.setFieldName(Content);
						break;
					case fieldValue:
						xlsFldObj.setFieldValue(Content);
						break;
					case rowNo:
						xlsFldObj.setRowNo(Integer.parseInt(Content));
						break;
					case columnNo:
						xlsFldObj.setColumnNo(Integer.parseInt(Content));
						break;
					case dataType:
						xlsFldObj.setDataType(Content);
						break;
					case prefix:
						xlsFldObj.setPrefix(Content);
						break;
					case suffix:
						xlsFldObj.setSuffix(Content);
						break;
					case listStartRow:
						xlsFldObj.setListStartRow(Integer.parseInt(Content));
						break;
					case dbFieldFlag:
						xlsFldObj.setDbFieldFlage(Content.charAt(0));
						break;
					case blank:
						break;
				}
			}
		}
	}
}
