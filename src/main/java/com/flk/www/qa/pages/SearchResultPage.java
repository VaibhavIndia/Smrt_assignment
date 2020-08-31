package com.flk.www.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.flk.www.qa.base.Base;


public class SearchResultPage extends Base{
	
	public SearchResultPage() 
	{
		PageFactory.initElements(driver, this);
		
	}

	
	//Obeject repo..
	@FindBy(xpath = "(//select[@class='fPjUPw'])[1]")
	WebElement minPriceRangeElement;
	
	@FindBy(xpath = "(//select[@class='fPjUPw'])[2]")
	WebElement maxPriceRangeElement;
	
	@FindBy(xpath = "//span[contains(text(),'Page 1 of')]")
	WebElement pagesCountElement;
	
	
	@FindBy(xpath = "//div[text()='Processor Brand']")
	WebElement processorBrandElement;
	
	//Actions
	
	
	public WebElement returnMinPriceddl()
	{
		return minPriceRangeElement;
	}
	
	public WebElement returnmaxPriceddl() {
	
		return maxPriceRangeElement;
	}

	public WebElement returnProcessorheading() {
		return processorBrandElement;
		
		
	}
	public WebElement createAndRetrivRameElement(String ramvalueString)
	{
		String ramString = "//div[text()='RAM']//parent::div//following-sibling::div/div/div[@title='" + ramvalueString + " GB']/div/div/label/input";
		return driver.findElement(By.xpath(ramString));
	}

	public WebElement creatandretriveprocessorElement(String processorvaluestring)
	{
		String processorString = "//div[text()='Processor Brand']//parent::div//following-sibling::div/div/div[@title='" + processorvaluestring + "']/div/div/label/input";
		return driver.findElement(By.xpath(processorString));
	}
	
	//div[text()='Processor Brand']//parent::div//following-sibling::div/div/div[@title='Snapdragon']/div/div/label/input
	
	public int returnpagecount()
	{
		String wholeString = pagesCountElement.getText();
		
		String[] breakStrings = wholeString.split(" ");
		
		return Integer.parseInt(breakStrings[3]);
	}
}
