package com.flk.www.qa.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class VaibhavExcelUtility {
		//public static String filename = System.getProperty("user.dir")+"\\src\\com\\testdata\\data1.xlsx";
		public  String path;
		public  FileInputStream fis = null;
		public  FileOutputStream fileOut =null;
		private XSSFWorkbook workbook = null;
		private XSSFSheet sheet = null;
		
//	public VaibhavExelUtility(String path) {
//			
//			this.path=path;
//			try {
//				fis = new FileInputStream(path);
//				workbook = new XSSFWorkbook(fis);
//				sheet = workbook.getSheetAt(0);
//				fis.close();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			}	
//			
	
		// 	returns the row count in a sheet

		public int getRowCount(String sheetName){
				int index = workbook.getSheetIndex(sheetName);
				if(index==-1)
					return 0;
				else{
				sheet = workbook.getSheetAt(index);
				int number=sheet.getLastRowNum()+1;
				return number;
				}
				
			}
	
		/****************
		 * 
		 * 
		 */
		// ex ArrayList<String> a  = c.getdata(fl, "Sheet1", "Testcase", "case2");
		public static ArrayList<String> getdata(File fl, String sheetName, String testCaseName, String userID) throws Exception
		{
			// TODO Auto-generated method stub
			DataFormatter formatter = new DataFormatter();
			FileInputStream fls = new FileInputStream(fl);
			// get the control of excel file 
			XSSFWorkbook wb = new XSSFWorkbook(fls);
			ArrayList<String> a = new ArrayList<String>();
			
			int totalSheet = wb.getNumberOfSheets();
			int rowCount = 0 , columnCount =0, column =0;
			for (int  i =0; i<totalSheet;i++)
			{
				if(wb.getSheetName(i).equalsIgnoreCase(sheetName))
					{
						XSSFSheet sheet = wb.getSheetAt(i);
						Iterator<Row> allrows = sheet.iterator();
						while(allrows.hasNext())
						{
							Row row = allrows.next();
							Iterator<Cell> cell = row.cellIterator();
							while(cell.hasNext())
							{
								Cell cellvalue = cell.next();
								if(formatter.formatCellValue(cellvalue).equalsIgnoreCase(testCaseName))
								//if(cellvalue.getStringCellValue().equalsIgnoreCase("Created"))
								{
									column = columnCount;
									//System.out.println("yes");
									//System.out.println(rowCount);
									//System.out.println(columnCount);
									
								}
								columnCount++;
							}
							rowCount++;
							columnCount=0;
							while(allrows.hasNext())
							{
								Row r = allrows.next();
								if(formatter.formatCellValue(r.getCell(column)).equalsIgnoreCase(userID))
								//if(r.getCell(column).getStringCellValue().equalsIgnoreCase("8"))
								{
									Iterator<Cell> values = r.cellIterator();
									while(values.hasNext())
									{
										//System.out.println("ok");
										Cell c = values.next();
										a.add(formatter.formatCellValue(c));
										//System.out.print(formatter.formatCellValue(c));
										//System.out.println(value);
									}
								}
							}
						}
						
						
					}
					
			}

			//System.out.println(rowCount);
			//System.out.println(columnCount);
			 wb.close();
			return a;
		}
	
		/****************
		 * 
		 * 
		 */
		
	// ex  c.getDataFromRequiredRowAndCell(fl, "Sheet1", 2 ,3);
	public static String getDataFromRequiredRowAndCell(File fl, String sheetName, int rowNumber, int columnNumber ) throws IOException
	{
		DataFormatter formatter = new DataFormatter();
		FileInputStream fls = new FileInputStream(fl);
		// get the control of excel file 
		XSSFWorkbook wb = new XSSFWorkbook(fls);
		String data="";
		int totalCountOfSheets =wb.getNumberOfSheets();
		for(int i=0;i<totalCountOfSheets;i++)
		{
			if(wb.getSheetName(i).equalsIgnoreCase(sheetName))
			{
				XSSFSheet mySheet = wb.getSheetAt(i);
				Row r = mySheet.getRow(rowNumber);
				Cell cell = r.getCell(columnNumber);
				data = formatter.formatCellValue(cell);
				//data = cell.getStringCellValue();
				//System.out.println(cell);
			}
		}
		
		 wb.close();
		return data;
	}
	
	/****************
	 * 
	 * 
	 */
	
	
	
	public static String getCell(File fl , String sheetName,String cellName) throws IOException{
		FileInputStream fls = new FileInputStream(fl);
		// get the control of excel file 
		XSSFWorkbook wb = new XSSFWorkbook(fls);
		String data="";
		int totalSheets = wb.getNumberOfSheets();
		DataFormatter formatter = new DataFormatter();
		try
		{
		for(int i =0; i<totalSheets;i++)
		{
			if(wb.getSheetName(i).equalsIgnoreCase(sheetName))
			{
				XSSFSheet sheet = wb.getSheetAt(i);
				Pattern r = Pattern.compile("^([A-Z]+)([0-9]+)$");
			    Matcher m = r.matcher(cellName);
			    if(m.matches())
			    {
			    	 String columnName = m.group(1);
			    	 int rowNumber = Integer.parseInt(m.group(2));
			    	 if(rowNumber > 0) {
			    		 
			    		 XSSFCell cell = sheet.getRow(rowNumber-1).getCell(CellReference.convertColStringToIndex(columnName));
			    		 data = formatter.formatCellValue(cell);
			    		 //String value = cell.getStringCellValue();
			    		 wb.close();
			    		 return data;
			             //return sheet.getRow(rowNumber-1).getCell(CellReference.convertColStringToIndex(columnName));
			         }
			    }
			}
			
		}
		
		}
		catch(Exception e )
		{
			e.printStackTrace();
			
		} wb.close();
	    return "default";
	   
	}
	
	public static void writeData(FileInputStream fls, String sheetName) throws IOException
	{
		//Create blank workbook
	      XSSFWorkbook workbook = new XSSFWorkbook();
	      //Create a blank sheet
	      XSSFSheet spreadsheet = workbook.createSheet(sheetName);

	      //Create row object
	      XSSFRow row;

	      //This data needs to be written (Object[])
	      Map < String, Object[] > empinfo = new TreeMap < String, Object[] >();
	      empinfo.put( "1", new Object[] {
	         "EMP ID", "EMP NAME", "DESIGNATION" });
	      
	      empinfo.put( "2", new Object[] {
	         "tp01", "Gopal", "Technical Manager" });
	      
	      empinfo.put( "3", new Object[] {
	         "tp02", "Manisha", "Proof Reader" });
	      
	      empinfo.put( "4", new Object[] {
	         "tp03", "Masthan", "Technical Writer" });
	      
	      empinfo.put( "5", new Object[] {
	         "tp04", "Satish", "Technical Writer" });
	      
	      empinfo.put( "6", new Object[] {
	         "tp05", "Krishna", "Technical Writer" });

	      //Iterate over data and write to sheet
	      Set < String > keyid = empinfo.keySet();
	      int rowid = 0;
	      
	      for (String key : keyid) {
	         row = spreadsheet.createRow(rowid++);
	         Object [] objectArr = empinfo.get(key);
	         int cellid = 0;
	         
	         for (Object obj : objectArr){
	            Cell cell = row.createCell(cellid++);
	            cell.setCellValue((String)obj);
	         }
	      }
	      //Write the workbook in file system
	      String filename = System.getProperty("user.dir")+"\\src\\test\\java\\com\\flk\\www\\qa\\testcases\\Writesheet.xlsx";
	      FileOutputStream out = new FileOutputStream(new File(filename));
	      
	      workbook.write(out);
	      out.close();
	      System.out.println("Writesheet.xlsx written successfully");
	 
	}

}