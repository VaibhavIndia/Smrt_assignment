package com.flk.www.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.flk.www.qa.base.Base;

public class HomePage extends Base {

	public HomePage()
	{
		PageFactory.initElements(driver, this);
	}
	
	//Obeject repo..
	
	//SearchBox
	@FindBy (xpath="//input[@title='Search for products, brands and more']")
	WebElement searchElement;
	
	//Actions
	
	//Enter Text in searchBox
	
	public WebElement returnsearchboxElement()
	{
		
		System.out.println("HI");
		return searchElement;
	}
	
	

	public WebElement returnprocessorLabElement (String processorName)
	{
		String xapthforProcessorString = "//div[text()='Processor Brand']//parent::div//following-sibling::div/div/div[@title='" + processorName + "']";
		return driver.findElement(By.xpath(xapthforProcessorString));
		
	}
}
