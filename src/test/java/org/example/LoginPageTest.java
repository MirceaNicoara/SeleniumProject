package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class LoginPageTest {
    LoginPage loginPage;
    WebDriver driver;

    @BeforeTest
    public void initialSetUp() {
        driver = new FirefoxDriver();
        driver.get("http://localhost:4200");
        loginPage = new LoginPage(driver);
    }
    @AfterTest
    public void closeDriver() {
        driver.close();
    }


    @Test(priority = 1)
    public void verifyHomepageTitle() {
        String expectedTitle = "Frontend";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @Test
    public void countInputElements() {
        List<WebElement> loginPageInputElements = driver.findElements(By.tagName("input"));
        int expectedInputElements = 2;
        int actualInputElements = loginPageInputElements.size();
        Assert.assertEquals(actualInputElements, expectedInputElements);
    }

    @Test
    public void verifyIconPresence() {
        Assert.assertTrue(loginPage.getKeylikeIconElement().isDisplayed());
    }

    @Test
    public void checkLoginButtonPresence() {
        Assert.assertEquals("Login", loginPage.getLoginButtonElement().getAttribute("innerHTML"));
    }

    @Test
    public void checkContrastLoginButtonWhenHover() {
        Actions builder = new Actions(driver);
        Action loginButtonHover = builder
                .moveToElement(loginPage.getLoginButtonElement())
                .build();
        loginButtonHover.perform();
        String bacgroundColor = driver.findElement(By.tagName("button")).getCssValue("background-color");
        String textColor = driver.findElement(By.tagName("button")).getCssValue("color");
        Assert.assertTrue(bacgroundColor != textColor);
    }

    @Test(priority = 8)
    public void verifyLoginBlankImpossible() {;
        loginPage.getLoginButtonElement().click();
        String newUrl = driver.getCurrentUrl();
        Assert.assertEquals(newUrl, loginPage.getBaseUrl());
    }

    @Test(priority = 9)
    public void verifyLoginSpaceKeyInputImpossible() {
        driver.get(loginPage.getBaseUrl());
        loginPage.getUsernameElement().sendKeys("      ");
        loginPage.getPasswordElement().sendKeys("      ");
        loginPage.getLoginButtonElement().click();
        String newUrl = driver.getCurrentUrl();
        Assert.assertEquals(newUrl, loginPage.getBaseUrl());
    }

}
