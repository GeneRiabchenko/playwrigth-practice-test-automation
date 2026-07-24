package com.playwright.toolshop.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static com.playwright.toolshop.testresources.Resources.MAIN_URL;
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

    @Override
    @Step("Return main page url")
    protected String getUrl() {
        return MAIN_URL;
    }

    @Step("Return out of stock product names")
    public List<String> getOutOfStockItems(){
        return CARD_LOCATOR.
                filter(new Locator.FilterOptions().setHas(OUT_OF_STOCK)).
                getByTestId("product-name").
                allInnerTexts();
    }

    @Step("Return search label on the product block")
    public String getSearchForLabel(){
        return SEARCH_FOR_LABEL.innerText();
    }

    @Step("Return count of the products")
    public int productCount(){
        return CARD_LOCATOR.count();
    }

    @Step("Return badge locator by text")
    public Locator getBadgeByText(String textElement){
        return BADGE_LOCATOR.getByText(textElement);
    }

    @Step("Compare product name with list of expected names")
    public void checkProductNames(List<String> expectedProductName){
        page.waitForCondition(() -> PRODUCT_NAME.first().isVisible());
        Assertions.assertEquals(expectedProductName, PRODUCT_NAME.allInnerTexts());
    }

    @Step("Check product image titles")
    public void checkProductImagesTitles(List<String> expectedProductImageTitles){
        page.waitForCondition(CARD_IMAGES.first()::isVisible);
        List<String> imagesTitles =  CARD_IMAGES.all()
                .stream()
                .map((img -> img.getAttribute("alt")))
                .toList();
        Assertions.assertEquals(expectedProductImageTitles, imagesTitles);
    }

    @Step("Check toaster message")
    public void waitForTheToasterMessage(String toasterMessage){
       assertEquals(page.getByRole(AriaRole.ALERT).innerText(), toasterMessage);
    }

    @Step("Check if toaster message will disappear after the certain amount of time")
    public void waitForTheToasterMessageToDisappear(){
        page.waitForCondition(TOASTER_MESSAGE::isHidden);
    }

    @Step("Check cart items count")
    public void checkCartItemCount(String expected){
        page.waitForCondition(() -> CART_QUANTITY.textContent().equals(expected));
    }

    @Step("Check count label for specified searched product")
    public void checkSearchCountField(int count, String searchString){
        String resultCountString;
        if (count == 1) {
            resultCountString = String.format("%s product found for '%s'", count, searchString);
        } else {
            resultCountString = String.format("%s products found for '%s'", count, searchString);
        }
        assertEquals(resultCountString, SEARCH_RESULT_COUNT.innerText(), "Search count result doesn't match.");
    }

    @Step("Check that search count is visible")
    public void isSearchCountFieldVisible(boolean isVisible){
        assertEquals(isVisible, SEARCH_RESULT_COUNT.isVisible());
    }

    @Step("Check that 'no results found' label is shown")
    public void isNoProductMessageShown(boolean isShown){
        assertEquals(isShown, NO_RESULT_MESSAGE.isVisible());
    }

    @Step("Check products is shown having specified name and price")
    public void isFilteredProductByNameAndPriceVisible(String name, Double price, boolean isVisible){
        Locator productCard = CARD_LOCATOR.filter(
                new Locator.FilterOptions()
                        .setHasText(name)
                        .setHasText(price.toString())
        );
        assertEquals(isVisible, productCard.isVisible());
    }

    @Step("Click on the product with exact specified name")
    public void openProductByName(String name){
        PRODUCT_NAME.getByText(name, new Locator.GetByTextOptions().setExact(true)).click();
    }

    @Step("Return the title of the page")
    public String getTitle(){
        PRODUCT_CONTAINER.first().waitFor();
        return page.title();
    }
}


