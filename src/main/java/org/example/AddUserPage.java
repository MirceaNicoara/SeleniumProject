package org.example;

import lombok.Getter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Random;

@Getter
public class AddUserPage {

    private WebDriver driver;

    @FindBy(xpath = "(//span[contains(.,'Add user')])[2]")
    private WebElement addUserButton;
    @FindBy(xpath = "//i[@class='fas fa-times fa-3x']")
    private WebElement closeAddModalButton;
    @FindBy(id = "mat-input-0")
    private WebElement usernameToAdd;
    @FindBy(id = "mat-input-1")
    private WebElement emailToAdd;
    @FindBy(id = "mat-input-2")
    private WebElement fullNameToAdd;
    @FindBy(id = "mat-input-3")
    private WebElement passwordToAdd;
    @FindBy(id = "mat-checkbox-1-input")
    private WebElement trait1;
    @FindBy(id = "mat-checkbox-2-input")
    private WebElement trait2;
    @FindBy(xpath = "(//span[@class='mat-radio-inner-circle'])[3]")
    private WebElement genderToAdd;

    public AddUserPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void insertCredentials(String username, String email, String fullName, String password) {

        Actions builder = new Actions(driver);
        Action addUser = builder
                .sendKeys(usernameToAdd, username)
                .sendKeys(emailToAdd, email)
                .sendKeys(fullNameToAdd, fullName)
                .sendKeys(passwordToAdd, password)
                .build();
        addUser.perform();
    }

    public void selectTraitsAndGender() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click()", trait1);
        jsExecutor.executeScript("arguments[0].click()", trait2);
        jsExecutor.executeScript("arguments[0].click()", genderToAdd);
    }

    public String getRandomString() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int stringLength = 5;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(stringLength);
        for (int i = 0; i < stringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        generatedString = generatedString.substring(0, 1).toUpperCase() + generatedString.substring(1).toLowerCase();
        return generatedString;
    }

    public UserModel createRandomUser() {
        String username = getRandomString();
        String email = username + "_email@gmail.com";
        String fullName = username + username;
        String password = username + "123";
        return new UserModel(username, email, fullName, password);
    }
}
