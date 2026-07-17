package com.playwright.toolshop.tests.product;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.HeadlessChromeOptions;
import com.playwright.toolshop.pageObjects.LeftNavigationPage;
import com.playwright.toolshop.pageObjects.MainPage;
import com.playwright.toolshop.pageObjects.ProductPage;
import com.playwright.toolshop.tests.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static com.playwright.toolshop.resources.Resources.*;

@UsePlaywright(HeadlessChromeOptions.class)
public class MainPageTests extends BaseTest {
    MainPage mainPage;
    ProductPage productPage;
    LeftNavigationPage leftNavigationPage;


    @BeforeEach
    void openHomePage(){
        mainPage.goToMainPage();
    }

    @BeforeEach
    public void setUp(Page page){
        mainPage = new MainPage(page);
        productPage = new ProductPage(page);
        leftNavigationPage = new LeftNavigationPage(page);
    }


    @Test
    void shouldShowTitle(Page page){
        Assertions.assertEquals("Practice Software Testing - Toolshop - v5.0", page.title());
    }

    @Test
    void checkSearchFeature(){
        leftNavigationPage.search("pliers");
        Assertions.assertEquals("Searched for: pliers", mainPage.getSearchForLabel());
        Assertions.assertEquals(4, mainPage.countSearchedResults());
        mainPage.checkProductNames(EXPECTED_PRODUCTS_PLIERS);
    }

    @Test
    void checkOutOfStockItemsAreNotFilteredBySearch(){
        leftNavigationPage.search("pliers");
        Assertions.assertEquals("Searched for: pliers", mainPage.getSearchForLabel());
        Assertions.assertEquals(4, mainPage.countSearchedResults());
        Assertions.assertEquals(EXPECTED_OUT_OF_STOCK_PRODUCTS, mainPage.getOutOfStockItems());
        mainPage.checkProductNames(EXPECTED_PRODUCTS_PLIERS);
    }

    @Test
    void checkThatProductPageIsOpenedAndBadgesAreShown(){
        mainPage.clickElementByText("Bolt Cutters");
        PlaywrightAssertions.assertThat(mainPage.getElementByAltText("Bolt Cutters")).isVisible();
        PlaywrightAssertions.assertThat(mainPage.getBadgeByText("MightyCraft Hardware")).isVisible();
        PlaywrightAssertions.assertThat(mainPage.getBadgeByText("Pliers")).isVisible();
    }

    @Test
    void checkRelatedProduct(){
        mainPage.clickElementByAltText("Combination Pliers");
        PlaywrightAssertions.assertThat(mainPage.getElementByAltText("Slip Joint Pliers")).isVisible();
        PlaywrightAssertions.assertThat(mainPage.getElementByAltText("Bolt Cutters")).isVisible();
        PlaywrightAssertions.assertThat(mainPage.getElementByAltText("Long Nose Pliers")).isVisible();
    }

    @Test
    void shouldShowProductImages(){
        mainPage.checkProductImagesTitles(EXPECTED_PRODUCTS_MAIN_PAGE);
    }

    @Test
    void shouldFilterProductsByCategory(){
        leftNavigationPage.selectPowerToolsCategory();
        mainPage.checkProductNames(EXPECTED_POWER_TOOLS);
    }

    @Test
    void clickingOnSiteLogoRedirectsToMainPage(){
        mainPage.clickElementByAltText("Combination Pliers");
        mainPage.clickElementByTitle("Practice Software Testing - Toolshop");
        mainPage.checkUrl(MAIN_URL);
    }

    @Test
    void shouldDisplayToasterMessage(){
        mainPage.clickElementByText("Bolt Cutters");
        productPage.addToTheCart();
        mainPage.waitForTheToasterMessage("Product added to shopping cart.");
        mainPage.waitForTheToasterMessageToDisappear();
    }

    @Test
    void shouldUpdateCartItemCount(){
        mainPage.clickElementByText("Bolt Cutters");
        productPage.addToTheCart();
        mainPage.checkCartItemCount("1");
    }

    @Test
    void waitForAPIResponse(Page page){

        //https://api.practicesoftwaretesting.com/products?page=0&sort=price,desc&between=price,1,100&is_rental=false
        page.waitForResponse(PRODUCTS_REQUEST_URL, () -> page.getByTestId("sort").selectOption("Price (High - Low)"));

        var productPrices = page.getByTestId("product-price").allInnerTexts()
                .stream()
                .map(MainPageTests::extractPrice)
                .toList();

        Assertions.assertEquals(productPrices.stream().sorted(Comparator.reverseOrder()).toList(), productPrices);
    }

    private static double extractPrice(String price){
        return Double.parseDouble(price.replace("$",""));
    }
}
