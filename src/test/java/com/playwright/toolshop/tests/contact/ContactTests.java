package com.playwright.toolshop.tests.contact;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.HeadlessChromeOptions;
import com.playwright.toolshop.fixtures.BaseTestRunner;
import com.playwright.toolshop.pages.ContactPage;
import net.serenitybdd.annotations.Feature;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Story;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.playwright.junit5.SerenityPlaywrightExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.playwright.toolshop.testresources.Resources.SAMPLE_FILE_URI;

@ExtendWith(SerenityJUnit5Extension.class)
@ExtendWith(SerenityPlaywrightExtension.class)
@UsePlaywright(HeadlessChromeOptions.class)
public class ContactTests extends BaseTestRunner {
    @Steps
    ContactPage contactPage;

    @BeforeEach
    public void setUp(Page page){
        contactPage.goToContactPage();
    }

    @Story("Contact EPIC")
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

    @Story("Contact EPIC")
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