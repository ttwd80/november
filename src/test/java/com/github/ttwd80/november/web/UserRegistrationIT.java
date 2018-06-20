package com.github.ttwd80.november.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.ttwd80.november.model.repository.UserRepository;

@ExtendWith(SpringExtension.class)
class UserRegistrationIT extends AbstractSeleniumIT {

	@Autowired
	UserRepository userRepository;

	@Test
	void testGivenDatabaseIsEmptyWhenWebRootIsAccessedThenUrlIsSetupSetup() {
		assertEquals(0L, userRepository.count());
		webDriver.get(BASE_URL + "/");
		try {
			webDriverWait.until(ExpectedConditions.urlContains("setup/setup"));
		} catch (TimeoutException e) {
			assertEquals(BASE_URL + "/setup/setup", webDriver.getCurrentUrl());
		}
	}

	@Test
	void testGivenUserHasAccessedSetupSetupWhenPageIsViewedThenChangePasswordFieldIsPresent() {
		webDriver.get(BASE_URL + "/");
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
	}

	@Test
	void testGivenUserCanSeeThePasswordResetFormWhenUserSubmitsItThenItShouldWork() {
		webDriver.get(BASE_URL + "/");
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("admin-password")));
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
