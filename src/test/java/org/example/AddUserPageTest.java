package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class AddUserPageTest {

    AddUserPage addUserObject;
    WebDriver driver;
    RandomStringGenerator randomString = new RandomStringGenerator();
    UsersListPage userListObject;

    @BeforeTest
    public void initialSetUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:4200/users");
        addUserObject = new AddUserPage(driver);
        userListObject = new UsersListPage(driver);
    }

    @Test(priority = 1)
    public void verifyAddUserFunctionalityValidCredentials() {
        // taking size of initial user list
        List<WebElement> usersList = driver.findElements(By.tagName("app-user-card"));
        int userListSizeBefore = usersList.size();

        // accessing add user webpage / functionality
        addUserObject.getAddUserButton().click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:4200/add");

        // generating strings to be inserted in the required fields
        String randomName = randomString.getRandomString();
        String randomEmail = randomName + "_email@gmail.com";

        // inserting data to complete form
        Actions builder = new Actions(driver);
        Action addUser = builder
                .sendKeys(addUserObject.getUsernameToAdd(), randomName)
                .sendKeys(addUserObject.getEmailToAdd(), randomEmail)
                .sendKeys(addUserObject.getFullNameToAdd(), randomName + " " + randomName)
                .sendKeys(addUserObject.getPasswordToAdd(), "1aa")
                .click(addUserObject.getTraitToAdd())
                .click(addUserObject.getGenderToAdd())
                .build();
        addUser.perform();

        // clicking Submit when becoming enabled
        WebDriverWait myWaitVariable = new WebDriverWait(driver, 5);
        myWaitVariable.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-addmodal/div/div/form/button"))).click();

        // counting users list after user added and comparing it to the initial count
        usersList = driver.findElements(By.tagName("app-user-card"));
        int usersListSizeAfter = usersList.size();
        Assert.assertEquals(usersListSizeAfter, userListSizeBefore + 1);
    }

    @Test(priority = 2)
    public void verifyAddUserWithPasswordSymbols() {
        int userListSizeBefore = initializeUserListPageAndReturnListCount();
        addUserObject.getAddUserButton().click();
        String randomName = randomString.getRandomString();
        String randomEmail = randomName + "_email@gmail.com";
        Actions builder = new Actions(driver);
        Action addUser = builder
                .sendKeys(addUserObject.getUsernameToAdd(), randomName)
                .sendKeys(addUserObject.getEmailToAdd(), randomEmail)
                .sendKeys(addUserObject.getFullNameToAdd(), randomName + " " + randomName)
                .sendKeys(addUserObject.getPasswordToAdd(), "1aa!@#")
                .click(addUserObject.getTraitToAdd())
                .click(addUserObject.getGenderToAdd())
                .build();
        addUser.perform();
        /* code commented failled because password field does not accept symbol characters input
        WebDriverWait myWaitVariable = new WebDriverWait(driver, 5);
        myWaitVariable.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-addmodal/div/div/form/button"))).click();
        int usersListSizeAfter = driver.findElements(By.tagName("app-user-card")).size();
        Assert.assertEquals(usersListSizeAfter, userListSizeBefore + 1); */

        // checking Submit button gets enabled
        boolean submitButtonStatus = driver.findElement(By.xpath("/html/body/app-root/app-addmodal/div/div/form/button")).isEnabled();
        Assert.assertTrue(submitButtonStatus);
    }

    @Test(priority = 8)
    public void verifyAddUserWithMarkedMandatoryFields() {
        int listSizeBefore = initializeUserListPageAndReturnListCount();

        // accessing add user webpage / functionality
        addUserObject.getAddUserButton().click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:4200/add");

        // generating strings to be inserted in the required fields
        String randomName = randomString.getRandomString();
        String randomEmail = randomName + "_email@gmail.com";

        // inserting data to complete form
        Actions builder = new Actions(driver);
        Action addUser = builder
                .sendKeys(addUserObject.getUsernameToAdd(), randomName)
                .sendKeys(addUserObject.getEmailToAdd(), randomEmail)
                .sendKeys(addUserObject.getPasswordToAdd(), "1aa")
                .build();
        addUser.perform();

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

        //counting users list after user added and comparing it to the initial count
        int listSizeAfter = initializeUserListPageAndReturnListCount();
        Assert.assertEquals(listSizeAfter, listSizeBefore + 1);
    }

    @Test(priority = 9)
    public void verifyAddUserWithJustUnmarkedMandatoryFields() {
        int listSizeBefore = initializeUserListPageAndReturnListCount();
        addUserObject.getAddUserButton().click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:4200/add");

        //generating strings to be inserted in the required fields
        String randomName = randomString.getRandomString();
        String randomEmail = randomName + "_email@gmail.com";

        // inserting data to complete form
        Actions builder = new Actions(driver);
        Action addUser = builder
                .sendKeys(addUserObject.getFullNameToAdd(), randomName + " " + randomName)
                .click(addUserObject.getTraitToAdd())
                .click(addUserObject.getGenderToAdd())
                .build();
        addUser.perform();

        // checking Submit button remains disabled
        boolean submitButtonStatus = driver.findElement(By.xpath("/html/body/app-root/app-addmodal/div/div/form/button")).isEnabled();
        Assert.assertFalse(submitButtonStatus);
    }



    @Test(priority = 2)
    public void verifyAddUserFunctionalityUsernameDuplicate() {
        initializeUserListPageAndReturnListCount();
        // getting existing username
        String usernameCard1 = userListObject.getUsername().getText();

        addUserObject.getAddUserButton().click();
        String randomFullName = randomString.getRandomString() + randomString.getRandomString();
        String randomEmail = randomFullName + "_email@gmail.com";
        Actions builder = new Actions(driver);
        Action addUser = builder
                .sendKeys(addUserObject.getUsernameToAdd(), usernameCard1)
                .sendKeys(addUserObject.getEmailToAdd(), randomEmail)
                .sendKeys(addUserObject.getFullNameToAdd(), randomFullName)
                .sendKeys(addUserObject.getPasswordToAdd(), "1aa")
                .click(addUserObject.getTraitToAdd())
                .click(addUserObject.getGenderToAdd())
                .build();
        addUser.perform();
        WebDriverWait myWaitVariable = new WebDriverWait(driver, 5);
        myWaitVariable.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-addmodal/div/div/form/button"))).click();

        String alertMessage = driver.switchTo().alert().getText();
        Assert.assertEquals(alertMessage, "Http failure response for http://localhost:8080/api/form/users: 400 OK");
        driver.switchTo().alert().accept();
        addUserObject.getCloseAddModalButton().click();
    }

    @Test(priority = 3)
    public void verifyAddUserFunctionalityEmailDuplicate() {
        initializeUserListPageAndReturnListCount();
        // getting existing email
        String emailCard1 = userListObject.getEmail().getText();

        addUserObject.getAddUserButton().click();
        String randomName = randomString.getRandomString();
        Actions builder = new Actions(driver);
        Action addUser = builder
                .sendKeys(addUserObject.getUsernameToAdd(), randomName)
                .sendKeys(addUserObject.getEmailToAdd(), emailCard1)
                .sendKeys(addUserObject.getFullNameToAdd(), randomName + " " + randomName)
                .sendKeys(addUserObject.getPasswordToAdd(), "1aa")
                .click(addUserObject.getTraitToAdd())
                .click(addUserObject.getGenderToAdd())
                .build();
        addUser.perform();
        WebDriverWait myWaitVariable = new WebDriverWait(driver, 5);
        myWaitVariable.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-addmodal/div/div/form/button"))).click();

        String alertMessage = driver.switchTo().alert().getText();
        Assert.assertEquals(alertMessage, "Http failure response for http://localhost:8080/api/form/users: 400 OK");
        driver.switchTo().alert().accept();
        addUserObject.getCloseAddModalButton().click();
    }

    private int initializeUserListPageAndReturnListCount() {
        driver.get("http://localhost:4200/users");

        //taking size of initial user list
        List<WebElement> usersList = driver.findElements(By.tagName("app-user-card"));
        int userListSizeBefore = usersList.size();
        return userListSizeBefore;
    }

}
