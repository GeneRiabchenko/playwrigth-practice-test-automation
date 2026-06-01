package com.playwright.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static com.playwright.locators.MainPageLocators.*;

@UsePlaywright
public class MainPage extends BasePage{

    public static void search(String searchString, Page page){
        page.getByTestId(SEARCH_INPUT).clear();
        page.getByTestId(SEARCH_INPUT).fill(searchString);
        page.getByTestId(SEARCH_BUTTON).click();
        page.waitForSelector(SEARCH_FINISHED_STATE);
    }

    public static List<String> getOutOfStockItems(Page page){
        return page.locator(CARD_LOCATOR).
                filter(new Locator.FilterOptions().setHas(page.getByTestId(OUT_OF_STOCK))).
                getByTestId(PRODUCT_NAME).
                allInnerTexts();
    }

    public static String getSearchForLabel(Page page){
        return page.getByTestId(SEARCH_FOR_LABEL).innerText();
    }

    public static int countSearchedResults(Page page){
        return page.locator(CARD_LOCATOR).count();
    }


    public static Locator getBadgeByText(String textElement, Page page){
        return page.locator(BADGE_LOCATOR).getByText(textElement);
    }

    public static void checkProductNames(List<String> expectedProductName, Page page){
        Assertions.assertEquals(expectedProductName, page.getByTestId(PRODUCT_NAME).allInnerTexts());
    }

    public static void checkProductImagesTitles(List<String> expectedProductImageTitles, Page page){
        page.waitForSelector(CARD_IMAGES);
        List<String> imagesTitles =  page.locator(CARD_IMAGES).all()
                .stream()
                .map((img -> img.getAttribute("alt")))
                .toList();
        Assertions.assertEquals(expectedProductImageTitles, imagesTitles);
    }
}


