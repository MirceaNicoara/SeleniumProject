package org.example;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class AddUserPage {

    private WebDriver driver;
    RandomStringGenerator randomStringGenerator;

    @FindBy(xpath = "/html/body/app-root/app-users/app-header/mat-toolbar/button[2]/span[1]/span")
    private WebElement addUserButton;
    @FindBy(xpath = "/html/body/app-root/app-addmodal/div/div/div/i")
    private WebElement closeAddModalButton;
    @FindBy(id = "mat-input-0")
    private WebElement usernameToAdd;
    @FindBy(id = "mat-input-1")
    private WebElement emailToAdd;
    @FindBy(id = "mat-input-2")
    private WebElement fullNameToAdd;
    @FindBy(id = "mat-input-3")
    private WebElement passwordToAdd;
    @FindBy(id = "mat-checkbox-4-input")
    private WebElement traitToAdd;
    @FindBy(xpath = "/html/body/app-root/app-addmodal/div/div/form/mat-radio-group/mat-radio-button[2]/label/span[1]/span[1]")
    private WebElement genderToAdd;

    public AddUserPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

//    public void sendMandatoryMarkedAddUserKeys() {
//        String randomName = randomStringGenerator.getRandomString();
//        String randomEmail = randomName + "_email@gmail.com";
//        Actions builder = new Actions(driver);
//        Action addUser = builder
//                .sendKeys(usernameToAdd, randomName)
//                .sendKeys(emailToAdd, randomEmail)
//                .sendKeys(passwordToAdd, "1aa")
//                .build();
//        addUser.perform();
//    }
//    public void sendAllAddUserKeys() {
//        String randomName = randomStringGenerator.getRandomString();
//        String randomEmail = randomName + "_email@gmail.com";
//        Actions builder = new Actions(driver);
//        Action addUser = builder
//                .sendKeys(usernameToAdd, randomName)
//                .sendKeys(emailToAdd, randomEmail)
//                .sendKeys(passwordToAdd, "1aa")
//                .click(traitToAdd)
//                .click(genderToAdd)
//                .build();
//        addUser.perform();
//    }
}
