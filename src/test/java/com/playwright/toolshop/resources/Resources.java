package com.playwright.toolshop.resources;
import java.util.Arrays;
import java.util.List;

public class Resources {
    public static String MAIN_URL = "https://practicesoftwaretesting.com/";
    public static String BASE_API_URL = "https://api.practicesoftwaretesting.com/";
    public static String CONTACTS_URL = String.format("%scontact", MAIN_URL);
    public static String SIGN_IN_URL = String.format("%sauth/login", MAIN_URL);
    public static String SAMPLE_FILE_URI = "C:/Users/Jeka/IdeaProjects/Playwright/src/test/java/com/playwright/toolshop/resources/data/sample-data.txt";

    public static String PRODUCTS_REQUEST_URL_WITH_PARAMETERS = "**/products?**";
    public static String PRODUCTS_REQUEST_URL = "**/products";

    public static List<String> BROWSER_LAUNCH_OPTION = Arrays.asList(
            "--no-sandbox",
            "--disable-gpu",
            "--disable-extensions"
            );

    public static List<String> EXPECTED_PRODUCTS_PLIERS = Arrays.asList("Combination Pliers", "Pliers", "Long Nose Pliers", "Slip Joint Pliers");
    public static List<String> EXPECTED_POWER_TOOLS = Arrays.asList("Sheet Sander", "Belt Sander", "Circular Saw", "Random Orbit Sander", "Cordless Drill 20V", "Cordless Drill 24V", "Cordless Drill 18V", "Cordless Drill 12V");
    public static List<String> EXPECTED_PRODUCTS_MAIN_PAGE = Arrays.asList("Combination Pliers",
            "Pliers",
            "Bolt Cutters",
            "Long Nose Pliers",
            "Slip Joint Pliers",
            "Claw Hammer with Shock Reduction Grip",
            "Hammer",
            "Claw Hammer",
            "Thor Hammer"
    );
    public static List<String> EXPECTED_OUT_OF_STOCK_PRODUCTS = List.of("Long Nose Pliers");
}
