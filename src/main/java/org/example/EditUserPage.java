package org.example;

import lombok.Getter;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class EditUserPage {

    private WebDriver driver;

    @FindBy(id = "mat-input-0")
    private WebElement usernameToEdit;
    @FindBy(id = "mat-input-1")
    private WebElement emailToEdit;
    @FindBy(id = "mat-input-2")
    private WebElement fullNameToEdit;
    @FindBy(id = "mat-input-3")
    private WebElement passwordToEdit;
    @FindBy(xpath = "/html/body/app-root/app-editmodal/div/div/form/div/mat-checkbox[1]/label/span[1]")
    private WebElement traitToEdit1;
    @FindBy(xpath = "/html/body/app-root/app-editmodal/div/div/form/div/mat-checkbox[2]/label/span[1]")
    private WebElement traitToEdit2;
    @FindBy(xpath = "/html/body/app-root/app-editmodal/div/div/form/div/mat-checkbox[3]/label/span[1]")
    private WebElement traitToEdit3;
    @FindBy(xpath = "/html/body/app-root/app-editmodal/div/div/form/div/mat-checkbox[4]/label/span[1]")
    private WebElement traitToEdit4;
    @FindBy(xpath = "/html/body/app-root/app-editmodal/div/div/form/mat-radio-group/mat-radio-button[1]/label/span[1]/span[1]")
    private WebElement genderToEdit1;
    @FindBy(xpath = "/html/body/app-root/app-editmodal/div/div/form/mat-radio-group/mat-radio-button[2]/label/span[1]/span[1]")
    private WebElement genderToEdit2;
    @FindBy(xpath = "/html/body/app-root/app-editmodal/div/div/form/mat-radio-group/mat-radio-button[3]/label/span[1]/span[1]")
    private WebElement genderToEdit3;
    @FindBy(xpath = "/html/body/app-root/app-editmodal/div/div/form/button/span[1]")
    private WebElement submitButton;
    @FindBy(xpath = "/html/body/app-root/app-editmodal/div/div/div/i")
    private WebElement closeEditModalButton;

    public EditUserPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clearEditForm() {
        clearInputFields();
//        usernameToEdit.clear();
//        emailToEdit.clear();
//        fullNameToEdit.clear();
//        passwordToEdit.clear();
        traitToEdit1.click();
        traitToEdit2.click();
        traitToEdit3.click();
        traitToEdit4.click();
    }

    public void clearInputFields() {
        usernameToEdit.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        emailToEdit.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        fullNameToEdit.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        passwordToEdit.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
    }

}
