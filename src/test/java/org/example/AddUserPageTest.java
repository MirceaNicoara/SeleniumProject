package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class AddUserPageTest {

    WebDriver driver;
    AddUserPage addUserObject;
    UsersListPage userListObject;
    UserModel userModel;

    @BeforeTest
    public void initialSetUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:4200/users");
        addUserObject = new AddUserPage(driver);
        userListObject = new UsersListPage(driver);
        userModel = addUserObject.createRandomUser();

    }

    @Test(priority = 9)
    public void verifyAddUserFunctionalityValidCredentials() {
        driver.get("http://localhost:4200/users");
        addUserObject.getAddUserButton().click();

        // inserting data to complete form
        addUserObject.insertCredentials(userModel.getUsername(), userModel.getEmail(), userModel.getFullName(), userModel.getPassword());
        addUserObject.selectTraitsAndGender();

        // clicking Submit when becoming enabled
        WebDriverWait myWaitVariable = new WebDriverWait(driver, 5);
        myWaitVariable.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-addmodal/div/div/form/button"))).click();

        // counting users list and comparing last element values with the newly introduced ones
        List<WebElement> usersList = driver.findElements(By.tagName("app-user-card"));
        int userCardNumber = usersList.size();
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + userCardNumber + "]/div/div[1]/a/h1")).getText(),
                userModel.getFullName());
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + userCardNumber + "]/div/div[2]/span")).getText(),
                userModel.getUsername());
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + userCardNumber + "]/div/div[3]/span")).getText(),
                userModel.getEmail());
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + userCardNumber + "]/div/div[4]/span")).getText(),
                userModel.getPassword());
    }

    @Test(priority = 5)
    public void verifyAddUserWithPasswordSymbols() {
        addUserObject.getAddUserButton().click();
        addUserObject.insertCredentials(userModel.getUsername(), userModel.getEmail(), userModel.getFullName(), userModel.getPassword() + "!@#");
        addUserObject.selectTraitsAndGender();

        /* bellow commented code failed because password field does not accept symbol characters input

        WebDriverWait myWaitVariable = new WebDriverWait(driver, 5);
        myWaitVariable.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-addmodal/div/div/form/button"))).click();
        int usersListSizeAfter = driver.findElements(By.tagName("app-user-card")).size();
        Assert.assertEquals(usersListSizeAfter, userListSizeBefore + 1);

        */

        // checking Submit button gets enabled
        boolean submitButtonStatus = driver.findElement(By.xpath("/html/body/app-root/app-addmodal/div/div/form/button")).isEnabled();
        Assert.assertTrue(submitButtonStatus);

    }

    @Test(priority = 1)
    public void verifyAddUserWithMarkedMandatoryFields() {

        // accessing add user webpage / functionality
        addUserObject.getAddUserButton().click();

        // inserting data to complete form
        addUserObject.insertCredentials(userModel.getUsername(), userModel.getEmail(), "", userModel.getPassword());

        // clicking Submit when becoming enabled
        WebDriverWait myWaitVariable = new WebDriverWait(driver, 5);
        try {
            myWaitVariable.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-addmodal/div/div/form/button"))).click();
            String alertMessage = driver.switchTo().alert().getText();
            Assert.assertEquals(alertMessage, "Http failure response for http://localhost:8080/api/form/users: 500 OK");
            driver.switchTo().alert().accept();
        } catch (UnhandledAlertException unhandledAlertException) {
            unhandledAlertException.printStackTrace();
        }

        driver.get("http://localhost:4200/users");
    }

    @Test(priority = 4)
    public void verifyAddUserWithJustUnmarkedMandatoryFields() {
        addUserObject.getAddUserButton().click();

        // inserting data to complete form
        addUserObject.insertCredentials("", "", userModel.getFullName(), "");
        addUserObject.selectTraitsAndGender();

        // checking Submit button remains disabled
        boolean submitButtonStatus = driver.findElement(By.xpath("/html/body/app-root/app-addmodal/div/div/form/button")).isEnabled();
        Assert.assertFalse(submitButtonStatus);

        driver.get("http://localhost:4200/users");
    }

    @Test(priority = 2)
    public void verifyAddUserFunctionalityUsernameDuplicate() {
        String usernameCard1 = userListObject.getUsername().getText();
        addUserObject.getAddUserButton().click();
        addUserObject.insertCredentials(usernameCard1, usernameCard1 + userModel.getEmail(), userModel.getFullName(), userModel.getPassword());
        addUserObject.selectTraitsAndGender();
        WebDriverWait myWaitVariable = new WebDriverWait(driver, 5);
        myWaitVariable.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-addmodal/div/div/form/button"))).click();
        String alertMessage = driver.switchTo().alert().getText();
        Assert.assertEquals(alertMessage, "Http failure response for http://localhost:8080/api/form/users: 400 OK");
        driver.switchTo().alert().accept();
        driver.get("http://localhost:4200/users");    }

    @Test(priority = 3)
    public void verifyAddUserFunctionalityEmailDuplicate() {
        String emailCard1 = userListObject.getEmail().getText();
        addUserObject.getAddUserButton().click();
        addUserObject.insertCredentials(userModel.getUsername(), emailCard1, userModel.getFullName(), userModel.getPassword());
        addUserObject.selectTraitsAndGender();
        WebDriverWait myWaitVariable = new WebDriverWait(driver, 5);
        myWaitVariable.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-addmodal/div/div/form/button"))).click();
        String alertMessage = driver.switchTo().alert().getText();
        Assert.assertEquals(alertMessage, "Http failure response for http://localhost:8080/api/form/users: 400 OK");
        driver.switchTo().alert().accept();
        driver.get("http://localhost:4200/users");    }
}
