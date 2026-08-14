package com.playwright.toolshop.tests.registration;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.HeadlessChromeOptions;
import com.playwright.toolshop.fixtures.BaseTestRunner;
import com.playwright.toolshop.pages.RegisterPage;
import com.playwright.toolshop.utils.User;
import com.playwright.toolshop.utils.UserAPIClient;
import net.serenitybdd.annotations.Feature;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.playwright.junit5.SerenityPlaywrightExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.playwright.toolshop.testresources.Resources.SIGN_IN_URL;

@ExtendWith(SerenityJUnit5Extension.class)
@ExtendWith(SerenityPlaywrightExtension.class)
@UsePlaywright(HeadlessChromeOptions.class)
public class RegistrationPageTests extends BaseTestRunner {
    @Steps
    RegisterPage registerPage;

    @Steps
    UserAPIClient userAPIClient;

    @BeforeEach
    public void setUp(Page page) {
        registerPage.navigate();
    }

    @Nested
    @Feature("Registration")
    class SuccessfulRegistration {

        @Test
        @DisplayName("Registering with valid details should redirect to the login page")
        void validUserShouldBeRegistered() {
            User newUser = User.randomUser();

            registerPage.fillForm(newUser);
            registerPage.selectCountry("United States of America (the)");
            registerPage.clickRegister();
            registerPage.waitForRedirectToLogin();

            registerPage.checkUrl(SIGN_IN_URL);
        }
    }

    @Nested
    @Feature("Registration")
    class PhoneNumberValidation {

        @ParameterizedTest(name = "Phone number {0} should be rejected")
        @ValueSource(strings = {
                "+1 555-123-4567",
                "(555) 123-4567",
                "555-123-4567",
                "+15551234567",
                "555 123 4567"
        })
        @DisplayName("Formatted phone numbers should be rejected")
        void formattedPhoneNumberShouldBeRejected(String phone) {
            User newUser = User.randomUser();

            registerPage.fillForm(newUser);
            registerPage.selectCountry("United States of America (the)");
            registerPage.fillPhone(phone);
            registerPage.clickRegister();

            registerPage.checkPhoneErrorMessage("Only numbers are allowed.");
        }
    }

    @Nested
    @Feature("Registration")
    class RequiredFieldValidation {

        @Test
        @DisplayName("Submitting an empty form should show required field errors")
        void emptyFormShouldShowRequiredFieldErrors() {
            registerPage.clickRegister();

            registerPage.checkFirstNameErrorMessage("First name is required");
            registerPage.checkLastNameErrorMessage("Last name is required");
            registerPage.checkDobErrorMessage("Please enter a valid date in YYYY-MM-DD format.\nDate of Birth is required");
            registerPage.checkCountryErrorMessage("Country is required");
            registerPage.checkPostcodeErrorMessage("Postcode is required");
            registerPage.checkHouseNumberErrorMessage("House number is required");
            registerPage.checkStreetErrorMessage("Street is required");
            registerPage.checkCityErrorMessage("City is required");
            registerPage.checkStateErrorMessage("State is required");
            registerPage.checkPhoneErrorMessage("Phone is required.");
            registerPage.checkEmailErrorMessage("Email is required");
            registerPage.checkPasswordErrorMessage("Password is required\nPassword must be minimal 6 characters long.\nPassword can not include invalid characters.");
        }
    }

    @Nested
    @Feature("Registration")
    class EmailValidation {

        @Test
        @DisplayName("An invalid email format should be rejected")
        void invalidEmailFormatShouldBeRejected() {
            User newUser = User.randomUser();

            registerPage.fillForm(newUser);
            registerPage.selectCountry("United States of America (the)");
            registerPage.fillEmail("not-an-email");
            registerPage.clickRegister();

            registerPage.checkEmailErrorMessage("Email format is invalid");
        }
    }

    @Nested
    @Feature("Registration")
    class PasswordValidation {

        @Test
        @DisplayName("A password shorter than 6 characters should be rejected")
        void tooShortPasswordShouldBeRejected() {
            User newUser = User.randomUser();

            registerPage.fillForm(newUser);
            registerPage.selectCountry("United States of America (the)");
            registerPage.fillPassword("ab1");
            registerPage.clickRegister();

            registerPage.checkPasswordErrorMessage("Password must be minimal 6 characters long.\nPassword can not include invalid characters.");
        }

        @Test
        @DisplayName("A password missing required character types should be rejected")
        void weakPasswordMissingComplexityShouldBeRejected() {
            User newUser = User.randomUser();

            registerPage.fillForm(newUser);
            registerPage.selectCountry("United States of America (the)");
            registerPage.fillPassword("password123");
            registerPage.clickRegister();

            registerPage.checkPasswordErrorMessage("Password can not include invalid characters.");
        }
    }

    @Nested
    @Feature("Registration")
    class DuplicateEmailValidation {

        @Test
        @DisplayName("Registering with an already registered email should show an error")
        void duplicateEmailShouldBeRejected() {
            User existingUser = User.randomUser();
            userAPIClient.createUserViaAPI(existingUser);

            User newUser = User.randomUser();
            registerPage.fillForm(newUser);
            registerPage.selectCountry("United States of America (the)");
            registerPage.fillEmail(existingUser.email());
            registerPage.clickRegister();

            registerPage.checkRegisterErrorMessage("A customer with this email address already exists.");
        }
    }
}
