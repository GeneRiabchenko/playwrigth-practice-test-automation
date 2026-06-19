package com.playwright.toolshop.product.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.BasePage;

import static com.playwright.toolshop.product.locators.ProductPageLocators.ADD_TO_THE_CART_BUTTON;

@UsePlaywright
public class ProductPage extends BasePage {
    private final Page page;
    public ProductPage(Page page) {
        super(page);
        this.page = page;
    }

    public void addToTheCart(){
        page.getByTestId(ADD_TO_THE_CART_BUTTON).click();
    }
}


