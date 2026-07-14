package com.playwright.toolshop.utils;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.RequestOptions;

import static com.playwright.toolshop.resources.Resources.BASE_API_URL;

@UsePlaywright
public class UserAPIClient {
    private final Page page;

    private static final String REGISTER_USER = BASE_API_URL + "users/register";

    public UserAPIClient(Page page){
        this.page = page;
    }

    public void createUserViaAPI(User user) {
       var response = page.request().post(REGISTER_USER,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Accept", "application/json")
                        .setData(user)
        );
        if(response.status() != 201){
            throw new IllegalArgumentException("Could not create user: " + response.status());
        }
    }
}
