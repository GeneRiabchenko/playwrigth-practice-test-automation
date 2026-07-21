package com.playwright.toolshop.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Assertions;

@UsePlaywright
public abstract class BasePage {
    private final Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    protected abstract String getUrl();

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

    public void navigate(){
        page.navigate(getUrl());
    }

    public void navigate(String part){
        page.navigate(getUrl() + part);
    }

}

