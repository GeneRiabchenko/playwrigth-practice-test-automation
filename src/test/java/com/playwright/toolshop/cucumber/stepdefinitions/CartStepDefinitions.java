package com.playwright.toolshop.cucumber.stepdefinitions;

import com.playwright.toolshop.pages.CartPage;
import com.playwright.toolshop.pages.LoginPage;
import com.playwright.toolshop.pages.MainPage;
import com.playwright.toolshop.pages.ProductPage;
import com.playwright.toolshop.utils.ProductSummary;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static com.playwright.toolshop.testresources.Resources.MAIN_URL;

public class CartStepDefinitions {

    MainPage mainPage;
    ProductPage productPage;
    CartPage cartPage;
    LoginPage loginPage;

    @Before
    public void setUpPageObjects(){
        mainPage = new MainPage(PlaywrightCucumberFixtures.getPage());
        productPage = new ProductPage(PlaywrightCucumberFixtures.getPage());
        cartPage = new CartPage(PlaywrightCucumberFixtures.getPage());
        loginPage = new LoginPage(PlaywrightCucumberFixtures.getPage());
    }

    @When("Actor adds {string} to the cart")
    public void actorAddsToTheCart(String productName) {
        mainPage.openProductByName(productName);
        productPage.addToCart();
        mainPage.clickHomeLink();
    }

    @And("Actor is on the cart page")
    public void actorIsOnTheCartPage() {
        cartPage.navigate();
    }

    @Then("the cart badge should show {string}")
    public void theCartBadgeShouldShow(String expectedCount) {
        mainPage.checkCartItemCount(expectedCount);
    }

    @And("the cart should contain the following products:")
    public void theCartShouldContainTheFollowingProducts(List<ProductSummary> expectedProducts) {
        Assertions.assertEquals(expectedProducts, cartPage.getProductSummaries());
    }

    @And("the cart total should be {string}")
    public void theCartTotalShouldBe(String expectedTotal) {
        cartPage.waitForCartTotal(expectedTotal);
        Assertions.assertEquals(expectedTotal, cartPage.getCartTotal());
    }

    @And("Actor changes the quantity of {string} to {string}")
    public void actorChangesTheQuantityOfTo(String productName, String quantity) {
        cartPage.setProductQuantity(productName, quantity);
    }

    @Then("the line total for {string} should be {string}")
    public void theLineTotalForShouldBe(String productName, String expectedLineTotal) {
        Assertions.assertEquals(expectedLineTotal, cartPage.getLineTotal(productName));
    }

    @And("Actor removes {string} from the cart")
    public void actorRemovesFromTheCart(String productName) {
        cartPage.removeProduct(productName);
    }

    @Then("the message {string} should be displayed in the cart")
    public void theMessageShouldBeDisplayedInTheCart(String expectedMessage) {
        Assertions.assertTrue(cartPage.isCartEmpty());
        Assertions.assertEquals(expectedMessage, cartPage.getEmptyCartMessage());
    }

    @And("Actor clicks continue shopping")
    public void actorClicksContinueShopping() {
        cartPage.clickContinueShopping();
    }

    @Then("Actor should be on the home page")
    public void actorShouldBeOnTheHomePage() {
        mainPage.checkUrl(MAIN_URL);
    }

    @And("Actor clicks proceed to checkout")
    public void actorClicksProceedToCheckout() {
        cartPage.clickProceedToCheckout();
    }

    @Then("the sign in form should be displayed")
    public void theSignInFormShouldBeDisplayed() {
        Assertions.assertTrue(loginPage.isLoginFormVisible());
    }
}
