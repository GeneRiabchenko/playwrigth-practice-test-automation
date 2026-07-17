package com.playwright.toolshop.tests.contact;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.HeadlessChromeOptions;
import com.playwright.toolshop.pageObjects.ContactPage;
import com.playwright.toolshop.tests.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.playwright.toolshop.resources.Resources.SAMPLE_FILE_URI;

@UsePlaywright(HeadlessChromeOptions.class)
public class ContactTests extends BaseTest {
    ContactPage contactPage;

    @BeforeEach
    public void setUp(Page page){
        contactPage = new ContactPage(page);
        contactPage.goToContactPage();
    }

    @Test
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

    @Test
    void checkContactPageErrorMessages(){
        contactPage.clickSend();
        contactPage.checkFirstNameErrorMessage("First name is required");
        contactPage.checkLastNameErrorMessage("Last name is required");
        contactPage.checkEmailErrorMessage("Email is required");
        contactPage.checkSubjectErrorMessage("Subject is required");
        contactPage.checkMessageFieldErrorMessage("Message is required");
    }

}