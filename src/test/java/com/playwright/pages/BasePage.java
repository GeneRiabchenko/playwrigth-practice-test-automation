package com.playwright.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Assertions;

import static com.playwright.locators.MainPageLocators.*;
import static com.playwright.resources.Resources.MAIN_URL;

@UsePlaywright
public class BasePage {
    public static void goToMainPage(Page page){
        page.navigate(MAIN_URL);
    }

    public static void clickElementByText(String textElement, Page page){
        page.getByText(textElement).click();
    }

    public static Locator getElementByText(String textElement, Page page){
        return page.getByText(textElement);
    }

    public static void clickElementByAltText(String textElement, Page page){
        page.getByAltText(textElement).click();
    }

    public static Locator getElementByAltText(String textElement, Page page){
        return page.getByAltText(textElement);
    }

    public static void clickElementByTitle(String textElement, Page page){
        page.getByTitle(textElement).click();
    }

    public static void checkUrl(String url, Page page){
        Assertions.assertEquals(url, page.url());
    }

}

