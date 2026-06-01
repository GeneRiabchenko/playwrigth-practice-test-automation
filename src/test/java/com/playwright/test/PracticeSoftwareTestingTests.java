package com.playwright.test;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.pages.ContactPage;
import org.junit.jupiter.api.*;
import com.playwright.pages.MainPage;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static com.playwright.resources.Resources.MAIN_URL;
import static com.playwright.resources.Resources.SAMPLE_FILE_URI;

//public class PracticeSoftwareTestingTests extends BaseTest {
@UsePlaywright(HeadlessChromeOptions.class)
public class PracticeSoftwareTestingTests {
    List<String> EXPECTED_PRODUCTS = Arrays.asList("Combination Pliers", "Pliers", "Long Nose Pliers", "Slip Joint Pliers");
    List<String> EXPECTED_PRODUCTS_MAIN_PAGE = Arrays.asList("Combination Pliers",
            "Pliers",
            "Bolt Cutters",
            "Long Nose Pliers",
            "Slip Joint Pliers",
            "Claw Hammer with Shock Reduction Grip",
            "Hammer",
            "Claw Hammer",
            "Thor Hammer"
    );
    List<String> EXPECTED_OUT_OF_STOCK_PRODUCTS = Arrays.asList("Long Nose Pliers");

    @Test
    void shouldShowTitle(Page page){
        MainPage.goToMainPage(page);
        Assertions.assertEquals("Practice Software Testing - Toolshop - v5.0", page.title());
    }

    @Test
    void checkSearchFeature(Page page){
        MainPage.goToMainPage(page);
        MainPage.search("pliers", page);
        Assertions.assertEquals("Searched for: pliers", MainPage.getSearchForLabel(page));
        Assertions.assertEquals(4, MainPage.countSearchedResults(page));
        MainPage.checkProductNames(EXPECTED_PRODUCTS, page);
    }

    @Test
    void checkOutOfStockItemsAreNotFilteredBySearch(Page page){
        MainPage.goToMainPage(page);
        MainPage.search("pliers", page);
        Assertions.assertEquals("Searched for: pliers", MainPage.getSearchForLabel(page));
        Assertions.assertEquals(4, MainPage.countSearchedResults(page));
        Assertions.assertEquals(EXPECTED_OUT_OF_STOCK_PRODUCTS, MainPage.getOutOfStockItems(page));
        MainPage.checkProductNames(EXPECTED_PRODUCTS, page);
    }

    @Test
    void checkThatProductPageIsOpenedAndBadgesAreShown(Page page){
        MainPage.goToMainPage(page);
        MainPage.clickElementByText("Bolt Cutters", page);
        PlaywrightAssertions.assertThat(MainPage.getElementByAltText("Bolt Cutters", page)).isVisible();
        PlaywrightAssertions.assertThat(MainPage.getBadgeByText("MightyCraft Hardware", page)).isVisible();
        PlaywrightAssertions.assertThat(MainPage.getBadgeByText("Pliers", page)).isVisible();
    }

    @Test
    void checkRelatedProduct(Page page){
        MainPage.goToMainPage(page);
        MainPage.clickElementByAltText("Combination Pliers", page);
        PlaywrightAssertions.assertThat(MainPage.getElementByAltText("Slip Joint Pliers", page)).isVisible();
        PlaywrightAssertions.assertThat(MainPage.getElementByAltText("Bolt Cutters", page)).isVisible();
        PlaywrightAssertions.assertThat(MainPage.getElementByAltText("Long Nose Pliers", page)).isVisible();
    }

    @Test
    void shouldShowProductImages(Page page){
        MainPage.goToMainPage(page);
        MainPage.checkProductImagesTitles(EXPECTED_PRODUCTS_MAIN_PAGE, page);
    }


    @Test
    void clickingOnSiteLogoRedirectsToMainPage(Page page){
        MainPage.goToMainPage(page);
        MainPage.clickElementByAltText("Combination Pliers", page);
        MainPage.clickElementByTitle("Practice Software Testing - Toolshop", page);
        MainPage.checkUrl(MAIN_URL, page);
    }

    @Test
    void sendContactRequest(Page page) throws URISyntaxException {
        Path fileToUpload = Paths.get(SAMPLE_FILE_URI);
        ContactPage.goToContactPage(page);
        ContactPage.fillFirstName("Vasya", page);
        ContactPage.fillLastName("Petya", page);
        ContactPage.fillEmail("vasya_petya@gmail.com", page);
        ContactPage.selectSubject("Return", page);
        ContactPage.fillMessage("Hi Vasya 23423423423423 4234234234 2342342342342 423423423423 42342", page);
        ContactPage.uploadFile(fileToUpload, page);
        ContactPage.clickSend(page);
    }

    @Test
    void checkContactPageErrorMessages(Page page){
        ContactPage.goToContactPage(page);
        ContactPage.clickSend(page);
        ContactPage.checkFirstNameErrorMessage("First name is required", page);
        ContactPage.checkLastNameErrorMessage("Last name is required", page);
        ContactPage.checkEmailErrorMessage("Email is required", page);
        ContactPage.checkSubjectErrorMessage("Subject is required", page);
        ContactPage.checkMessageFieldErrorMessage("Message is required", page);
    }

}
