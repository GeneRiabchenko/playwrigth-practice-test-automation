package com.playwright.toolshop.fixtures;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.HashMap;

import static com.playwright.toolshop.testresources.Resources.BASE_API_URL;
import static com.playwright.toolshop.testresources.Resources.BROWSER_LAUNCH_OPTION;

@ExtendWith(TestWatcherExtension.class)
public class BaseAPITestRunner extends Tracer implements TestWatcher {
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
    protected static BrowserContext browserContext;
    protected static APIRequestContext requestContext;
    protected static Page page;

    @BeforeAll
    public static void setupRequestContext() {
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
    void tearDownTest() {
        browserContext.close();
    }

    @AfterAll
    static void tearDownSet() {
        browser.get().close();
        playwright.get().close();
    }
}
