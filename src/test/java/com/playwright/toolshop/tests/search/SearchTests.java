package com.playwright.toolshop.tests.search;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.HeadlessChromeOptions;
import com.playwright.toolshop.pageObjects.LeftNavigationPage;
import com.playwright.toolshop.pageObjects.MainPage;
import com.playwright.toolshop.tests.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.playwright.toolshop.resources.Resources.EXPECTED_PRODUCTS_MAIN_PAGE;
import static com.playwright.toolshop.resources.Resources.EXPECTED_PRODUCTS_PLIERS;
import static org.junit.jupiter.api.Assertions.assertEquals;

@UsePlaywright(HeadlessChromeOptions.class)
public class SearchTests extends BaseTest {
    MainPage mainPage;
    LeftNavigationPage leftNavigationPage;


    @BeforeEach
    void openHomePage(){
        mainPage.goToMainPage();
    }

    @BeforeEach
    public void setUp(Page page){
        mainPage = new MainPage(page);
        leftNavigationPage = new LeftNavigationPage(page);
    }

    @Test
    public void searchDoesNotWorkWhenEmpty(){
        leftNavigationPage.search("");
        mainPage.checkProductNames(EXPECTED_PRODUCTS_MAIN_PAGE);
    }

    @DisplayName("Check that no results are shown when search doesn't match with any product")
    @Test
    public void noMatchesTest(){
        String searchString = "4564746";
        int expectedCount = 0;
        leftNavigationPage.search(searchString);
        mainPage.checkSearchCountField(expectedCount, searchString);
        assertEquals(expectedCount, mainPage.countSearchedResults());
        mainPage.isNoProductMessageShown(true);
    }

    @DisplayName("Cross button should clear search results")
    @Test
    void crossButtonClearsSearchResult() {
        leftNavigationPage.search("pliers");
        mainPage.checkProductNames(EXPECTED_PRODUCTS_PLIERS);
        leftNavigationPage.clickCrossButton();
        mainPage.isSearchCountFieldVisible(false);
        mainPage.checkProductNames(EXPECTED_PRODUCTS_MAIN_PAGE);
    }

}
