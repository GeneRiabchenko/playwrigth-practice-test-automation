package com.playwright.toolshop.tests.contact;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.HeadlessChromeOptions;
import com.playwright.toolshop.pages.ContactPage;
import com.playwright.toolshop.tests.BaseTestRunner;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.playwright.toolshop.testresources.Resources.SAMPLE_FILE_URI;

@UsePlaywright(HeadlessChromeOptions.class)
public class ContactTests extends BaseTestRunner {
    ContactPage contactPage;

    @BeforeEach
    public void setUp(Page page){
        contactPage = new ContactPage(page);
        contactPage.goToContactPage();
    }

    @Epic("Contact EPIC")
    @Feature("Creating new user web")
    @Test
    @DisplayName("Send contact request")
    void sendContactRequest() {
        Path fileToUpload = Paths.get(SAMPLE_FILE_URI);
        contactPage.fillFirstName("Vasya");
        contactPage.fillLastName("Petya");
        contactPage.fillEmail("vasya_petya@gmail.com");
        contactPage.selectSubject("Return");
        contactPage.fillMessage("Hi Vasya 23423423423423 4234234234 2342342342342 423423423423 42342");
        contactPage.uploadFile(fileToUpload);
        contactPage.clickSend();
    }

    @Epic("Contact EPIC")
    @Feature("Contacts")
    @Test
    @DisplayName("Check contact request validations")
    void checkContactPageErrorMessages(){
        contactPage.clickSend();
        contactPage.checkFirstNameErrorMessage("First name is required");
        contactPage.checkLastNameErrorMessage("Last name is required");
        contactPage.checkEmailErrorMessage("Email is required");
        contactPage.checkSubjectErrorMessage("Subject is required");
        contactPage.checkMessageFieldErrorMessage("Message is required");
    }

}