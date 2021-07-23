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
//    @FindBy(id = "mat-checkbox-1")
    @FindBy(xpath = "/html/body/app-root/app-editmodal/div/div/form/div/mat-checkbox[1]/label/span[1]")
    private WebElement traitToEdit1;
//    @FindBy(id = "mat-checkbox-2")
    @FindBy(xpath = "/html/body/app-root/app-editmodal/div/div/form/div/mat-checkbox[2]/label/span[1]")
    private WebElement traitToEdit2;
//    @FindBy(id = "mat-checkbox-3")
    @FindBy(xpath = "/html/body/app-root/app-editmodal/div/div/form/div/mat-checkbox[3]/label/span[1]")
    private WebElement traitToEdit3;
//    @FindBy(id = "mat-checkbox-4")
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
        usernameToEdit.clear();
        emailToEdit.clear();
        fullNameToEdit.clear();
        passwordToEdit.clear();
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

    private WebElement randomSelectTraitToCheck() {
        int traitOption = (int) (Math.random() * (4 - 1)) + 1;
        switch (traitOption) {
            case 1:
                return traitToEdit1;
            case 2:
                return traitToEdit2;
            case 3:
                return traitToEdit3;
            default:
                return traitToEdit4;
        }
    }

    private WebElement randomSelectGenderToCheck() {
        int genderOption = (int) (Math.random() * (3 - 1)) + 1;
        switch (genderOption) {
            case 1:
                return genderToEdit1;
            case 2:
                return genderToEdit2;
            default:
                return genderToEdit3;
        }
    }

}
