//Find jar from here "http://poi.apache.org/download.html"
package com.krkh.xls.util;

import  java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFontFormatting;
import  org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
import  org.apache.poi.hssf.usermodel.HSSFRow;
import  org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.krkh.xls.util.XLSFieldObject;

public class GenerateXLS{
    public void generateXLS1(List inputData, String fileNameWithPath, String dataClassName, HSSFWorkbook workbook) {
        try {
            HSSFSheet sheet = workbook.createSheet("Disb Details");
            
            Class clsHeader = Class.forName(dataClassName+"Header");
            Class cls = Class.forName(dataClassName);
            
            Method[] headerMethods = clsHeader.getDeclaredMethods();
            Method[] methods = cls.getDeclaredMethods();
            /*Creating Header Sections */
            Object headerObj = clsHeader.newInstance();
            //System.out.println(headerObj.toString());
            CellStyle headerCellStyle = getHeaderCellStyle(workbook);
            
            HSSFRow rowhead = sheet.createRow((short)0);
            for(int i=0; i<headerMethods.length; i++){
            	if((headerMethods[i].getName()).startsWith("get")){
            		int cellNo = Integer.parseInt((headerMethods[i].getName().split("_"))[1])-1;
            		HSSFCell cell = rowhead.createCell(cellNo);
            		cell.setCellStyle(headerCellStyle);           		
            		cell.setCellValue(headerMethods[i].invoke(headerObj)+"");
            		sheet.autoSizeColumn(cellNo);
            	}
            }
            /* Creating Details Sections */
            CellStyle detailCellStyle = getDetailCellStyle(workbook);
            for(int i=0; i<inputData.size(); i++){
            	Object inputObj = cls.cast(inputData.get(i));
            	int count = 0;
            	HSSFRow row = sheet.createRow((short)(i+1));
            	for(int j=0;j<methods.length; j++){
            		if((methods[j].getName()).startsWith("get")){
            			int cellNo = Integer.parseInt((methods[j].getName().split("_"))[1])-1;
            			HSSFCell cell = row.createCell(cellNo);
            			cell.setCellStyle(detailCellStyle);
            			if((methods[j].getName()).contains("Amt") || (methods[j].getName()).contains("Num"))
            				cell.setCellValue(Double.parseDouble((methods[j].invoke(inputObj))+""));
            			else
            				cell.setCellValue(methods[j].invoke(inputObj)+"");
            			count++;
            		}
            	}
            }
            FileOutputStream fileOut = new FileOutputStream(fileNameWithPath);
            workbook.write(fileOut);
            fileOut.close();
            //System.out.println("Your excel file has been generated!");
        } catch ( Exception ex ) {
            System.out.println(ex);
        }
    }
    private CellStyle getHeaderCellStyle(HSSFWorkbook workbook){
		CellStyle locCellStyle = workbook.createCellStyle();
    	Font locFont = workbook.createFont();
		locCellStyle.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
		locCellStyle.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
		locCellStyle.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
		locCellStyle.setBorderRight(HSSFBorderFormatting.BORDER_THIN);
		locCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		locCellStyle.setWrapText(true);
		locFont.setBold(true);
		locFont.setColor(HSSFColor.BLACK.index);
		locCellStyle.setFont(locFont);
		locCellStyle.setFillBackgroundColor(HSSFColor.BLUE.index);
		locCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		locCellStyle.setWrapText(true);
				
    	return locCellStyle;
    }
    
