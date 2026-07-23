package com.playwright.toolshop.tests;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Tracing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.nio.file.Paths;

public class Tracer {

    @BeforeEach
    void setupTrace(BrowserContext browserContext){
        browserContext.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
    }

    @AfterEach
    void recordTrace(TestInfo info, BrowserContext browserContext){
        String traceName = info.getDisplayName().replace("()", "").toLowerCase();
        browserContext.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("target/traces/trace-" + traceName + ".zip")));
    }
}
