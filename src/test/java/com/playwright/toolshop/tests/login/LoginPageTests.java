package com.playwright.toolshop.tests.login;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.HeadlessChromeOptions;
import com.playwright.toolshop.fixtures.BaseTestRunner;
import com.playwright.toolshop.pages.LoginPage;
import com.playwright.toolshop.utils.User;
import com.playwright.toolshop.utils.UserAPIClient;
import com.playwright.toolshop.utils.UserBuilder;
import net.serenitybdd.annotations.Feature;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Story;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.playwright.junit5.SerenityPlaywrightExtension;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
@ExtendWith(SerenityPlaywrightExtension.class)
@UsePlaywright(HeadlessChromeOptions.class)
@Feature("Login")
public class LoginPageTests extends BaseTestRunner {
    @Steps
    LoginPage loginPage;

    @Steps
    UserAPIClient userAPIClient;

    @BeforeEach
    public void setUp(Page page){
        loginPage.navigate();
    }

    @Story("Rejecting invalid login attempts")
    @Test
    void invalidUserShouldNotBeLoggedIn(){
        User randomUser = UserBuilder.aUser().build();

        loginPage.loginAs(randomUser);
        Assertions.assertThat(loginPage.loginError()).isEqualTo("Invalid email or password");
    }

    @Story("Logging in with valid credentials")
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

    @Story("Logging in with valid credentials")
    @Test
    void newValidUserShouldNotBeLoggedIn(){
        User validUser = UserBuilder.aUser().build();
        userAPIClient.createUserViaAPI(validUser);
        loginPage.loginAs(validUser);
        Assertions.assertThat(loginPage.title()).isEqualTo("My account");
    }

}
