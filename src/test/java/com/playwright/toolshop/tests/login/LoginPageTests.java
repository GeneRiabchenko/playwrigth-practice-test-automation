package com.playwright.toolshop.tests.login;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.HeadlessChromeOptions;
import com.playwright.toolshop.pages.LoginPage;
import com.playwright.toolshop.tests.BaseTest;
import com.playwright.toolshop.utils.User;
import com.playwright.toolshop.utils.UserAPIClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UsePlaywright(HeadlessChromeOptions.class)
public class LoginPageTests extends BaseTest {
    LoginPage loginPage;
    UserAPIClient userAPIClient;

    @BeforeEach
    public void setUp(Page page){
        loginPage = new LoginPage(page);
        loginPage.open();
    }

    @Test
    void invalidUserShouldNotBeLoggedIn(){
        User randomUser = User.randomUser();

        loginPage.loginAs(randomUser);
        Assertions.assertThat(loginPage.loginError()).isEqualTo("Invalid email or password");
    }

    @Test
    void validUserShouldNotBeLoggedIn(){
        User validUser = new User(null,
                null,
                null,
                null,
                null,
                "welcome01",
                "customer@practicesoftwaretesting.com");

        loginPage.loginAs(validUser);
        Assertions.assertThat(loginPage.title()).isEqualTo("My account");
    }

    @Test
    void newValidUserShouldNotBeLoggedIn(Page page){
        userAPIClient = new UserAPIClient(page);
        User validUser = User.randomUser();
        userAPIClient.createUserViaAPI(validUser);

        loginPage.loginAs(validUser);
        Assertions.assertThat(loginPage.title()).isEqualTo("My account");
    }

}
