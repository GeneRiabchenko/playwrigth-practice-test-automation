package com.playwright.toolshop.cucumber.stepdefinitions;

import com.playwright.toolshop.pages.LeftNavigationPage;
import com.playwright.toolshop.pages.MainPage;
import com.playwright.toolshop.utils.ProductSummary;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;

public class ProductCatalogStepDefinitions {

    LeftNavigationPage navBar;
    MainPage mainPage;

    @Before
    public void setUpPageObjects(){
        navBar = new LeftNavigationPage(PlaywrightCucumberFixtures.getPage());
        mainPage = new MainPage(PlaywrightCucumberFixtures.getPage());
    }

    @Given("Actor is on the home page")
    public void actor_is_on_the_home_page() {
        navBar.navigate();
    }
    @When("he searches for {string}")
    public void he_searches_for(String searchString) {
        navBar.search(searchString);
    }

    @Then("the {string} product should be displayed")
    public void the_product_should_be_displayed(String productName) {
        mainPage.isSearchedProductDisplayed(productName);
    }

    @DataTableType
    public ProductSummary productSummaryRow(Map<String, String> productData){
        return new ProductSummary(productData.get("Product"), productData.get("Price"));
    }

    @Then("the following products are shown:")
    public void theFollowingProductsAreShown(List<ProductSummary> expectedProductSummaries) {
        List<ProductSummary> matchingProducts = mainPage.getProductSummaries();
//       List<Map<String,String>> expectedProductData = expectedProducts.asMaps();
//        List<ProductSummary> expectedProductSummaries = expectedProductData
//                .stream()
//                .map(productData -> new ProductSummary(
//                        productData.get("Product"),
//                        productData.get("Price")
//                        )).toList();
        Assertions.assertEquals(expectedProductSummaries, matchingProducts);
    }

    @Then("no products should be displayed")
    public void noProductsShouldBeDisplayed() {
        Assertions.assertEquals(0, mainPage.productCount());
    }

    @And("the message {string} should be displayed")
    public void theMessageShouldBeDisplayed(String expectedNoResultMessage) {
        mainPage.isNoProductMessageShown(true);
        Assertions.assertEquals(expectedNoResultMessage, mainPage.getNoResultMessage());
    }

    @When("Actor selects following categories:")
    public void actorSelectsFollowingCategories(List<String> categories) {
        navBar.selectCategory(categories);
    }
}
