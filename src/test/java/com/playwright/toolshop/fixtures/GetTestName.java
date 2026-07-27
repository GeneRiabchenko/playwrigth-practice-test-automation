package com.playwright.toolshop.fixtures;

import org.junit.jupiter.api.TestInfo;

public class GetTestName {
    public static String getTestName(TestInfo info) {
       return info.getDisplayName().replace("()", "").toLowerCase();
    }
}
