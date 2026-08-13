package com.playwright.toolshop.tests.product;

import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.HeadlessChromeOptions;
import com.playwright.toolshop.fixtures.BaseTestRunner;
import com.playwright.toolshop.pages.LeftNavigationPage;
import com.playwright.toolshop.pages.MainPage;
import com.playwright.toolshop.pages.ProductPage;
import net.serenitybdd.annotations.Feature;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.playwright.junit5.SerenityPlaywrightExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Comparator;

import static com.playwright.toolshop.testresources.Resources.*;

@ExtendWith(SerenityJUnit5Extension.class)
@ExtendWith(SerenityPlaywrightExtension.class)
@UsePlaywright(HeadlessChromeOptions.class)
public class MainPageTests extends BaseTestRunner {
    @Steps MainPage mainPage;
    @Steps ProductPage productPage;
    @Steps LeftNavigationPage leftNavigationPage;

    @BeforeEach
    void openHomePage(){
        mainPage.navigate();
    }


    @Test
    @DisplayName("Correct title should be shown on the main page")
    void shouldShowTitle(){
        Assertions.assertEquals("Practice Software Testing - Toolshop - v5.0", mainPage.getTitle());
    }

    @Feature("Product Catalog")
    @Test
    @DisplayName("Correct searched products should be shown")
    void checkSearchFeature(){
        leftNavigationPage.search("pliers");
        Assertions.assertEquals("Searched for: pliers", mainPage.getSearchForLabel());
        Assertions.assertEquals(4, mainPage.productCount());
        mainPage.checkProductNames(EXPECTED_PRODUCTS_PLIERS);
    }

    @Feature("Product Catalog")
    @Test
    @DisplayName("Out of stock products should not be filtered by search")
    void checkOutOfStockItemsAreNotFilteredBySearch(){
        leftNavigationPage.search("pliers");
        Assertions.assertEquals("Searched for: pliers", mainPage.getSearchForLabel());
        Assertions.assertEquals(4, mainPage.productCount());
        Assertions.assertEquals(EXPECTED_OUT_OF_STOCK_PRODUCTS, mainPage.getOutOfStockItems());
        mainPage.checkProductNames(EXPECTED_PRODUCTS_PLIERS);
    }

    @Feature("Product Catalog")
    @Test
    @DisplayName("Out of stock products should not be filtered by search")
    void checkThatProductPageIsOpenedAndBadgesAreShown(){
        mainPage.clickElementByText("Bolt Cutters");
        PlaywrightAssertions.assertThat(mainPage.getElementByAltText("Bolt Cutters")).isVisible();
        PlaywrightAssertions.assertThat(mainPage.getBadgeByText("MightyCraft Hardware")).isVisible();
        PlaywrightAssertions.assertThat(mainPage.getBadgeByText("Pliers")).isVisible();
    }

    @Feature("Product Catalog")
    @Test
    @DisplayName("Check related products")
    void checkRelatedProduct(){
        mainPage.clickElementByAltText("Combination Pliers");
        PlaywrightAssertions.assertThat(mainPage.getElementByAltText("Slip Joint Pliers")).isVisible();
        PlaywrightAssertions.assertThat(mainPage.getElementByAltText("Bolt Cutters")).isVisible();
        PlaywrightAssertions.assertThat(mainPage.getElementByAltText("Long Nose Pliers")).isVisible();
    }

    @Feature("Product Catalog")
    @Test
    @DisplayName("Product images with titles should be shown")
    void shouldShowProductImages(){
        mainPage.checkProductImagesTitles(EXPECTED_PRODUCTS_MAIN_PAGE);
    }

    @Feature("Filter")
    @Test
    @DisplayName("Product should be filtered by category")
    void shouldFilterProductsByCategory(){
        leftNavigationPage.selectPowerToolsCategory();
        mainPage.checkProductNames(EXPECTED_POWER_TOOLS);
    }

    @Feature("Product Catalog")
    @Test
    @DisplayName("Clicking on Page logo redirects to main page")
    void clickingOnSiteLogoRedirectsToMainPage(){
        mainPage.clickElementByAltText("Combination Pliers");
        mainPage.clickElementByTitle("Practice Software Testing - Toolshop");
        mainPage.checkUrl(MAIN_URL);
    }

    @Feature("Shopping Cart")
    @Test
    @DisplayName("Clicking on Page logo redirects to main page")
    void shouldDisplayToasterMessage(){
        mainPage.clickElementByText("Bolt Cutters");
        productPage.addToCart();
        mainPage.waitForTheToasterMessage("Product added to shopping cart.");
        mainPage.waitForTheToasterMessageToDisappear();
    }

    @Feature("Shopping Cart")
    @Test
    void shouldUpdateCartItemCount(){
        mainPage.clickElementByText("Bolt Cutters");
        productPage.addToCart();
        mainPage.checkCartItemCount("1");
    }

    @Test
    @Disabled
    void waitForAPIResponse(){

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
