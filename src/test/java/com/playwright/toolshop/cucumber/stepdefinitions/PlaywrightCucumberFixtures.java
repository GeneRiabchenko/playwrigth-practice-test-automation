package com.playwright.toolshop.cucumber.stepdefinitions;

import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;

import static com.playwright.toolshop.testresources.Resources.BROWSER_LAUNCH_OPTION;

public class PlaywrightCucumberFixtures {
    private static final ThreadLocal<Playwright> playwright
            = ThreadLocal.withInitial(() -> {
        Playwright playwright = Playwright.create();
        playwright.selectors().setTestIdAttribute("data-test");
        return playwright;
    });

    private static final ThreadLocal<Browser> browser = ThreadLocal.withInitial(() ->
            playwright.get().chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(false)
                            .setArgs(BROWSER_LAUNCH_OPTION)
            )
    );

    private static final ThreadLocal<BrowserContext> browserContext = new ThreadLocal<>();

    private static final ThreadLocal<Page> page = new ThreadLocal<>();

    @Before(order = 100)
    public void setUp() {
        browserContext.set(browser.get().newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)));
        browserContext.get().setDefaultTimeout(10000);
        browserContext.get().setDefaultNavigationTimeout(10000);
        page.set(browserContext.get().newPage());
    }

    @After
    public void closeContext() {
        browserContext.get().close();
    }

    @AfterAll
    public static void tearDown() {
        browser.get().close();
        browser.remove();

        playwright.get().close();
        playwright.remove();
    }

    public static Page getPage(){
        return page.get();
    }
}
