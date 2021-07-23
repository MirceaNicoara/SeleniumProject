package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import org.openqa.selenium.NoSuchElementException;

public class UsersListPageTest {

    UsersListPage usersList;
    WebDriver driver;

    @BeforeTest
    public void initialSetUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:4200/users");
        usersList = new UsersListPage(driver);
    }
    @AfterTest
    public void closeDriver() {
        driver.close();
    }

    @Test
    public void checkMenuButtonPresence() {
        Assert.assertTrue(driver.findElement(By.xpath("/html/body/app-root/app-users/app-header/mat-toolbar/button[1]/span[1]/mat-icon")).isDisplayed());
    }
    @Test
    public void checkAddUserButtonPresence() {
        Assert.assertTrue(driver.findElement(By.xpath("/html/body/app-root/app-users/app-header/mat-toolbar/button[2]/span[1]/span")).isDisplayed());
    }
    @Test
    public void checkBackButtonPresence() {
        Assert.assertTrue(driver.findElement(By.xpath("/html/body/app-root/app-users/app-header/mat-toolbar/div/button/mat-icon")).isDisplayed());
    }
    @Test
    public void checkBackButtonFunction() {
        driver.findElement(By.xpath("/html/body/app-root/app-users/app-header/mat-toolbar/div/button/mat-icon")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:4200/");
    }
    @Test
    public void verifyUsersListNotVoid() {
        List<WebElement> usersList = driver.findElements(By.tagName("app-user-card"));
        Assert.assertNotNull(usersList);
    }

    @Test(priority = 9)
    public void deleteUser() {
        // getting list size for deleting last element on next step
        List<WebElement> usersList = driver.findElements(By.tagName("app-user-card"));
        int lastElementToBeDeleted = usersList.size();
        // clicking delete button for last element
        driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + lastElementToBeDeleted +"]/div/div[7]/button[2]")).click();
        // clicking confirmation button
        driver.findElement(By.xpath("/html/body/app-root/app-users/div[2]/app-deletemodal/div/div/button[2]/span[1]")).click();
        // saving presence of deleted element in boolean primitive
        boolean deletedElement;
        try{
            driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" +
                    lastElementToBeDeleted + "]/div/div[7]/button[2]"));
            deletedElement = true;
        } catch (NoSuchElementException nsee) {
            deletedElement = false;
        }
        // checking presence == false
        Assert.assertEquals(deletedElement, false);

    }

}
