package org.example;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class UsersListPage {

    private WebDriver driver;

    @FindBy(xpath = "/html/body/app-root/app-users/app-user-card[1]/div/div[2]/span")
    private WebElement username;
    @FindBy(xpath = "/html/body/app-root/app-users/app-user-card[1]/div/div[3]/span")
    private WebElement email;
    @FindBy(xpath = "/html/body/app-root/app-users/app-user-card[1]/div/div[1]/a/h1")
    private WebElement fullName;
    @FindBy(xpath = "/html/body/app-root/app-users/app-user-card[1]/div/div[4]/span")
    private WebElement password;
    @FindBy(xpath = "/html/body/app-root/app-users/app-user-card[1]/div/div[5]/span")
    private WebElement trait;
    @FindBy(xpath = "/html/body/app-root/app-users/app-user-card[1]/div/div[6]/span")
    private WebElement gender;
    @FindBy(xpath = "/html/body/app-root/app-users/app-header/mat-toolbar/button[1]/span[1]/mat-icon")
    private WebElement menuButton;
    @FindBy(xpath = "/html/body/app-root/app-users/app-header/mat-toolbar/button[2]/span[1]/span")
    private WebElement addUserButton;
    @FindBy(xpath = "/html/body/app-root/app-users/app-header/mat-toolbar/div/button/mat-icon")
    private WebElement backButtonElement;
    @FindBy(xpath = "(//button[contains(.,'Edit')])[1]")
    private WebElement editButton;

    public UsersListPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}

