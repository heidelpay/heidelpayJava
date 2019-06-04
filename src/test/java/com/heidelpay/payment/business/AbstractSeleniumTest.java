package com.heidelpay.payment.business;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Currency;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.heidelpay.payment.Paypage;

public class AbstractSeleniumTest extends AbstractPaymentTest {

	public enum Browser {Chrome, Firefox}; 
	private Browser defaultBrowser = Browser.Firefox;
	private boolean close = true;

	public AbstractSeleniumTest() {
	}


	private RemoteWebDriver driver;

	protected void initializeDriver(Browser browser) {
		if(browser == Browser.Chrome) {
			System.setProperty("webdriver.chrome.driver", getChromedriverPath());
			driver = new ChromeDriver();
		} else {
			System.setProperty("webdriver.gecko.driver", getGeckodriverPath());
			driver = new FirefoxDriver();
		}
	}
	
	protected RemoteWebDriver getDriver(Browser browser) {
		initializeDriver(browser);
		return driver;
	}

	protected RemoteWebDriver getDriver() {
		if (driver == null) {
			initializeDriver(defaultBrowser);
		}
		return driver;
	}

	protected RemoteWebDriver openUrl(String url, Browser browser) {
		getDriver(browser).get(url);
		return getDriver();
	}

	protected RemoteWebDriver openUrl(String url) {
		getDriver().get(url);
		return getDriver();
	}
	
	private String getGeckodriverPath () {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win")) {
			return "selenium/geckodriver/geckodriver.exe";
		} else if (os.contains("mac")) {
			return "selenium/geckodriver/geckodriver";
		} else {
			return null;
		}
	}
	private String getChromedriverPath () {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win")) {
			return "selenium/chrome/chromedriver.exe";
		} else if (os.contains("mac")) {
			return "selenium/chrome/chromedriver";
		} else {
			return null;
		}
	}
		

	protected void pay(WebDriver driver, String expectedUrl) {
		WebElement pay = driver.findElement(By.xpath("//*[contains(text(), 'Pay € 1.00')]"));
		pay.click();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.urlContains(expectedUrl));
		assertContains(expectedUrl, driver.getCurrentUrl());		
	}

	protected void sendDataFrameById(RemoteWebDriver driver, String frameName, String id, String value) {
		WebElement iFrame = driver.findElement(By.xpath("//*[contains(@id, '" + frameName + "')]"));
		TargetLocator iFrameList = driver.switchTo();
		WebDriver iFrameDriver = iFrameList.frame(iFrame);
		sendDataById(iFrameDriver, id, value);
		driver.switchTo().defaultContent();
	}
	
	private void assertContains(String expectedUrl, String currentUrl) {
		assertTrue(currentUrl.contains(expectedUrl));
	}

	protected void sendDataByName(WebDriver driver, String name, String data) {
		WebElement element = driver.findElement(By.name(name));
		element.sendKeys(data);
	}

	protected void sendDataById(WebDriver driver, String id, String data) {
		WebElement element = driver.findElement(By.id(id));
		element.sendKeys(data);
	}

	protected void choosePaymentMethod(WebDriver driver, String id) {
		WebElement sdd = driver.findElement(By.id(id));
		assertNotNull(sdd);
		sdd.click();
	}

	// TODO: QUESTION: Why is returnUrl mandatory. In case of embedded Paypage it may not be needed
	protected Paypage getMinimumPaypage() throws MalformedURLException {
		Paypage paypage = new Paypage();
		paypage.setAmount(BigDecimal.ONE);
		paypage.setCurrency(Currency.getInstance("EUR"));
		paypage.setReturnUrl(new URL(getReturnUrl()));
		return paypage;
	}

	// TODO: Currently not possible
	protected Paypage getPaypage3DS() throws MalformedURLException {
		Paypage paypage = new Paypage();
		paypage.setAmount(BigDecimal.ONE);
		paypage.setCurrency(Currency.getInstance("EUR"));
		paypage.setReturnUrl(new URL(getReturnUrl()));
		return paypage;
	}

	protected String getReturnUrl() {
		return "https://www.google.at/";
	}
	
	
	
	protected void close() {
		if (driver != null && close) driver.quit();
	}

	protected void assertUIElement(String url, String id, String value) {
		RemoteWebDriver driver = openUrl(url);
		assertEquals(value, driver.findElementById(id).getText());
	}

	protected void assertNotExistent(RemoteWebDriver driver, By by) {
		try {
			driver.findElement(by);
			fail("Element with name '" + by + "' found");
		} catch (NoSuchElementException e) {
			; // That is what we expect
		}
	}

	protected Map<String, String> getFormParameterMap(String parReq, String termUrl, String md) {
		Map<String, String> formParameterMap = new LinkedHashMap<String, String>();
		formParameterMap.put("PaReq", parReq);
		formParameterMap.put("TermUrl", termUrl);
		formParameterMap.put("MD", md);
		return formParameterMap;
	}



}
