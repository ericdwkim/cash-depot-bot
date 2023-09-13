package com.txb.cashdepot;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;


public class CashDepotReportPull {
	
	//used for testing
	static boolean isTest = false;
	static String testLabel = "TEST - ";
	
	//must adjust dlFolder depending on where the report will download (e.g. ekuriakose vs ekuriakosea)

	static String dlFolder = "/Users/ekim/Downloads/";

	//network folder where PDI pulls in report
	static String destFolder = "\\\\PDIPRODSQL\\enterprisedata\\Imports\\Daily Paperwork\\ATM\\";
	static String destFolderArchive = destFolder+"Bot Archive\\";
	static String fileName = "Daily Summary - ASCII";	
	static String fileName1 = testLabel+fileName;
	
	static String yesterday;
	static int daysBack = -2;
	//used to create version of jar that allows custom date picking, should be false for default yesterday pull
	static boolean isCustom = false;
	static InputStream stream = System.in;
	static Scanner scnr = new Scanner(stream);
	static String driverPath = "/opt/homebrew/bin/"; // chromedriver ver 116 on M1
//	static String driverPath = "C:/Users/ekima/AppData/Local/anaconda3/envs/bots/Lib/site-packages/seleniumbase/drivers/"; // chromedriver ver 116 on windows

	//cashdepot site and account info
	static WebsiteCredentials cashdepot = new WebsiteCredentials("https://webmon.cashdepotplus.com/spsadmin/addins/Reports/rptIndex.aspx","webmon0764","NEkwikchek");

	public static void main(String[] args) throws InterruptedException 
	{
		
		if(!isTest)
		{
			//user = "ekuriakosea";
			//dlFolder = "C:/Users/"+user+"/Downloads/";
			testLabel = "";
			fileName1 = testLabel+fileName;
			//driverPath = "";
		}
		
		if(isCustom)
		{
			System.out.print("How many days back? (e.g. -2, -3, etc): ");
			daysBack = scnr.nextInt();
		}			
		
		yesterday = getYest(1);

		
		System.setProperty("webdriver.chrome.driver", driverPath+"chromedriver");
		
				
		WebDriver driver = new ChromeDriver();
		driver.get(cashdepot.getWebsite());
		
		logIn(driver, cashdepot);		
		
		getDailySum(driver);
		
		driver.get(cashdepot.getWebsite());		
			
		Thread.sleep(5*1000);
		
		driver.quit();
		
		try {
			convertReport();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("\n\nDone!");
	}
	
	//returns "yesterday's" date in requested format
	public static String getYest(int format)
	{
		LocalDateTime yest = LocalDateTime.now().plusDays(daysBack);
		DateTimeFormatter f1 = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.ENGLISH);
		DateTimeFormatter f2 = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.ENGLISH);
		DateTimeFormatter f3 = DateTimeFormatter.ofPattern("MMddyyyy", Locale.ENGLISH);
		
		LocalDateTime yestMinusOne = LocalDateTime.now().plusDays(daysBack-1); //gives date as the day before "yesterday" to compensate for PDI lockout issues
		
		switch(format) {
			case 1: 
				return f1.format(yest);
			case 2:
				return f2.format(yest);
			case 3:
				return f3.format(yest);
			case 4:
				return f3.format(yestMinusOne);
			default:
				return "Invalid Format Selection";
		}		
	}
	
	public static void logIn(WebDriver driver, WebsiteCredentials cd )
	{
		driver.findElement(By.id("txtLogin")).sendKeys(cd.getUsername());
		
		driver.findElement(By.id("txtPassword")).sendKeys(cd.getPw());
		
		driver.findElement(By.id("cmdGo")).click();
	}
	
	public static void getDailySum(WebDriver driver)
	{
		driver.findElement(By.cssSelector("a[href=\"javascript:__doPostBack('ctl33','')\"]")).click();		
		driver.findElement(By.id("SettDate_dtp")).clear();
		driver.findElement(By.id("SettDate_dtp")).sendKeys(yesterday);
		driver.findElement(By.id("rblOptions_1")).click();
		driver.findElement(By.cssSelector("#bGetReport")).click();
	}
	
	//edits report to correct format and moves files into correct positions 
	public static void convertReport() throws java.io.IOException
	{
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(destFolderArchive+fileName1+"-"+getYest(3)+".csv"));
			BufferedReader br = new BufferedReader(new FileReader(dlFolder+fileName+".txt"));
			
			String s;
			
			//copy downloaded txt file to csv file while appending the date 
			while((s = br.readLine()) != null)
			{
				bw.write(s+","+getYest(2)+"\n");				
			}
			br.close();
			bw.close();
		}catch(Exception ex) {
			return;
		}
		
		File orig = new File(dlFolder+fileName+".txt");
		File  dest = new File(destFolder+fileName1+".csv");
		//archived report from 2 days ago 
		File src = new File(destFolderArchive+fileName1+"-"+getYest(3)+".csv");
		System.out.print(getYest(4));
		
		//copies src file to dest overwriting the report that was previously there, except if requesting custom date report
		if(!isCustom)
			Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		orig.delete();
	}
}
