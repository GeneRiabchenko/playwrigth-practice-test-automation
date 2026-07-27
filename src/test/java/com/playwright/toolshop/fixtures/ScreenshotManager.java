package com.playwright.toolshop.fixtures;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.ScreenshotType;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInfo;

import java.io.ByteArrayInputStream;
import java.nio.file.Paths;

import static com.playwright.toolshop.fixtures.GetTestName.getTestName;

public interface ScreenshotManager {

    @AfterEach
    default void takeScreenshot(Page page, TestInfo info) {
            var screenShot = page.screenshot(new Page.ScreenshotOptions()
                    .setType(ScreenshotType.PNG)
                    .setPath(Paths.get("target/screenshots/" + getTestName(info)))
                    .setFullPage(true));
            Allure.addAttachment(getTestName(info), new ByteArrayInputStream(screenShot));
    }
}

