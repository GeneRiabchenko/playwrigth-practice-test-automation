package com.playwright.toolshop.tests.contact;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.RequestOptions;
import com.playwright.toolshop.utils.Address;
import com.playwright.toolshop.utils.User;
import net.serenitybdd.annotations.Feature;
import net.serenitybdd.annotations.Story;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.playwright.junit5.SerenityPlaywrightExtension;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.playwright.toolshop.testresources.Resources.BASE_API_URL;

@ExtendWith(SerenityJUnit5Extension.class)
@ExtendWith(SerenityPlaywrightExtension.class)
@UsePlaywright
public class ContactAPITest {
    private APIRequestContext request;

    @BeforeEach
    void setup(Playwright playwright){
        request = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL(BASE_API_URL)
        );
    }

    @AfterEach
    void tearDown(){
        if (request != null){
            request.dispose();
        }
    }

    @Story("Contact EPIC")
    @Feature("Creating new user api")
    @Test
    @DisplayName("Valid user should be created via API")
    void validUserIsCreated(){
        Gson gson = new Gson();
        User validUser = User.randomUser();

        var response = createUser(validUser);
        String responseBody = response.text();
        User createdUser = gson.fromJson(responseBody, User.class);
        JsonObject responseObject = gson.fromJson(responseBody, JsonObject.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.status())
                    .as("Registration should return 201 code")
                    .isEqualTo(201);
            softly.assertThat(createdUser)
                    .as("Created user should match specified user without the password")
                    .isEqualTo(validUser.withPassword(null));
            softly.assertThat(responseObject.has("password"))
                    .as("Password is not returned in the response")
                    .isFalse();
            softly.assertThat(responseObject.get("id").getAsString())
                    .as("Id field is returned in the response")
                    .isNotEmpty();
        });

    }

    @Story("Contact EPIC")
    @Feature("Creating new user API")
    @Test
    @DisplayName("First name field is mandatory for user creation via API")
    void firstNameIsMandatory(){
        User userWithNoName = new User(
                null,
                "Jordan",
                Address.randomAddress(),
                "0994355787",
                "1986-05-23",
                "SESROCC12345!!!",
                "someEmail@mail.com"
        );

        Gson gson = new Gson();
        var response = createUser(userWithNoName);
        JsonObject responseObject = gson.fromJson(response.text(), JsonObject.class);

        SoftAssertions.assertSoftly(softly ->{
            softly.assertThat(response.status())
                    .as("Registration should return 422 code")
                    .isEqualTo(422);
            softly.assertThat(responseObject.get("first_name").getAsString())
                    .as("Error in the first name field")
                    .isEqualTo("The first name field is required.");
        });
    }

    private APIResponse createUser(User user){
        return request.post("users/register",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(user)
        );
    }
}
