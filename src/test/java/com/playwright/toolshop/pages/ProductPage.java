package com.playwright.toolshop.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.utils.enums.Specs;
import io.qameta.allure.Step;

import static com.playwright.toolshop.testresources.Resources.MAIN_URL;


@UsePlaywright
public class ProductPage extends BasePage {
    private final Page page;
    private final Locator ADD_TO_CART_BUTTON;
    private final Locator ADD_TO_FAVORITES;
    private final Locator SPECIFICATIONS_TABLE;
    private final Locator SPECIFICATIONS_ROWS;

    public ProductPage(Page page) {
        super(page);
        this.page = page;
        this.ADD_TO_CART_BUTTON = page.getByTestId("add-to-cart");
        this.ADD_TO_FAVORITES = page.getByTestId("add-to-favorites");
        this.SPECIFICATIONS_TABLE = page.getByTestId("product-specs");
        this.SPECIFICATIONS_ROWS = page.getByTestId("spec-row");
    }

    @Override
    @Step("Return main url")
    protected String getUrl() {
        return MAIN_URL;
    }

    @Step("Click on the cart icon to add product to the cart")
    public void addToCart(){
        ADD_TO_CART_BUTTON.click();
    }

    @Step("Click on the favorites icon to add product to favorites")
    public void addToFavorites(){
        ADD_TO_FAVORITES.click();
    }

    @Step("Return value of the specification by name")
    public String getSpecValueByName(Specs specName){
        SPECIFICATIONS_TABLE.waitFor();
        return SPECIFICATIONS_ROWS
                .filter(new Locator.FilterOptions()
                        .setHas(page.getByText(specName.getSpecName(), new Page.GetByTextOptions().setExact(true))))
                .getByTestId("spec-value")
                .innerText();
    }
}


