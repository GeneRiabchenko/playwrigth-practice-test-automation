package com.playwright.toolshop.pageObjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Assertions;

import static com.playwright.toolshop.resources.Resources.MAIN_URL;

@UsePlaywright
public class BasePage {
    private final Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    public void goToMainPage(){
        page.navigate(MAIN_URL);
    }

    public void clickElementByText(String textElement){
        page.getByText(textElement).click();
    }

    public Locator getElementByText(String textElement){
        return page.getByText(textElement);
    }

    public void clickElementByAltText(String textElement){
        page.getByAltText(textElement).click();
    }

    public Locator getElementByAltText(String textElement){
        return page.getByAltText(textElement);
    }

    public void clickElementByTitle(String textElement){
        page.getByTitle(textElement).click();
    }

    public void checkUrl(String url){
        Assertions.assertEquals(url, page.url());
    }

}

