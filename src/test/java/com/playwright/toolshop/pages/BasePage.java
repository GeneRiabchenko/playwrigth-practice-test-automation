package com.playwright.toolshop.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

@UsePlaywright
public abstract class BasePage {
    private final Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    protected abstract String getUrl();

    @Step("Click on element with text")
    public void clickElementByText(String textElement){
        page.getByText(textElement).click();
    }

    public Locator getElementByText(String textElement){
        return page.getByText(textElement);
    }

    @Step("Click on element with alt text")
    public void clickElementByAltText(String textElement){
        page.getByAltText(textElement).click();
    }

    @Step("Get element by alt text")
    public Locator getElementByAltText(String textElement){
        return page.getByAltText(textElement);
    }

    @Step("Click on element by title")
    public void clickElementByTitle(String textElement){
        page.getByTitle(textElement).click();
    }

    @Step("Check page URL")
    public void checkUrl(String url){
        Assertions.assertEquals(url, page.url());
    }

    @Step("Go to page")
    public void navigate(){
        page.navigate(getUrl());
    }

    @Step("Check page with {0}")
    public void navigate(String part){
        page.navigate(getUrl() + part);
    }

}

