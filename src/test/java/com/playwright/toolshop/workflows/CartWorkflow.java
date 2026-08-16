package com.playwright.toolshop.workflows;

import com.playwright.toolshop.pages.CartPage;
import com.playwright.toolshop.pages.MainPage;
import com.playwright.toolshop.pages.ProductPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.annotations.Steps;

public class CartWorkflow {
    @Steps MainPage mainPage;
    @Steps ProductPage productPage;
    @Steps CartPage cartPage;

    @Step("Add {0} to the cart")
    public void addProductToCart(String productName) {
        mainPage.openProductByName(productName);
        productPage.addToCart();
    }

    @Step("Add {0} to the cart and open the cart page")
    public void addProductAndGoToCart(String productName) {
        addProductToCart(productName);
        mainPage.checkCartItemCount("1");
        cartPage.navigate();
    }
}
