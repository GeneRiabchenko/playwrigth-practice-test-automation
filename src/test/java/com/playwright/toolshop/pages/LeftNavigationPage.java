package com.playwright.toolshop.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.playwright.toolshop.testresources.Resources.PRODUCTS_REQUEST_URL;

public class LeftNavigationPage extends MainPage {
    private final Page page;
    private final Locator SORT_BY_DROPDOWN;
    private final Locator SEARCH_INPUT;
    private final Locator SEARCH_BUTTON;
    private final Locator CROSS_BUTTON;

    public LeftNavigationPage(Page page) {
        super(page);
        this.page = page;
        this.SORT_BY_DROPDOWN = page.getByTestId("sort");
        this.SEARCH_INPUT = page.getByTestId("search-query");
        this.SEARCH_BUTTON = page.getByTestId("search-submit");
        this.CROSS_BUTTON = page.getByTestId("search-reset");
    }

    public void clickSearchButton(){
        SEARCH_BUTTON.click();
    }

    public void clickCrossButton(){
        page.waitForResponse(PRODUCTS_REQUEST_URL, CROSS_BUTTON::click);
    }

    public void fillSearchField(String searchString){
        SEARCH_INPUT.clear();
        SEARCH_INPUT.fill(searchString);
    }

    public void clearSearchField(){
        SEARCH_INPUT.clear();
    }

    public void search(String searchString){
        SEARCH_INPUT.clear();
        SEARCH_INPUT.fill(searchString);
        SEARCH_BUTTON.click();
        if (searchString != null && !searchString.isEmpty()) {
            page.waitForCondition(SEARCH_FINISHED_STATE::isVisible);
        }
    }

    public void selectPowerToolsCategory(){
        page.getByRole(AriaRole.MENUBAR).getByText("Categories").click();
        page.getByRole(AriaRole.MENUBAR).getByText("Power Tools").click();
        page.waitForCondition(CARD_IMAGES.first()::isVisible);
    }

    public void selectSortBy(String sortBy){
        SORT_BY_DROPDOWN.selectOption(sortBy);
    }
}
