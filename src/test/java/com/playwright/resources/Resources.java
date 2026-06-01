package com.playwright.resources;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Resources {
    public static String MAIN_URL = "https://practicesoftwaretesting.com/";
    public static String CONTACTS_URL = String.format("%s/contact", MAIN_URL);
    public static String SIGN_IN_URL = String.format("%s/auth/login", MAIN_URL);
    public static String SAMPLE_FILE_URI = "C:/Users/Jeka/IdeaProjects/Playwright/src/test/java/com/playwright/resources/data/sample-data.txt";

    public static List<String> BROWSER_LAUNCH_OPTION = Arrays.asList(
            "--no-sandbox",
            "--disable-gpu",
            "--disable-extensions"
            );
}
