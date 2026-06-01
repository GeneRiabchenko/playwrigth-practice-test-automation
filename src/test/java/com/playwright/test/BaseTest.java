package com.playwright.test;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static com.playwright.resources.Resources.BROWSER_LAUNCH_OPTION;

public class BaseTest {
    private static Playwright playwright;
    private static Browser browser;
    Page page;


    @BeforeAll
    public static void setUpBrowser(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(BROWSER_LAUNCH_OPTION)
        );
        BrowserContext browserContext = browser.newContext();
        playwright.selectors().setTestIdAttribute("data-test");
    }

    @BeforeEach
    public void setUp(){
        page = browser.newPage();
    }

    @AfterEach
    public void tearDownTest(){
        page.close();
    }

    @AfterAll
    public static void tearDownSet(){
        browser.close();
        playwright.close();
    }
}
