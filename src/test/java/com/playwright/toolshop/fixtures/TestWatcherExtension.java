package com.playwright.toolshop.fixtures;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class TestWatcherExtension implements TestWatcher {

    @Override
    public void testSuccessful(ExtensionContext context) {
        deleteTraces(context);
        deleteScreenshots(context);
        TestWatcher.super.testSuccessful(context);
    }

    private void deleteTraces(ExtensionContext context){
        String traceName = context.getDisplayName().replace("()", "").toLowerCase();
        Path tracePath = Paths.get("target/traces/trace-" + traceName + ".zip");
        try {
            Files.deleteIfExists(tracePath);
        } catch (IOException e){
            System.out.println("Failed to delete trace" + traceName);
        }
    }

    private void deleteScreenshots(ExtensionContext context){
        String screenshotName = context.getDisplayName().replace("()", "").toLowerCase();
        Path screenshotPath = Paths.get("target/screenshots/" + screenshotName);
        try {
            Files.deleteIfExists(screenshotPath);
        } catch (IOException e){
            System.out.println("Failed to delete screenshot" + screenshotPath);
        }
    }

}
