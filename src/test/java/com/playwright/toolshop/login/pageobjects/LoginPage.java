package com.playwright.toolshop.login.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.utils.User;

import static com.playwright.toolshop.resources.Resources.SIGN_IN_URL;

@UsePlaywright
public class LoginPage {
    private final Page page;

    private final Locator EMAIl_ADDRESS_FIELD;
    private final Locator PASSWORD_FIELD;
    private final Locator LOGIN_BUTTON;
    private final Locator LOGIN_ERROR_MESSAGE;
    private final Locator PAGE_TITLE;

    public LoginPage(Page page) {
        this.page = page;
        this.EMAIl_ADDRESS_FIELD = page.getByTestId("email");
        this.PASSWORD_FIELD = page.getByTestId("password");
        this.LOGIN_BUTTON = page.getByTestId("login-submit");
        this.LOGIN_ERROR_MESSAGE = page.getByTestId("login-error");
        this.PAGE_TITLE = page.getByTestId("page-title");
    }

    public void open(){
        page.navigate(SIGN_IN_URL);
    }

    public void loginAs(User user){
        EMAIl_ADDRESS_FIELD.fill(user.email());
        PASSWORD_FIELD.fill(user.password());
        LOGIN_BUTTON.click();
    }

    public String loginError(){
        return LOGIN_ERROR_MESSAGE.innerText();
    }

    public String title() {
        return PAGE_TITLE.innerText();
    }

}
