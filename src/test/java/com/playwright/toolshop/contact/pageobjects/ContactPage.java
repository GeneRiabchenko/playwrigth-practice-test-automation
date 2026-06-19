package com.playwright.toolshop.contact.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.BasePage;
import org.junit.jupiter.api.Assertions;

import java.nio.file.Path;

import static com.playwright.toolshop.contact.locators.ContactPageLocators.*;
import static com.playwright.toolshop.resources.Resources.CONTACTS_URL;

@UsePlaywright
public class ContactPage extends BasePage {
    private final Page page;

    public ContactPage(Page page) {
        super(page);
        this.page = page;
    }

    public void goToContactPage(){
        page.navigate(CONTACTS_URL);
    }

    public void fillFirstName(String firstName){
        page.getByLabel(FIRST_NAME_FIELD_LABEL).clear();
        page.getByLabel(FIRST_NAME_FIELD_LABEL).fill(firstName);
    }

    public void fillLastName(String lastName){
        page.locator(LAST_NAME_FIELD_INPUT).clear();
        page.locator(LAST_NAME_FIELD_INPUT).fill(lastName);
    }

    public void fillEmail(String lastName){
        page.getByPlaceholder(EMAIL_FIELD_PLACEHOLDER).clear();
        page.getByPlaceholder(EMAIL_FIELD_PLACEHOLDER).fill(lastName);
    }

    public void selectSubject(String subject){
        page.locator(SUBJECT_SELECTOR).selectOption(subject);
    }

    public void fillMessage(String message){
        page.locator(MESSAGE_FIELD_INPUT).clear();
        page.locator(MESSAGE_FIELD_INPUT).fill(message);
    }

    public void uploadFile(Path pathToFile){
        page.locator(UPLOAD_FILE_BUTTON).setInputFiles(pathToFile);
    }

    public void clickSend(){
        page.locator(SEND_BUTTON).click();
    }

    public void checkFirstNameErrorMessage(String expectedMessage){
        Assertions.assertEquals(expectedMessage, page.locator(FIRST_NAME_FIELD_ERROR).innerText());
    }

    public void checkLastNameErrorMessage(String expectedMessage){
        Assertions.assertEquals(expectedMessage, page.locator(LAST_NAME_FIELD_ERROR).innerText());
    }

    public void checkEmailErrorMessage(String expectedMessage){
        Assertions.assertEquals(expectedMessage, page.locator(EMAIL_FIELD_ERROR).innerText());
    }

    public void checkSubjectErrorMessage(String expectedMessage){
        Assertions.assertEquals(expectedMessage, page.locator(SUBJECT_FIELD_ERROR).innerText());
    }

    public void checkMessageFieldErrorMessage(String expectedMessage){
        Assertions.assertEquals(expectedMessage, page.locator(MESSAGE_FIELD_ERROR).innerText());
    }
}

