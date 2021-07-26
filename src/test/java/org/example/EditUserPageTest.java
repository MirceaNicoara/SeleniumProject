package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class EditUserPageTest {

    EditUserPage editUserPageObject;
    UsersListPage usersListPageObject;
    WebDriver driver;
    AddUserPage addUserPageObject;
    UserModel userModel;

    @BeforeTest
    public void initialSetUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:4200/users");
        editUserPageObject = new EditUserPage(driver);
        usersListPageObject = new UsersListPage(driver);
        addUserPageObject = new AddUserPage(driver);
        userModel = addUserPageObject.createRandomUser();
    }

    @Test(priority = 5)
    public void verifyUserEditFunctionalityValidCredentials() {
        initializeUserListPage();
        // taking first user to be edited as after editing will be visible without scrolling
        // if this approach does not gratify you, comment next line and uncomment the two lines after the next one
        int userToBeEditedId = 1;
        /* List<WebElement> usersList = driver.findElements(By.tagName("app-user-card"));
           int userToBeEditedId = (int) (Math.random() * (usersList.size() - 1)) + 1; */
        driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + userToBeEditedId + "]/div/div[7]/button[1]")).click();

        WebDriverWait myWaitVariable = new WebDriverWait(driver, 5);
        myWaitVariable.until(ExpectedConditions.urlContains("http://localhost:4200/edit/" + userToBeEditedId));

        // clearing fields
        editUserPageObject.clearEditForm();

        // inserting new values
        addUserPageObject.insertCredentials(userModel.getUsername(), userModel.getEmail(), userModel.getFullName(), userModel.getPassword());
        addUserPageObject.selectTraitsAndGender();

        // submitting form
        myWaitVariable.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-editmodal/div/div/form/button/span[1]"))).click();
        myWaitVariable.until(ExpectedConditions.urlContains("http://localhost:4200/users"));

        // checking values
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + userToBeEditedId + "]/div/div[1]/a/h1")).getText(),
                userModel.getFullName());
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + userToBeEditedId + "]/div/div[2]/span")).getText(),
                userModel.getUsername());
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + userToBeEditedId + "]/div/div[3]/span")).getText(),
                userModel.getEmail());
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + userToBeEditedId + "]/div/div[4]/span")).getText(),
                userModel.getPassword());
    }

    @Test(priority = 7)
    public void verifyUserEditFunctionalityBlankCredentials() {
        initializeUserListPage();
        /* taking first user to be edited as after editing will be visible without scrolling;
           if this approach does not gratify you, comment next line and uncomment the two lines after the next one to get random edit*/
        int userToBeEditedId = 1;
        /* List<WebElement> usersList = driver.findElements(By.tagName("app-user-card"));
           int userToBeEditedId = (int) (Math.random() * (usersList.size() - 1)) + 1; */
        driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + userToBeEditedId + "]/div/div[7]/button[1]")).click();

        WebDriverWait myWaitVariable = new WebDriverWait(driver, 5);
        myWaitVariable.until(ExpectedConditions.urlContains("http://localhost:4200/edit/" + userToBeEditedId));

        // clearing fields
        editUserPageObject.clearInputFields();

        myWaitVariable.until(ExpectedConditions.elementToBeClickable(editUserPageObject.getSubmitButton())).click();
        myWaitVariable.until(ExpectedConditions.urlContains("http://localhost:4200/users"));

        Assert.assertNotEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + userToBeEditedId + "]/div/div[1]/a/h1")).getText(),
                "");
        Assert.assertNotEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + userToBeEditedId + "]/div/div[2]/span")).getText(),
                "");
        Assert.assertNotEquals(driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[" + userToBeEditedId + "]/div/div[3]/span")).getText(),
                "");
    }

    @Test(priority = 6)
    public void checkEditUserWithInvalidEmail() { // invalid email = not containing @{domain}.{domain}
        initializeUserListPage();
        usersListPageObject.getEditButton().click();
        editUserPageObject.getEmailToEdit().clear();
        editUserPageObject.getEmailToEdit().sendKeys("emailTest");
        editUserPageObject.getSubmitButton().click();
        Assert.assertNotEquals(usersListPageObject.getEmail().getText(), "emailTest");
    }

    @Test(priority = 6)
    public void checkEditUserWithInvalidUsername() { // invalid = blank space inside username
        initializeUserListPage();
        usersListPageObject.getEditButton().click();
        editUserPageObject.getUsernameToEdit().clear();
        editUserPageObject.getUsernameToEdit().sendKeys("!@#");
//        editUserPageObject.getUsernameToEdit().sendKeys(Keys.SPACE);
//        editUserPageObject.getUsernameToEdit().sendKeys(Keys.SPACE);
//        editUserPageObject.getUsernameToEdit().sendKeys(Keys.SPACE);
        Actions action = new Actions(driver);
        action.sendKeys(Keys.SPACE).build().perform();
        action.sendKeys(Keys.SPACE).build().perform();
        action.sendKeys(Keys.SPACE).build().perform();
        editUserPageObject.getUsernameToEdit().sendKeys("!@#");
        editUserPageObject.getSubmitButton().click();
        Assert.assertNotEquals(usersListPageObject.getAddUserButton().getText(), "!@# !@#");
    }

    @Test
    public void verifyEditUserFunctionalityUsernameDuplicate() {
        initializeUserListPage();
        // getting existing username card 2
        String usernameCard2 = driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[2]/div/div[2]/span")).getText();
        // entering editing card 1, clearing old data, duplicating card 2 username
        driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[1]/div/div[7]/button[1]")).click();
        editUserPageObject.getUsernameToEdit().clear();
        editUserPageObject.getUsernameToEdit().sendKeys(usernameCard2);

        WebDriverWait myWaitVariable = new WebDriverWait(driver, 5);
        myWaitVariable.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-editmodal/div/div/form/button"))).click();

        String alertMessage = driver.switchTo().alert().getText();
        Assert.assertEquals(alertMessage, "Http failure response for http://localhost:8080/api/form/users/1: 400 OK");
        driver.switchTo().alert().accept();
        editUserPageObject.getCloseEditModalButton().click();
    }

    @Test
    public void verifyAddUserFunctionalityEmailDuplicate() {
        initializeUserListPage();
        // getting existing email on card 2
        String emailCard2 = driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[2]/div/div[3]/span")).getText();
        // entering editing card 1, clearing old data, duplicating card 2 username
        driver.findElement(By.xpath("/html/body/app-root/app-users/app-user-card[1]/div/div[7]/button[1]")).click();
        editUserPageObject.getEmailToEdit().clear();
        editUserPageObject.getEmailToEdit().sendKeys(emailCard2);

        WebDriverWait myWaitVariable = new WebDriverWait(driver, 5);
        myWaitVariable.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-editmodal/div/div/form/button"))).click();

        String alertMessage = driver.switchTo().alert().getText();
        Assert.assertEquals(alertMessage, "Http failure response for http://localhost:8080/api/form/users/1: 400 OK");
        driver.switchTo().alert().accept();
        editUserPageObject.getCloseEditModalButton().click();
    }

    private void initializeUserListPage() {
        driver.get("http://localhost:4200/users");
    }
}
