package com.github.ttwd80.november.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserRegistrationIT extends AbstractSeleniumIT {

	@Test
	void testGivenDatabaseIsEmptyWhenWebRootIsAccessedThenUrlIsSetupSetup() {
		webDriver.get(BASE_URL + "/");
		webDriverWait.until(ExpectedConditions.urlContains("setup/setup"));
		assertEquals(BASE_URL + "/setup/setup", webDriver.getCurrentUrl());
	}

	@Test
	void testGivenUserHasAccessedSetupSetupWhenPageIsViewedThenChangePasswordFieldIsPresent() {
		webDriver.get(BASE_URL + "/");
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
	}

	@Test
	void testGivenUserCanSeeThePasswordResetFormWhenUserSubmitsItThenItShouldWork() {
		webDriver.get(BASE_URL + "/");
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
		webDriver.findElement(By.id("admin-password")).sendKeys("xxx");
		webDriver.findElement(By.id("admin-password-confirm")).sendKeys("xxx");
		webDriver.findElement(By.id("button-submit")).click();
		webDriverWait.until(ExpectedConditions.urlContains("/login"));
	}

	@Test
	void testGivenPasswordCanBeChangedWhenUserTriesToLoginThenItShouldWork() {
		webDriver.get(BASE_URL + "/");
		webDriver.findElement(By.id("admin-password")).sendKeys("xxx");
		webDriver.findElement(By.id("admin-password-confirm")).sendKeys("xxx");
		webDriver.findElement(By.id("button-submit")).click();
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
		webDriver.findElement(By.name("username")).sendKeys("admin");
		webDriver.findElement(By.name("password")).sendKeys("xxx");
		webDriver.findElement(By.name("submit")).click();
		webDriverWait.until(ExpectedConditions.urlContains("admin/index"));
	}

}
