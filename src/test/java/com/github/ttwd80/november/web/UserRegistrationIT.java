package com.github.ttwd80.november.web;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class UserRegistrationIT extends AbstractSeleniumIT {

	String BASE_URL = "http://localhost:58080";

	@PersistenceContext
	EntityManager entityManager;

	@BeforeEach
	void setUp() throws Exception {
		entityManager.createQuery("delete from UserRole").executeUpdate();
		entityManager.createQuery("delete from User").executeUpdate();
		entityManager.createQuery("delete from Role").executeUpdate();
	}

	@Test
	void test() {
		WebDriver webDriver = new ChromeDriver();
		webDriver.get(BASE_URL + "/");
		webDriver.findElement(By.id("admin-password")).sendKeys("xxx");
		webDriver.findElement(By.id("admin-password-confirm")).sendKeys("xxx");
		webDriver.findElement(By.id("button-submit")).click();
		WebDriverWait waitForLoginForm = new WebDriverWait(webDriver, 10_000);
		waitForLoginForm.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
		webDriver.findElement(By.name("username")).sendKeys("admin");
		webDriver.findElement(By.name("password")).sendKeys("xxx");
		webDriver.findElement(By.name("submit")).click();
		waitForLoginForm.until(ExpectedConditions.urlContains("admin/index"));
	}

}
