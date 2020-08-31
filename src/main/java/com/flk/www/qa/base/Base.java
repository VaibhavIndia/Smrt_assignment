package com.flk.www.qa.base;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.flk.www.qa.utils.TimeUtils;


public class Base {

	public static WebDriver driver;
	public static Properties prop;
		
	public Base()
	{
		try 
		{
			prop = new Properties();
			String filename = System.getProperty("user.dir")+"\\src\\main\\java\\com\\flk\\www\\qa\\config\\config.properties";
			FileInputStream fls = new FileInputStream(filename);
			prop.load(fls);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static void initialize()
	{
		String browserName = prop.getProperty("browser");
		System.out.println(browserName);
		if(browserName.equals("chrome"))
		{
			String chromedriverfilename = System.getProperty("user.dir")+"\\src\\main\\java\\com\\flk\\www\\qa\\drivers\\chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", chromedriverfilename);
			driver = new ChromeDriver();
		}
		else if(browserName.equals("firefox"))
		{
			String geckodriverfilename = System.getProperty("user.dir")+"\\src\\main\\java\\com\\flk\\www\\qa\\drivers\\geckodriver.exe";
			System.setProperty("webdriver.gecko.driver", geckodriverfilename);
			driver = new FirefoxDriver();
		}
		else
		{
			System.out.println("Invalid browser");
			System.exit(0);
		}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TimeUtils.page_load_timeout, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TimeUtils.page_implicit_timeout, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));
	}
}