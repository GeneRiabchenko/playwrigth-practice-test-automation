package com.playwright.toolshop.tests.cart;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.HeadlessChromeOptions;
import com.playwright.toolshop.fixtures.BaseTestRunner;
import com.playwright.toolshop.pages.CartPage;
import com.playwright.toolshop.pages.LoginPage;
import com.playwright.toolshop.pages.MainPage;
import com.playwright.toolshop.pages.ProductPage;
import com.playwright.toolshop.utils.ProductSummary;
import net.serenitybdd.annotations.Feature;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.playwright.junit5.SerenityPlaywrightExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static com.playwright.toolshop.testresources.Resources.MAIN_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SerenityJUnit5Extension.class)
@ExtendWith(SerenityPlaywrightExtension.class)
@UsePlaywright(HeadlessChromeOptions.class)
public class CartTests extends BaseTestRunner {
    @Steps MainPage mainPage;
    @Steps ProductPage productPage;
    @Steps CartPage cartPage;
    @Steps LoginPage loginPage;

    @BeforeEach
    public void setUp(Page page) {
        mainPage.navigate();
    }

    @Nested
    @Feature("Cart")
    class AddingProducts {

        @Test
        @DisplayName("Adding a product should update the cart badge")
        void addingProductShouldUpdateCartBadge() {
            mainPage.openProductByName("Combination Pliers");
            productPage.addToCart();
            mainPage.checkCartItemCount("1");
        }

        @Test
        @DisplayName("Cart should display the added product with its price")
        void cartShouldDisplayAddedProduct() {
            mainPage.openProductByName("Combination Pliers");
            productPage.addToCart();
            mainPage.checkCartItemCount("1");

            cartPage.navigate();
            assertEquals(List.of(new ProductSummary("Combination Pliers", "$14.15")), cartPage.getProductSummaries());
        }

        @Test
        @DisplayName("Cart total should sum the prices of multiple different products")
        void cartTotalShouldSumMultipleProducts() {
            mainPage.openProductByName("Combination Pliers");
            productPage.addToCart();
            mainPage.checkCartItemCount("1");
            mainPage.navigate();
            mainPage.openProductByName("Pliers");
            productPage.addToCart();
            mainPage.checkCartItemCount("2");

            cartPage.navigate();
            cartPage.waitForCartTotal("$26.16");
            assertEquals("$26.16", cartPage.getCartTotal());
        }
    }

    @Nested
    @Feature("Cart")
    class UpdatingQuantity {

        @Test
        @DisplayName("Updating quantity in the cart should update the line total and the cart total")
        void updatingQuantityShouldUpdateLineAndCartTotal() {
            mainPage.openProductByName("Combination Pliers");
            productPage.addToCart();
            mainPage.checkCartItemCount("1");

            cartPage.navigate();
            cartPage.setProductQuantity("Combination Pliers", "3");
            assertEquals("3", cartPage.getProductQuantity("Combination Pliers"));
            assertEquals("$42.45", cartPage.getLineTotal("Combination Pliers"));
            cartPage.waitForCartTotal("$42.45");
            assertEquals("$42.45", cartPage.getCartTotal());
        }
    }

    @Nested
    @Feature("Cart")
    class RemovingProducts {

        @Test
        @DisplayName("Removing the only product from the cart should show the empty cart message")
        void removingOnlyProductShouldShowEmptyCartMessage() {
            mainPage.openProductByName("Combination Pliers");
            productPage.addToCart();
            mainPage.checkCartItemCount("1");

            cartPage.navigate();
            cartPage.removeProduct("Combination Pliers");
            assertTrue(cartPage.isCartEmpty());
            assertEquals("The cart is empty. Nothing to display.", cartPage.getEmptyCartMessage());
        }
    }

    @Nested
    @Feature("Cart")
    class Navigation {

        @Test
        @DisplayName("Continue shopping should return to the home page")
        void continueShoppingShouldReturnToHomePage() {
            mainPage.openProductByName("Combination Pliers");
            productPage.addToCart();
            mainPage.checkCartItemCount("1");

            cartPage.navigate();
            cartPage.clickContinueShopping();
            mainPage.checkUrl(MAIN_URL);
        }

        @Test
        @DisplayName("Proceeding to checkout as a guest should prompt sign in")
        void proceedToCheckoutShouldPromptSignInForGuest() {
            mainPage.openProductByName("Combination Pliers");
            productPage.addToCart();
            mainPage.checkCartItemCount("1");

            cartPage.navigate();
            cartPage.clickProceedToCheckout();
            assertTrue(loginPage.isLoginFormVisible());
        }
    }
}
