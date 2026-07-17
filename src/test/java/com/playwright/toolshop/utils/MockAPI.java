package com.playwright.toolshop.utils;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Route;

public class MockAPI {
    private final Page page;

    public MockAPI(Page page) {
        this.page = page;
    }

    public void mockRequest(String url, String body, int statusCode){
        page.route(url, route ->
                route.fulfill(
                        new Route.FulfillOptions()
                                .setBody(body)
                                .setStatus(statusCode)
                )
        );
    }
}
