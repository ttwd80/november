package com.github.ttwd80.november.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.ttwd80.november.model.repository.AbstractRepositoryIT;

public class AbstractSeleniumIT extends AbstractRepositoryIT {

	protected String BASE_URL = "http://localhost:58080";

	protected WebDriver webDriver;

	protected Wait<WebDriver> webDriverWait;

	@BeforeEach
	protected void setUp() throws Exception {
		super.setUp();
		webDriver = new FirefoxDriver();
		webDriverWait = new WebDriverWait(webDriver, 60 * 10);
	}

	@AfterEach
	protected void tearDown() throws Exception {
		webDriver.quit();
	}

}
