package com.playwright.toolshop.pageObjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UsePlaywright
public class MainPage extends BasePage {
    private final Page page;

    protected final Locator SEARCH_FINISHED_STATE;
    protected final Locator SEARCH_FOR_LABEL;
    protected final Locator CARD_LOCATOR;
    protected final Locator BADGE_LOCATOR;
    protected final Locator PRODUCT_NAME;
    protected final Locator OUT_OF_STOCK;
    protected final Locator CARD_IMAGES;
    protected final Locator NAV_CATEGORIES;
    protected final Locator NAV_HAND_TOOLS;
    protected final Locator TOASTER_CONTAINER;
    protected final Locator TOASTER_MESSAGE;
    protected final Locator CART_QUANTITY;
    protected final Locator SEARCH_RESULT_COUNT;
    protected final Locator NO_RESULT_MESSAGE;
    protected final Locator PRODUCT_CONTAINER;

    public MainPage(Page page) {
        super(page);
        this.page = page;

        this.SEARCH_FINISHED_STATE = page.locator("[data-test=search_completed]");
        this.SEARCH_FOR_LABEL = page.getByTestId("search-caption");
        this.CARD_LOCATOR = page.locator(".card");
        this.BADGE_LOCATOR = page.locator(".badge");
        this.PRODUCT_NAME = page.getByTestId("product-name");
        this.OUT_OF_STOCK = page.getByTestId("out-of-stock");
        this.CARD_IMAGES = page.locator(".card-img-top");
        this.NAV_CATEGORIES = page.getByTestId("nav-categories");
        this.NAV_HAND_TOOLS = page.getByTestId("nav-hand-tools");
        this.TOASTER_CONTAINER = page.getByTestId("toast-container");
        this.TOASTER_MESSAGE = page.locator(".toast-message");
        this.CART_QUANTITY = page.getByTestId("cart-quantity");
        this.SEARCH_RESULT_COUNT = page.getByTestId("search-result-count");
        this.NO_RESULT_MESSAGE = page.getByTestId("no-results");
        this.PRODUCT_CONTAINER = page.locator(".container");
    }

    public List<String> getOutOfStockItems(){
        return CARD_LOCATOR.
                filter(new Locator.FilterOptions().setHas(OUT_OF_STOCK)).
                getByTestId("product-name").
                allInnerTexts();
    }

    public String getSearchForLabel(){
        return SEARCH_FOR_LABEL.innerText();
    }

    public int countSearchedResults(){
        return CARD_LOCATOR.count();
    }

    public Locator getBadgeByText(String textElement){
        return BADGE_LOCATOR.getByText(textElement);
    }

    public void checkProductNames(List<String> expectedProductName){
        page.waitForCondition(() -> PRODUCT_NAME.first().isVisible());
        Assertions.assertEquals(expectedProductName, PRODUCT_NAME.allInnerTexts());
    }

    public void checkProductImagesTitles(List<String> expectedProductImageTitles){
        page.waitForCondition(CARD_IMAGES.first()::isVisible);
        List<String> imagesTitles =  CARD_IMAGES.all()
                .stream()
                .map((img -> img.getAttribute("alt")))
                .toList();
        Assertions.assertEquals(expectedProductImageTitles, imagesTitles);
    }

    public void waitForTheToasterMessage(String toasterMessage){
       assertEquals(page.getByRole(AriaRole.ALERT).innerText(), toasterMessage);
    }

    public void waitForTheToasterMessageToDisappear(){
        page.waitForCondition(TOASTER_MESSAGE::isHidden);
    }

    public void checkCartItemCount(String expected){
        page.waitForCondition(() -> CART_QUANTITY.textContent().equals(expected));
    }

    public void checkSearchCountField(int count, String searchString){
        String resultCountString;
        if (count == 1) {
            resultCountString = String.format("%s product found for '%s'", count, searchString);
        } else {
            resultCountString = String.format("%s products found for '%s'", count, searchString);
        }
        assertEquals(resultCountString, SEARCH_RESULT_COUNT.innerText(), "Search count result doesn't match.");
    }

    public void isSearchCountFieldVisible(boolean isVisible){
        assertEquals(isVisible, SEARCH_RESULT_COUNT.isVisible());
    }

    public void isNoProductMessageShown(boolean isShown){
        assertEquals(isShown, NO_RESULT_MESSAGE.isVisible());
    }

    public void isFilteredProductByNameAndPriceVisible(String name, Double price, boolean isVisible){
        Locator productCard = CARD_LOCATOR.filter(
                new Locator.FilterOptions()
                        .setHasText(name)
                        .setHasText(price.toString())
        );
        assertEquals(isVisible, productCard.isVisible());
    }
}


