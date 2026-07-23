package com.playwright.toolshop.tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.util.HashMap;

import static com.playwright.toolshop.testresources.Resources.BASE_API_URL;
import static com.playwright.toolshop.testresources.Resources.BROWSER_LAUNCH_OPTION;

public abstract class BaseTestRunner extends Tracer {
    protected static ThreadLocal<Playwright> playwright
            = ThreadLocal.withInitial(() -> {
                        Playwright playwright = Playwright.create();
                        playwright.selectors().setTestIdAttribute("data-test");
                        return playwright;
    });
    protected static ThreadLocal<Browser> browser = ThreadLocal.withInitial(() ->
        playwright.get().chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(true)
                        .setArgs(BROWSER_LAUNCH_OPTION)
        )
    );

    protected BrowserContext browserContext;
    protected APIRequestContext requestContext;
    protected Page page;

    @BeforeEach
    public void setupRequestContext() {
        requestContext = playwright.get().request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL(BASE_API_URL)
                        .setExtraHTTPHeaders(new HashMap<>() {{
                            put("Accept", "application/json");
                        }})
        );
    }

    @BeforeEach
    void setUp() {
        browserContext = browser.get().newContext();
        page = browserContext.newPage();
    }


    @AfterEach
    void tearDownTest(TestInfo info) {
        browserContext.close();
        requestContext.dispose();
    }

    @AfterAll
    static void tearDownSet() {
        browser.get().close();
        browser.remove();

        playwright.get().close();
        playwright.remove();
    }



}

