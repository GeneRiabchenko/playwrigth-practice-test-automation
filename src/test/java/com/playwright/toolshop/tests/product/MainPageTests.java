package com.playwright.toolshop.tests.product;

import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.HeadlessChromeOptions;
import com.playwright.toolshop.fixtures.BaseTestRunner;
import com.playwright.toolshop.pages.LeftNavigationPage;
import com.playwright.toolshop.pages.MainPage;
import com.playwright.toolshop.workflows.CartWorkflow;
import net.serenitybdd.annotations.Feature;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Story;
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
    @Steps LeftNavigationPage leftNavigationPage;
    @Steps CartWorkflow cartWorkflow;

    @BeforeEach
    void openHomePage(){
        mainPage.navigate();
    }

    @Nested
    @Feature("Product Catalog")
    class ProductCatalog {

        @Story("Viewing the main page")
        @Test
        @DisplayName("Correct title should be shown on the main page")
        void shouldShowTitle(){
            Assertions.assertEquals("Practice Software Testing - Toolshop - v5.0", mainPage.getTitle());
        }

        @Story("Searching for products")
        @Test
        @DisplayName("Correct searched products should be shown")
        void checkSearchFeature(){
            leftNavigationPage.search("pliers");
            Assertions.assertEquals("Searched for: pliers", mainPage.getSearchForLabel());
            Assertions.assertEquals(4, mainPage.productCount());
            mainPage.checkProductNames(EXPECTED_PRODUCTS_PLIERS);
        }

        @Story("Searching for products")
        @Test
        @DisplayName("Out of stock products should not be filtered by search")
        void checkOutOfStockItemsAreNotFilteredBySearch(){
            leftNavigationPage.search("pliers");
            Assertions.assertEquals("Searched for: pliers", mainPage.getSearchForLabel());
            Assertions.assertEquals(4, mainPage.productCount());
            Assertions.assertEquals(EXPECTED_OUT_OF_STOCK_PRODUCTS, mainPage.getOutOfStockItems());
            mainPage.checkProductNames(EXPECTED_PRODUCTS_PLIERS);
        }

        @Story("Viewing product details from the catalog")
        @Test
        @DisplayName("Out of stock products should not be filtered by search")
        void checkThatProductPageIsOpenedAndBadgesAreShown(){
            mainPage.clickElementByText("Bolt Cutters");
            PlaywrightAssertions.assertThat(mainPage.getElementByAltText("Bolt Cutters")).isVisible();
            PlaywrightAssertions.assertThat(mainPage.getBadgeByText("MightyCraft Hardware")).isVisible();
            PlaywrightAssertions.assertThat(mainPage.getBadgeByText("Pliers")).isVisible();
        }

        @Story("Viewing related products")
        @Test
        @DisplayName("Check related products")
        void checkRelatedProduct(){
            mainPage.clickElementByAltText("Combination Pliers");
            PlaywrightAssertions.assertThat(mainPage.getElementByAltText("Slip Joint Pliers")).isVisible();
            PlaywrightAssertions.assertThat(mainPage.getElementByAltText("Bolt Cutters")).isVisible();
            PlaywrightAssertions.assertThat(mainPage.getElementByAltText("Long Nose Pliers")).isVisible();
        }

        @Story("Viewing the main page")
        @Test
        @DisplayName("Product images with titles should be shown")
        void shouldShowProductImages(){
            mainPage.checkProductImagesTitles(EXPECTED_PRODUCTS_MAIN_PAGE);
        }

        @Story("Site navigation")
        @Test
        @DisplayName("Clicking on Page logo redirects to main page")
        void clickingOnSiteLogoRedirectsToMainPage(){
            mainPage.clickElementByAltText("Combination Pliers");
            mainPage.clickElementByTitle("Practice Software Testing - Toolshop");
            mainPage.checkUrl(MAIN_URL);
        }

        @Story("Sorting products by price")
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
    }

    @Nested
    @Feature("Filter")
    class Filter {

        @Story("Filtering products by category")
        @Test
        @DisplayName("Product should be filtered by category")
        void shouldFilterProductsByCategory(){
            leftNavigationPage.selectPowerToolsCategory();
            mainPage.checkProductNames(EXPECTED_POWER_TOOLS);
        }
    }

    @Nested
    @Feature("Shopping Cart")
    class ShoppingCart {

        @Story("Adding products to the cart")
        @Test
        @DisplayName("Clicking on Page logo redirects to main page")
        void shouldDisplayToasterMessage(){
            cartWorkflow.addProductToCart("Bolt Cutters");
            mainPage.waitForTheToasterMessage("Product added to shopping cart.");
            mainPage.waitForTheToasterMessageToDisappear();
        }

        @Story("Adding products to the cart")
        @Test
        void shouldUpdateCartItemCount(){
            cartWorkflow.addProductToCart("Bolt Cutters");
            mainPage.checkCartItemCount("1");
        }
    }

    private static double extractPrice(String price){
        return Double.parseDouble(price.replace("$",""));
    }
}
