package com.playwright.toolshop.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.SelectOption;
import com.playwright.toolshop.utils.User;
import net.serenitybdd.annotations.Step;
import org.junit.jupiter.api.Assertions;

import static com.playwright.toolshop.testresources.Resources.REGISTER_URL;
import static com.playwright.toolshop.testresources.Resources.SIGN_IN_URL;

@UsePlaywright
public class RegisterPage extends BasePage {
    private final Page page;

    private final Locator FIRST_NAME_FIELD;
    private final Locator LAST_NAME_FIELD;
    private final Locator DOB_FIELD;
    private final Locator COUNTRY_FIELD;
    private final Locator POSTCODE_FIELD;
    private final Locator HOUSE_NUMBER_FIELD;
    private final Locator STREET_FIELD;
    private final Locator CITY_FIELD;
    private final Locator STATE_FIELD;
    private final Locator PHONE_FIELD;
    private final Locator EMAIL_FIELD;
    private final Locator PASSWORD_FIELD;
    private final Locator REGISTER_BUTTON;
    private final Locator FIRST_NAME_FIELD_ERROR;
    private final Locator LAST_NAME_FIELD_ERROR;
    private final Locator DOB_FIELD_ERROR;
    private final Locator COUNTRY_FIELD_ERROR;
    private final Locator POSTCODE_FIELD_ERROR;
    private final Locator HOUSE_NUMBER_FIELD_ERROR;
    private final Locator STREET_FIELD_ERROR;
    private final Locator CITY_FIELD_ERROR;
    private final Locator STATE_FIELD_ERROR;
    private final Locator PHONE_FIELD_ERROR;
    private final Locator EMAIL_FIELD_ERROR;
    private final Locator PASSWORD_FIELD_ERROR;
    private final Locator REGISTER_ERROR;

    public RegisterPage(Page page) {
        super(page);
        this.page = page;
        this.FIRST_NAME_FIELD = page.getByTestId("first-name");
        this.LAST_NAME_FIELD = page.getByTestId("last-name");
        this.DOB_FIELD = page.getByTestId("dob");
        this.COUNTRY_FIELD = page.getByTestId("country");
        this.POSTCODE_FIELD = page.getByTestId("postal_code");
        this.HOUSE_NUMBER_FIELD = page.getByTestId("house_number");
        this.STREET_FIELD = page.getByTestId("street");
        this.CITY_FIELD = page.getByTestId("city");
        this.STATE_FIELD = page.getByTestId("state");
        this.PHONE_FIELD = page.getByTestId("phone");
        this.EMAIL_FIELD = page.getByTestId("email");
        this.PASSWORD_FIELD = page.getByTestId("password");
        this.REGISTER_BUTTON = page.getByTestId("register-submit");
        this.FIRST_NAME_FIELD_ERROR = page.getByTestId("first-name-error");
        this.LAST_NAME_FIELD_ERROR = page.getByTestId("last-name-error");
        this.DOB_FIELD_ERROR = page.getByTestId("dob-error");
        this.COUNTRY_FIELD_ERROR = page.getByTestId("country-error");
        this.POSTCODE_FIELD_ERROR = page.getByTestId("postal_code-error");
        this.HOUSE_NUMBER_FIELD_ERROR = page.getByTestId("house_number-error");
        this.STREET_FIELD_ERROR = page.getByTestId("street-error");
        this.CITY_FIELD_ERROR = page.getByTestId("city-error");
        this.STATE_FIELD_ERROR = page.getByTestId("state-error");
        this.PHONE_FIELD_ERROR = page.getByTestId("phone-error");
        this.EMAIL_FIELD_ERROR = page.getByTestId("email-error");
        this.PASSWORD_FIELD_ERROR = page.getByTestId("password-error");
        this.REGISTER_ERROR = page.getByTestId("register-error");
    }

    @Override
    @Step("Return registration page url")
    protected String getUrl() {
        return REGISTER_URL;
    }

