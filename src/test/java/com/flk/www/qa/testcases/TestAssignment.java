package com.flk.www.qa.testcases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.security.DrbgParameters.NextBytes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.naming.directory.SearchResult;
import javax.sound.sampled.LineListener;import org.apache.commons.codec.language.bm.Rule.RPattern;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.ss.formula.ptg.StringPtg;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.ant.taskdefs.MacroDef.Text;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.ClickAction;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.flk.www.qa.base.Base;
import com.flk.www.qa.pages.HomePage;
import com.flk.www.qa.pages.SearchResultPage;
import com.flk.www.qa.utils.VaibhavExcelUtility;

public class TestAssignment  extends Base{
	
	HomePage homePage;
	SearchResultPage searchResultPage;
	
	
	public TestAssignment() 
	{
		super();
	}
	
	
	@BeforeMethod
	public void setUp() 
	{
		initialize();
		homePage = new HomePage();
		searchResultPage = new SearchResultPage();     
	}
	
	@Test
	public void Testdata() throws Exception
	{
		
		// initializa action class
		Actions actions = new Actions(driver);
		
		
		// Get Url from properties file  and go to url 
		String urlString = prop.getProperty("url");
		driver.get(urlString);
		
		
		// Close the popup of login		
		actions.sendKeys(Keys.ESCAPE).build().perform();
		
		
		
		// Grab the name of excel file to write data and initialize outputstream
		String filename = System.getProperty("user.dir") + prop.getProperty("writedatafilename");
		FileOutputStream out = new FileOutputStream(new File(filename));
		
		// Grab the testdata file name from property file and using that file name use
		// our library to grab the data from excel file for search Keys and enter the same in search box
		String filenameString =  System.getProperty("user.dir") + prop.getProperty("testdatafilename");		
		File filenameFile  = new File(filenameString);
		ArrayList<String> searchStrings = VaibhavExcelUtility.getdata(filenameFile, "Sheet1", "Testcases", "searchkey");		
		String searchKey =  searchStrings.get(1);
		//driver.findElement(By.xpath("//input[@title='Search for products, brands and more']")).sendKeys("samsung mobiles");
		homePage.returnsearchboxElement().sendKeys(searchKey);
		
		
		//driver.findElement(By.xpath("//input[@title='Search for products, brands and more']")).sendKeys("samsung mobiles");
		actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
		

		ArrayList<String> minpricevalue = VaibhavExcelUtility.getdata(filenameFile, "Sheet1", "Testcases", "minpricevalue");
		String minpricevalueString =  minpricevalue.get(1);
		Select minPriceRangeSelect = new Select(searchResultPage.returnMinPriceddl());
		minPriceRangeSelect.selectByValue(minpricevalueString);
		
		
		ArrayList<String> maxpricevalue = VaibhavExcelUtility.getdata(filenameFile, "Sheet1", "Testcases", "maxpricevalue");
		String maxpricevalueString =  maxpricevalue.get(1);
		Select maxPriceRangeSelect = new Select(searchResultPage.returnmaxPriceddl());
		maxPriceRangeSelect.selectByValue(maxpricevalueString);
		
		Thread.sleep(2000);
		
		// get the Ram from excel file
		ArrayList<String> ramvalue = VaibhavExcelUtility.getdata(filenameFile, "Sheet1", "Testcases", "ram");
		String ramvalueString =  ramvalue.get(1);
		WebElement ramElement = searchResultPage.createAndRetrivRameElement(ramvalueString);
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		//ramElement.click();
		executor.executeScript("arguments[0].click();", ramElement);
		
		
		Thread.sleep(2000);
		// Get processor brand from excel
		ArrayList<String> processorvalue = VaibhavExcelUtility.getdata(filenameFile, "Sheet1", "Testcases", "processor");
		String processorName =  processorvalue.get(1);		
		WebElement processorHeadingElement = searchResultPage.returnProcessorheading();
		//processorHeadingElement.click();		
		executor.executeScript("arguments[0].click();", processorHeadingElement);
		WebElement processorElement = searchResultPage.creatandretriveprocessorElement(processorName);
		executor.executeScript("arguments[0].click();", processorElement);
		
		
		Thread.sleep(20000);
		
		XSSFWorkbook workbook = new XSSFWorkbook();
	      
	    //Create a blank sheet
	    XSSFSheet spreadsheet = workbook.createSheet("Product Info ");

	    //Create row object
	    XSSFRow row;
	    int productCount = 0;
		
	  //This data needs to be written (Object[])
	    Map < String, Object[] > empinfo = new TreeMap < String, Object[] >();
	    empinfo.put( "1", new Object[] {"Sr. No.", "Product Name", "Price Name" });
	    
		do
		{
			List<WebElement> allProductNameOnPageElement = driver.findElements(By.xpath("//div[@class='bhgxx2 col-12-12']/div/div/div/a/div[2]/div/div[@class='_3wU53n']"));
			List<WebElement> allProductpriceOnPageElement = driver.findElements(By.xpath("//div[@class='bhgxx2 col-12-12']/div/div/div/a/div[2]/div[2]/div/div/div[1]")); 
			
			int Elementsonpgae = allProductpriceOnPageElement.size();
			System.out.println(Elementsonpgae + " Elements on page");
			for(int j=0; j<Elementsonpgae;j++)
			{
				System.out.println(productCount +" product id");
				productCount++;
				empinfo.put( Integer.toString(productCount+1), new Object[] {Integer.toString(productCount), allProductNameOnPageElement.get(j).getText(), allProductpriceOnPageElement.get(j).getText() });
				System.out.println(empinfo);
			}
			
			if((driver.findElements(By.xpath("//span[text()='Next']"))).size()>0)
			{
				driver.findElement(By.xpath("//span[text()='Next']")).click();
				continue;
			}
			else {
				break;
			}
			
				
			}
		while(driver.findElements(By.xpath("//span[text()='Previous']")).size() >0);
			
		
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

	     
	      
	      workbook.write(out);
	      out.close();
	      System.out.println("Writesheet.xlsx written successfully");		
		
	}
}
