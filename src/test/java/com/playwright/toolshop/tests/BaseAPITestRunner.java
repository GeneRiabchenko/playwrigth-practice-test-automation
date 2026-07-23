package com.playwright.toolshop.tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;

import static com.playwright.toolshop.testresources.Resources.BASE_API_URL;
import static com.playwright.toolshop.testresources.Resources.BROWSER_LAUNCH_OPTION;

public class BaseAPITestRunner {
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;
    protected static APIRequestContext requestContext;

    @BeforeAll
    public static void setupRequestContext() {
        requestContext = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL(BASE_API_URL)
                        .setExtraHTTPHeaders(new HashMap<>() {{
                            put("Accept", "application/json");
                        }})
        );
    }

    @BeforeAll
    public static void setUpBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(BROWSER_LAUNCH_OPTION)
        );
        playwright.selectors().setTestIdAttribute("data-test");
    }

    @BeforeEach
    void setUp() {
        browserContext = browser.newContext();
    }

    @AfterEach
    void tearDownTest() {
        browserContext.close();
    }

    @AfterAll
    static void tearDownSet() {
        browser.close();
        playwright.close();
    }
}
