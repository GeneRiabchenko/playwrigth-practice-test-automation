package com.playwright.toolshop.fixtures;

import com.microsoft.playwright.*;
import net.serenitybdd.playwright.PlaywrightSerenity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.HashMap;

import static com.playwright.toolshop.testresources.Resources.BASE_API_URL;
import static com.playwright.toolshop.testresources.Resources.BROWSER_LAUNCH_OPTION;

@ExtendWith(TestWatcherExtension.class)
public abstract class BaseTestRunner extends Tracer implements TestWatcher {
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
    protected static Page page;

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
        browserContext = browser.get().newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080));
        browserContext.setDefaultTimeout(10000);
        browserContext.setDefaultNavigationTimeout(10000);
        page = browserContext.newPage();
        page.setDefaultTimeout(10000);
        PlaywrightSerenity.registerPage(page);
    }


    @AfterEach
    void tearDownTest() {
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

