package com.playwright.toolshop.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.BaseTest;
import com.playwright.toolshop.HeadlessChromeOptions;
import com.playwright.toolshop.product.pageobjects.MainPage;
import com.playwright.toolshop.search.pageobjects.LeftNavigationPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.stream.Stream;

@UsePlaywright(HeadlessChromeOptions.class)
public class APITests extends BaseTest{
    private static APIRequestContext requestContext;
    MainPage mainPage;
    LeftNavigationPage navigationPage;

    @BeforeAll
    public static void setupRequestContext() {
        requestContext = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL("https://api.practicesoftwaretesting.com/")
                        .setExtraHTTPHeaders(new HashMap<>() {{
                            put("Accept", "application/json");
                        }})
        );
    }

    @BeforeEach
    void openHomePage(){
        mainPage.goToMainPage();
    }

    @BeforeEach
    void setUp(Page page){
        mainPage = new MainPage(page);
        navigationPage = new LeftNavigationPage(page);
    }

    @DisplayName("Checking price and name in the API")
    @ParameterizedTest(name = "Checking product {0}")
    @MethodSource("products")
    void checkKnownProducts(Product product){
        navigationPage.search(product.name);
        mainPage.isFilteredProductByNameAndPriceVisible(product.name, product.price, true);
    }

    public record Product(String name, Double price) {
    }

    static Stream<Product> products() {
        Stream<Product> allProducts = Stream.empty();
        APIResponse response = requestContext.get("/products?page=1");
        Assertions.assertEquals(200, response.status());

        int pageCount = new Gson().fromJson(response.text(), JsonObject.class).getAsJsonPrimitive("last_page").getAsInt();

        for (int page = 1; page < pageCount; page++) {
            response = requestContext.get(String.format("/products?page=%s", page));
            JsonObject jsonObject = new Gson().fromJson(response.text(), JsonObject.class);
            JsonArray data = jsonObject.getAsJsonArray("data");
            Stream<Product> productsPerPage = data.asList().stream()
                    .map(jsonElement -> {
                        JsonObject productJson = jsonElement.getAsJsonObject();
                        return new Product(
                                productJson.get("name").getAsString(),
                                productJson.get("price").getAsDouble()
                        );
                    });
            allProducts = Stream.concat(allProducts, productsPerPage);
        }
        return allProducts;
    }
}
