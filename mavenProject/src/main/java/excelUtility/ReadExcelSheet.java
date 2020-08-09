package excelUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utility.commonData;

public class ReadExcelSheet {
	
	public ArrayList readExcelData(int colNo) throws IOException
	{
	  // Call filePath variable using class name.
	  String filePath = commonData.filePath;
	  
	  // Create an object of file class and pass filePath as an input parameter to its constructor.
	  File file = new File(filePath);
	  // Create an object of FileInputStream class and pass file as parameter to its constructor.
	  FileInputStream fis = new FileInputStream(file);
	 
	  // create an object of XSSFWorkbook class and pass fis as parameter to i
	  XSSFWorkbook wb = new XSSFWorkbook(fis);
	  
	  // Get desired sheet from the workbook
	  XSSFSheet sheet = wb.getSheet("Sheet1");
		
	  // Create an ArrayList. It will accept values.
	  ArrayList list = new ArrayList();
	  
	// Iterate through each rows one by one
		  Iterator<Row> rowIterator = sheet.iterator();
		  rowIterator.next();
		  // Checking the next element availability using reference variable row.
		  while(rowIterator.hasNext())
		  {
			  // Moving cursor to next Row using reference variable row.
			  Row r = rowIterator.next();
			  // Moving cursor to the cell by getting cell number;
			  Cell c = r.getCell(colNo);
			  // Read the value of the cell using getStringCellValue() method.
		   
		   	switch (c.getCellTypeEnum()) {
			case NUMERIC:
				list.add(c.getNumericCellValue());
				break;
			case STRING:
				list.add(c.getStringCellValue());
				break;
			}
		 }
		  System.out.println("List: " +list);
		  return list;
	}
}
