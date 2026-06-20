package com.playwright.toolshop.search.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.playwright.toolshop.product.pageobjects.MainPage;

import static com.playwright.toolshop.resources.Resources.PRODUCTS_REQUEST_URL;
import static com.playwright.toolshop.search.locators.LeftNavigationLocators.*;

public class LeftNavigationPage extends MainPage {
    private final Page page;

    public LeftNavigationPage(Page page) {
        super(page);
        this.page = page;
    }

    public void clickSearchButton(){
        page.getByTestId(SEARCH_BUTTON).click();
    }

    public void clickCrossButton(){
        page.waitForResponse(PRODUCTS_REQUEST_URL, () -> page.getByTestId(CROSS_BUTTON).click());
    }

    public void fillSearchField(String searchString){
        page.getByTestId(SEARCH_INPUT).clear();
        page.getByTestId(SEARCH_INPUT).fill(searchString);
    }

    public void clearSearchField(){
        page.getByTestId(SEARCH_INPUT).clear();
    }

    public void search(String searchString){
        page.getByTestId(SEARCH_INPUT).clear();
        page.getByTestId(SEARCH_INPUT).fill(searchString);
        page.getByTestId(SEARCH_BUTTON).click();
        if (searchString != null && !searchString.isEmpty()) {
            page.waitForCondition(SEARCH_FINISHED_STATE::isVisible);
        }
    }

    public void selectPowerToolsCategory(){
        page.getByRole(AriaRole.MENUBAR).getByText("Categories").click();
        page.getByRole(AriaRole.MENUBAR).getByText("Power Tools").click();
        page.waitForCondition(CARD_IMAGES.first()::isVisible);
    }
}
