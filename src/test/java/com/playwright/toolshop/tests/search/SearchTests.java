package com.playwright.toolshop.tests.search;

import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.HeadlessChromeOptions;
import com.playwright.toolshop.fixtures.BaseTestRunner;
import com.playwright.toolshop.pages.LeftNavigationPage;
import com.playwright.toolshop.pages.MainPage;
import net.serenitybdd.annotations.Feature;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.playwright.junit5.SerenityPlaywrightExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.playwright.toolshop.testresources.Resources.EXPECTED_PRODUCTS_MAIN_PAGE;
import static com.playwright.toolshop.testresources.Resources.EXPECTED_PRODUCTS_PLIERS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SerenityJUnit5Extension.class)
@ExtendWith(SerenityPlaywrightExtension.class)
@UsePlaywright(HeadlessChromeOptions.class)
public class SearchTests extends BaseTestRunner {
    @Steps MainPage mainPage;
    @Steps LeftNavigationPage leftNavigationPage;

    @BeforeEach
    void openHomePage(){
        mainPage.navigate();
    }

    @Feature("Product Catalog")
    @Test
    public void searchDoesNotWorkWhenEmpty(){
        leftNavigationPage.search("");
        mainPage.checkProductNames(EXPECTED_PRODUCTS_MAIN_PAGE);
    }

    @Feature("Product Catalog")
    @Test
    @DisplayName("Check that no results are shown when search doesn't match with any product")
    public void noMatchesTest(){
        String searchString = "4564746";
        int expectedCount = 0;
        leftNavigationPage.search(searchString);
        mainPage.checkSearchCountField(expectedCount, searchString);
        assertEquals(expectedCount, mainPage.productCount());
        mainPage.isNoProductMessageShown(true);
    }

    @Feature("Filter")
    @Test
    @DisplayName("Cross button should clear search results")
    void crossButtonClearsSearchResult() {
        leftNavigationPage.search("pliers");
        mainPage.checkProductNames(EXPECTED_PRODUCTS_PLIERS);
        leftNavigationPage.clickCrossButton();
        mainPage.isSearchCountFieldVisible(false);
        mainPage.checkProductNames(EXPECTED_PRODUCTS_MAIN_PAGE);
    }

}