    @Step("Fill the registration form with user details")
    public void fillForm(User user) {
        FIRST_NAME_FIELD.fill(user.first_name());
        LAST_NAME_FIELD.fill(user.last_name());
        DOB_FIELD.fill(user.dob());
        selectCountry(user.address().country());
        POSTCODE_FIELD.fill(user.address().postal_code());
        HOUSE_NUMBER_FIELD.fill(user.address().house_number());
        STREET_FIELD.fill(user.address().street());
        CITY_FIELD.fill(user.address().city());
        STATE_FIELD.fill(user.address().state());
        PHONE_FIELD.fill(user.phone());
        EMAIL_FIELD.fill(user.email());
        PASSWORD_FIELD.fill(user.password());
    }

    @Step("Fill the phone field with {0}")
    public void fillPhone(String phone) {
        PHONE_FIELD.fill(phone);
    }

    @Step("Fill the email field with {0}")
    public void fillEmail(String email) {
        EMAIL_FIELD.fill(email);
    }

    @Step("Fill the password field with {0}")
    public void fillPassword(String password) {
        PASSWORD_FIELD.fill(password);
    }

    @Step("Select country {0}")
    public void selectCountry(String country) {
        COUNTRY_FIELD.selectOption(new SelectOption().setLabel(country));
    }

    @Step("Click the register button")
    public void clickRegister() {
        REGISTER_BUTTON.click();
    }

    @Step("Check error message under the first name field")
    public void checkFirstNameErrorMessage(String expectedMessage) {
        Assertions.assertEquals(expectedMessage, FIRST_NAME_FIELD_ERROR.innerText().trim());
    }

    @Step("Check error message under the last name field")
    public void checkLastNameErrorMessage(String expectedMessage) {
        Assertions.assertEquals(expectedMessage, LAST_NAME_FIELD_ERROR.innerText().trim());
    }

    @Step("Check error message under the date of birth field")
    public void checkDobErrorMessage(String expectedMessage) {
        Assertions.assertEquals(expectedMessage, DOB_FIELD_ERROR.innerText().trim());
    }

    @Step("Check error message under the country field")
    public void checkCountryErrorMessage(String expectedMessage) {
        Assertions.assertEquals(expectedMessage, COUNTRY_FIELD_ERROR.innerText().trim());
    }

    @Step("Check error message under the postcode field")
    public void checkPostcodeErrorMessage(String expectedMessage) {
        Assertions.assertEquals(expectedMessage, POSTCODE_FIELD_ERROR.innerText().trim());
    }

    @Step("Check error message under the house number field")
    public void checkHouseNumberErrorMessage(String expectedMessage) {
        Assertions.assertEquals(expectedMessage, HOUSE_NUMBER_FIELD_ERROR.innerText().trim());
    }

    @Step("Check error message under the street field")
    public void checkStreetErrorMessage(String expectedMessage) {
        Assertions.assertEquals(expectedMessage, STREET_FIELD_ERROR.innerText().trim());
    }

    @Step("Check error message under the city field")
    public void checkCityErrorMessage(String expectedMessage) {
        Assertions.assertEquals(expectedMessage, CITY_FIELD_ERROR.innerText().trim());
    }

    @Step("Check error message under the state field")
    public void checkStateErrorMessage(String expectedMessage) {
        Assertions.assertEquals(expectedMessage, STATE_FIELD_ERROR.innerText().trim());
    }

    @Step("Check error message under the phone field")
    public void checkPhoneErrorMessage(String expectedMessage) {
        Assertions.assertEquals(expectedMessage, PHONE_FIELD_ERROR.innerText().trim());
    }

    @Step("Check error message under the email field")
    public void checkEmailErrorMessage(String expectedMessage) {
        Assertions.assertEquals(expectedMessage, EMAIL_FIELD_ERROR.innerText().trim());
    }

    @Step("Check error message under the password field")
    public void checkPasswordErrorMessage(String expectedMessage) {
        Assertions.assertEquals(expectedMessage, PASSWORD_FIELD_ERROR.innerText().trim());
    }

    @Step("Wait for redirect to the login page")
    public void waitForRedirectToLogin() {
        page.waitForURL(SIGN_IN_URL);
    }

    @Step("Check the registration error message")
    public void checkRegisterErrorMessage(String expectedMessage) {
        Assertions.assertEquals(expectedMessage, REGISTER_ERROR.innerText().trim());
    }
}
