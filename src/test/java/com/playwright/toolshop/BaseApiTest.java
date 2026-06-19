package com.playwright.toolshop;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.playwright.toolshop.resources.Resources.BROWSER_LAUNCH_OPTION;

public class BaseApiTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;
    private static APIRequestContext requestContext;


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
