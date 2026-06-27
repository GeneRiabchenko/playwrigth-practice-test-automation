package com.playwright.toolshop.product.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.BasePage;


@UsePlaywright
public class ProductPage extends BasePage {
    private final Page page;
    private final Locator ADD_TO_THE_CART_BUTTON;

    public ProductPage(Page page) {
        super(page);
        this.page = page;
        this.ADD_TO_THE_CART_BUTTON = page.getByTestId("add-to-cart");
    }

    public void addToTheCart(){
        ADD_TO_THE_CART_BUTTON.click();
    }
}


