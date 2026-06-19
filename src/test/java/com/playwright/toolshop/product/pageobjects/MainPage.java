package com.playwright.toolshop.product.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.playwright.toolshop.BasePage;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static com.playwright.toolshop.product.locators.MainPageLocators.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@UsePlaywright
public class MainPage extends BasePage {
    private final Page page;

    public MainPage(Page page) {
        super(page);
        this.page = page;
    }

    public List<String> getOutOfStockItems(){
        return page.locator(CARD_LOCATOR).
                filter(new Locator.FilterOptions().setHas(page.getByTestId(OUT_OF_STOCK))).
                getByTestId(PRODUCT_NAME).
                allInnerTexts();
    }

    public String getSearchForLabel(){
        return page.getByTestId(SEARCH_FOR_LABEL).innerText();
    }

    public int countSearchedResults(){
        return page.locator(CARD_LOCATOR).count();
    }


    public Locator getBadgeByText(String textElement){
        return page.locator(BADGE_LOCATOR).getByText(textElement);
    }

    public void checkProductNames(List<String> expectedProductName){
        page.waitForCondition(() -> page.getByTestId(PRODUCT_NAME).first().isVisible());
        Assertions.assertEquals(expectedProductName, page.getByTestId(PRODUCT_NAME).allInnerTexts());
    }

    public void checkProductImagesTitles(List<String> expectedProductImageTitles){
        page.waitForSelector(CARD_IMAGES);
        List<String> imagesTitles =  page.locator(CARD_IMAGES).all()
                .stream()
                .map((img -> img.getAttribute("alt")))
                .toList();
        Assertions.assertEquals(expectedProductImageTitles, imagesTitles);
    }

    public void waitForTheToasterMessage(String toasterMessage){
       assertEquals(page.getByRole(AriaRole.ALERT).innerText(), toasterMessage);
    }

    public void waitForTheToasterMessageToDisappear(){
        page.waitForCondition(() -> page.locator(TOASTER_MESSAGE).isHidden());
    }

    public void checkCartItemCount(String expected){
        page.waitForCondition(() -> page.getByTestId(CART_QUANTITY).textContent().equals(expected));
    }

    public void checkSearchCountField(int count, String searchString){
        String resultCountString;
        if (count == 1) {
            resultCountString = String.format("%s product found for '%s'", count, searchString);
        } else {
            resultCountString = String.format("%s products found for '%s'", count, searchString);
        }
        assertEquals(resultCountString, page.getByTestId(SEARCH_RESULT_COUNT).innerText(), "Search count result doesn't match.");
    }

    public void isSearchCountFieldVisible(boolean isVisible){
        assertEquals(isVisible, page.getByTestId(SEARCH_RESULT_COUNT).isVisible());
    }

    public void isNoProductMessageShown(boolean isShown){
        assertEquals(isShown, page.getByTestId(NO_RESULT_MESSAGE).isVisible());
    }

    public void isFilteredProductByNameAndPriceVisible(String name, Double price, boolean isVisible){
        Locator productCard = page.locator(CARD_LOCATOR).filter(
                new Locator.FilterOptions()
                        .setHasText(name)
                        .setHasText(price.toString())
        );
        assertEquals(isVisible, productCard.isVisible());
    }
}


