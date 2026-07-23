package com.playwright.toolshop.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Assertions;

import java.nio.file.Path;

import static com.playwright.toolshop.testresources.Resources.CONTACTS_URL;

@UsePlaywright
public class ContactPage {
    private final Page page;

    private final Locator FIRST_NAME_FIELD;
    private final Locator LAST_NAME_FIELD;
    private final Locator EMAIL_FIELD;
    private final Locator SUBJECT_FIELD;
    private final Locator MESSAGE_FIELD;
    private final Locator UPLOAD_FILE_BUTTON;
    private final Locator SEND_BUTTON;
    private final Locator FIRST_NAME_FIELD_ERROR;
    private final Locator LAST_NAME_FIELD_ERROR;
    private final Locator EMAIL_FIELD_ERROR;
    private final Locator SUBJECT_FIELD_ERROR;
    private final Locator MESSAGE_FIELD_ERROR;

    public ContactPage(Page page) {
        this.page = page;
        this.FIRST_NAME_FIELD = page.getByTestId("first-name");
        this.LAST_NAME_FIELD = page.getByTestId("last-name");
        this.EMAIL_FIELD = page.getByTestId("email");
        this.SUBJECT_FIELD = page.getByTestId("subject");
        this.MESSAGE_FIELD = page.getByTestId("message");
        this.UPLOAD_FILE_BUTTON = page.getByTestId("attachment");
        this.SEND_BUTTON = page.getByTestId("contact-submit");
        this.FIRST_NAME_FIELD_ERROR = page.getByTestId("first-name-error");
        this.LAST_NAME_FIELD_ERROR = page.getByTestId("last-name-error");
        this.EMAIL_FIELD_ERROR = page.getByTestId("email-error");
        this.SUBJECT_FIELD_ERROR = page.getByTestId("subject-error");
        this.MESSAGE_FIELD_ERROR = page.getByTestId("message-error");

    }

    public void goToContactPage(){
        page.navigate(CONTACTS_URL);
    }

    public void fillFirstName(String firstName){
        FIRST_NAME_FIELD.clear();
        FIRST_NAME_FIELD.fill(firstName);
    }

    public void fillLastName(String lastName){
        LAST_NAME_FIELD.clear();
        LAST_NAME_FIELD.fill(lastName);
    }

    public void fillEmail(String lastName){
        EMAIL_FIELD.clear();
        EMAIL_FIELD.fill(lastName);
    }

    public void selectSubject(String subject){
        SUBJECT_FIELD.selectOption(subject);
    }

    public void fillMessage(String message){
        MESSAGE_FIELD.clear();
        MESSAGE_FIELD.fill(message);
    }

    public void uploadFile(Path pathToFile){
        UPLOAD_FILE_BUTTON.setInputFiles(pathToFile);
    }

    public void clickSend(){
        SEND_BUTTON.click();
    }

    public void checkFirstNameErrorMessage(String expectedMessage){
        Assertions.assertEquals(expectedMessage, FIRST_NAME_FIELD_ERROR.innerText());
    }

    public void checkLastNameErrorMessage(String expectedMessage){
        Assertions.assertEquals(expectedMessage, LAST_NAME_FIELD_ERROR.innerText());
    }

    public void checkEmailErrorMessage(String expectedMessage){
        Assertions.assertEquals(expectedMessage, EMAIL_FIELD_ERROR.innerText());
    }

    public void checkSubjectErrorMessage(String expectedMessage){
        Assertions.assertEquals(expectedMessage, SUBJECT_FIELD_ERROR.innerText());
    }

    public void checkMessageFieldErrorMessage(String expectedMessage){
        Assertions.assertEquals(expectedMessage, MESSAGE_FIELD_ERROR.innerText());
    }
}

