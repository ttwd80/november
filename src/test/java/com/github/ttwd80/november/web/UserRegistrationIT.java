package com.github.ttwd80.november.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
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
		webDriverWait.until(ExpectedConditions.urlContains("setup/setup"));
	}

	@Test
	void testGivenUserHasAccessedSetupSetupWhenPageIsViewedThenChangePasswordFieldIsPresent() {
		assertEquals(0L, userRepository.count());
		webDriver.get(BASE_URL + "/");
		webDriverWait.until(ExpectedConditions.urlContains("setup/setup"));
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("admin-password")));
	}

	@Test
	void testGivenUserCanSeeThePasswordResetFormWhenUserSubmitsItThenItShouldWork() {
		assertEquals(0L, userRepository.count());
		webDriver.get(BASE_URL + "/");
		webDriverWait.until(ExpectedConditions.urlContains("setup/setup"));
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("admin-password")));
		webDriver.findElement(By.id("admin-password")).sendKeys("xxx");
		webDriver.findElement(By.id("admin-password-confirm")).sendKeys("xxx");
		webDriver.findElement(By.id("button-submit")).click();
		webDriverWait.until(ExpectedConditions.urlContains("/login"));
	}

	@Test
	void testGivenPasswordCanBeChangedWhenUserTriesToLoginThenItShouldWork() {
		assertEquals(0L, userRepository.count());
		webDriver.get(BASE_URL + "/");
		webDriverWait.until(ExpectedConditions.urlContains("setup/setup"));
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("admin-password")));
		webDriver.findElement(By.id("admin-password")).sendKeys("xxx");
		webDriver.findElement(By.id("admin-password-confirm")).sendKeys("xxx");
		webDriver.findElement(By.id("button-submit")).click();
		webDriverWait.until(ExpectedConditions.urlContains("/login"));
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
		webDriver.findElement(By.name("username")).sendKeys("admin");
		webDriver.findElement(By.name("password")).sendKeys("xxx");
		webDriver.findElement(By.name("submit")).click();
		webDriverWait.until(ExpectedConditions.urlContains("admin/index"));
	}

}