    private CellStyle getDetailCellStyle(HSSFWorkbook workbook){
		CellStyle locCellStyle = workbook.createCellStyle();
    	Font locFont = workbook.createFont();
		locCellStyle.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
		locCellStyle.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
		locCellStyle.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
		locCellStyle.setBorderRight(HSSFBorderFormatting.BORDER_THIN);
		locCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		locCellStyle.setWrapText(true);
		locFont.setColor(HSSFColor.BLACK.index);
		locCellStyle.setFont(locFont);
		//locCellStyle.setFillBackgroundColor(HSSFColor.BLUE.index);
		//locCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				
    	return locCellStyle;
    }
    public void generateXLSSummarySheet(Object inputObj, String fileNameWithPath, String dataClassName, HSSFWorkbook workbook){
        
        HSSFSheet sheet = workbook.createSheet("Summary Details");
        
        Class clsHeader;
		try {
			clsHeader = Class.forName(dataClassName+"Header");
		
        Class cls = Class.forName(dataClassName);
        
        Method[] headerMethods = clsHeader.getDeclaredMethods();
        Method[] methods = cls.getDeclaredMethods();
        /*Creating Header Sections */
        Object headerObj = clsHeader.newInstance();
        //System.out.println(headerObj.toString());
        CellStyle headerCellStyle = getHeaderCellStyle(workbook);
        Object summaryObj = cls.cast(inputObj);
        CellStyle detailCellStyle = getDetailCellStyle(workbook);
        for(int i=0; i<headerMethods.length; i++){
        	if((headerMethods[i].getName()).startsWith("get")){
        		if(!headerMethods[i].getName().contains("_"))
        			continue;
        		int rowNo = Integer.parseInt((headerMethods[i].getName().split("_"))[1])-1;
        		HSSFRow rowhead = sheet.createRow((short)rowNo);
        		HSSFCell cell = rowhead.createCell(8);
        		cell.setCellStyle(headerCellStyle);           		
        		cell.setCellValue(headerMethods[i].invoke(headerObj)+"");
        		sheet.autoSizeColumn(8);
        		String summaryGetName = (headerMethods[i].getName().split("Header"))[0];
        		for(int j=0; j<methods.length; j++){
        			if(methods[j].getName().startsWith(summaryGetName)){
        				int cellNo = Integer.parseInt((methods[j].getName().split("_"))[1])-1;
            			HSSFCell cellDtl = rowhead.createCell(9);
            			cellDtl.setCellStyle(detailCellStyle);
            			if((methods[j].getName()).contains("Amt") || (methods[j].getName()).contains("Num"))
            				cellDtl.setCellValue(Double.parseDouble((methods[j].invoke(inputObj))+""));
            			else
            				cellDtl.setCellValue(methods[j].invoke(inputObj)+"");
            			sheet.autoSizeColumn(9);
        			}
        			
        		}
        	}
        }
        FileOutputStream fileOut = new FileOutputStream(fileNameWithPath);
        workbook.write(fileOut);
        fileOut.close();
        //System.out.println("Your excel file has been generated with Summary!");
        
	    } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void generateXLSWithSummaryFromTemplate(SummaryDtlsInterface summaryObj, List dtlObjLL, String targetFile,
    												String templateFile)
    {
    	FileInputStream inp;
		
		try {
			inp = new FileInputStream(templateFile);
			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
			Row[] rows = new Row[summaryObj.getSummaryLL().size()];
	    	Cell[] cells = new Cell[summaryObj.getSummaryLL().size()];
	    	populateSummary(sheet,summaryObj,rows,cells);
	    	   	
			populateDtlList(sheet, dtlObjLL);
			
			FileOutputStream fileOut = new FileOutputStream(targetFile);
		    wb.write(fileOut);
		    fileOut.close();
			} catch (EncryptedDocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
    private void populateSummary(Sheet sheet, SummaryDtlsInterface summaryObj, Row[] rows, Cell[] cells){
    	List summaryFldDtlsLL = summaryObj.getSummaryLL();
    	System.out.println("entered populateSummary size = "+summaryFldDtlsLL.size());
    	
    	for(int i=0; i<summaryFldDtlsLL.size(); i++){
			XLSFieldObject locObj = (XLSFieldObject) summaryFldDtlsLL.get(i);
			rows[i] = sheet.getRow(locObj.getRowNo());
			if(rows[i] == null)
				rows[i] = sheet.createRow(locObj.getRowNo());
			cells[i] = rows[i].getCell(locObj.getColumnNo());
			if(cells[i] == null)
				cells[i] = rows[i].createCell(locObj.getColumnNo());
			if(!locObj.getPrefix().equals("")){
				String value = locObj.getPrefix()+" "+locObj.getFieldValue();
				locObj.setFieldValue(value);
			}
			if(!locObj.getSuffix().equals("")){
				String value = locObj.getFieldValue() + " "+locObj.getSuffix();
				locObj.setFieldValue(value);
			}
			System.out.println(locObj.getFieldName()+"  "+locObj.getFieldValue());
			setCellValue(cells[i],locObj);
			//cells[i].setCellValue(locObj.getFieldValue());
    	}
    	return;
    }
    
    private void populateDtlList(Sheet sheet, List dtlObjLL){
    	
    	if(dtlObjLL.size() == 0)
    		return;
    	System.out.println("dtlObjLL.size() "+dtlObjLL.size());
    	List FldDtlsLL = ((XLSFieldObject)dtlObjLL.get(0)).getValueList();
    	Row[] rows = new Row[FldDtlsLL.size()];
    	Cell[][] cells = new Cell[FldDtlsLL.size()][dtlObjLL.size()];
    	System.out.println("FldDtlsLL.size() "+FldDtlsLL.size());
    	
    	int templateRowNo = ((XLSFieldObject)(dtlObjLL.get(0))).getListStartRow();
    	Row templateRow = sheet.getRow(templateRowNo);
    	for(int j=0; j<FldDtlsLL.size(); j++){
    		XLSFieldObject FldDtlsLLObject = (XLSFieldObject) dtlObjLL.get(0);
    		rows[j] = sheet.getRow((FldDtlsLLObject.getListStartRow())+j+1);
			if(rows[j] == null){
				System.out.println("enter create row");
				rows[j] = sheet.createRow((FldDtlsLLObject.getListStartRow())+j+1);
			}
    		for(int i=0; i<dtlObjLL.size(); i++) {
				XLSFieldObject locObj = (XLSFieldObject) dtlObjLL.get(i);
				cells[j][i] = rows[j].getCell(locObj.getColumnNo());
				String value = (String) locObj.getValueList().get(j);
				locObj.setFieldValue(value);
				if(cells[j][i] == null)
					cells[j][i] = rows[j].createCell(locObj.getColumnNo());
				if(!locObj.getPrefix().equals("")){
					value = locObj.getPrefix()+" "+locObj.getFieldValue();
					locObj.setFieldValue(value);
				}
				if(!locObj.getSuffix().equals("")){
					value = locObj.getFieldValue() + " "+locObj.getSuffix();
					locObj.setFieldValue(value);
				}
				
				Cell templateCell = templateRow.getCell(locObj.getColumnNo());
				cells[j][i].setCellStyle(templateCell.getCellStyle());
				cells[j][i].setCellValue(locObj.getFieldValue());
				setCellValue(cells[j][i],locObj);
	    	}
    	}
    	deleteRow(sheet, templateRowNo);
    	//sheet.removeRow(templateRow);
    }
    private void deleteRow(Sheet sheet, int rowNo){
    	int lastRowNum=sheet.getLastRowNum();
        if(rowNo>=0&&rowNo<lastRowNum){
            sheet.shiftRows(rowNo+1,lastRowNum, -1);
        }
        if(rowNo==lastRowNum){
            Row removingRow=sheet.getRow(rowNo);
            if(removingRow!=null){
                sheet.removeRow(removingRow);
            }}

    }
    public void generateXLS(XMLObject xmlObject, String templateFileWithPath, String fileNameWithPath){
    	FileInputStream inp;
		try {
			inp = new FileInputStream(templateFileWithPath);
			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
	    	if(xmlObject.getXlsType() == 1){
	    		SummaryDataObject summaryObj = (SummaryDataObject) xmlObject.getSummaryDtls();
	    		Row[] rows = new Row[summaryObj.getSummaryLL().size()];
		    	Cell[] cells = new Cell[summaryObj.getSummaryLL().size()];
		    	populateSummary(sheet,summaryObj,rows,cells);
	    	}
	    	else if(xmlObject.getXlsType() == 2){
	    		ListDtlsObject listObj = (ListDtlsObject)xmlObject.getListDtls();
		    	populateDtlList(sheet,listObj.getDetailLL());
	    	}
	    	FileOutputStream fileOut = new FileOutputStream(fileNameWithPath);
		    wb.write(fileOut);
		    fileOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
    }
    private void setCellValue(Cell cell, XLSFieldObject fieldObject){
    	if(fieldObject.getFieldValue() == null)
    		return;
    	if(!fieldObject.getPrefix().equals("") || !fieldObject.getSuffix().equals("")){
	    	if(!fieldObject.getPrefix().equals("")){
				String value = fieldObject.getPrefix()+" "+fieldObject.getFieldValue();
				fieldObject.setFieldValue(value);
			}
			if(!fieldObject.getSuffix().equals("")){
				String value = fieldObject.getFieldValue() + " "+fieldObject.getSuffix();
				fieldObject.setFieldValue(value);
			}
			fieldObject.setDataType("String");
    	}
    	if(fieldObject.getDataType().equals("int")){
    		double locValueInt = Integer.parseInt(fieldObject.getFieldValue());
			cell.setCellValue(locValueInt);
    	}else if(fieldObject.getDataType().equals("float")){
    		double locValueFloat = Float.parseFloat(fieldObject.getFieldValue());
			cell.setCellValue(locValueFloat);
    	}else if(fieldObject.getDataType().equals("double")){
    		double locValueFloat = Double.parseDouble(fieldObject.getFieldValue());
			cell.setCellValue(locValueFloat);
    	}else
    		cell.setCellValue(String.valueOf(fieldObject.getFieldValue()));
    	/*switch(DataType.valueOf(fieldObject.getDataType())){
    		case XLSInteger:
    			double locValueInt = Integer.parseInt(fieldObject.getFieldValue());
    			cell.setCellValue(locValueInt);
    			break;
    			
    		case XLSFloat:
    			double locValueFloat = Float.parseFloat(fieldObject.getFieldValue());
    			cell.setCellValue(locValueFloat);
    			break;
    		
    		case XLSString:
    		default:
    			cell.setCellValue(String.valueOf(fieldObject.getFieldValue()));
    			break;
    	}*/
    }
}
