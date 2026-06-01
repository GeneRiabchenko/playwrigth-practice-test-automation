package com.playwright.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Assertions;

import java.nio.file.Path;

import static com.playwright.locators.ContactPageLocators.*;
import static com.playwright.resources.Resources.CONTACTS_URL;

@UsePlaywright
public class ContactPage extends BasePage{
    public static void goToContactPage(Page page){
        page.navigate(CONTACTS_URL);
    }

    public static void fillFirstName(String firstName, Page page){
        page.getByLabel(FIRST_NAME_FIELD_LABEL).clear();
        page.getByLabel(FIRST_NAME_FIELD_LABEL).fill(firstName);
    }

    public static void fillLastName(String lastName, Page page){
        page.locator(LAST_NAME_FIELD_INPUT).clear();
        page.locator(LAST_NAME_FIELD_INPUT).fill(lastName);
    }

    public static void fillEmail(String lastName, Page page){
        page.getByPlaceholder(EMAIL_FIELD_PLACEHOLDER).clear();
        page.getByPlaceholder(EMAIL_FIELD_PLACEHOLDER).fill(lastName);
    }

    public static void selectSubject(String subject, Page page){
        page.locator(SUBJECT_SELECTOR).selectOption(subject);
    }

    public static void fillMessage(String message, Page page){
        page.locator(MESSAGE_FIELD_INPUT).clear();
        page.locator(MESSAGE_FIELD_INPUT).fill(message);
    }

    public static void uploadFile(Path pathToFile, Page page){
        page.locator(UPLOAD_FILE_BUTTON).setInputFiles(pathToFile);
    }

    public static void clickSend(Page page){
        page.locator(SEND_BUTTON).click();
    }

    public static void checkFirstNameErrorMessage(String expectedMessage, Page page){
        Assertions.assertEquals(expectedMessage, page.locator(FIRST_NAME_FIELD_ERROR).innerText());
    }

    public static void checkLastNameErrorMessage(String expectedMessage, Page page){
        Assertions.assertEquals(expectedMessage, page.locator(LAST_NAME_FIELD_ERROR).innerText());
    }

    public static void checkEmailErrorMessage(String expectedMessage, Page page){
        Assertions.assertEquals(expectedMessage, page.locator(EMAIL_FIELD_ERROR).innerText());
    }

    public static void checkSubjectErrorMessage(String expectedMessage, Page page){
        Assertions.assertEquals(expectedMessage, page.locator(SUBJECT_FIELD_ERROR).innerText());
    }

    public static void checkMessageFieldErrorMessage(String expectedMessage, Page page){
        Assertions.assertEquals(expectedMessage, page.locator(MESSAGE_FIELD_ERROR).innerText());
    }
}

