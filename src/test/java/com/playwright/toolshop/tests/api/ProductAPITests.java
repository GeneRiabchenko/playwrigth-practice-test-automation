package com.playwright.toolshop.tests.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.HeadlessChromeOptions;
import com.playwright.toolshop.pageObjects.LeftNavigationPage;
import com.playwright.toolshop.pageObjects.MainPage;
import com.playwright.toolshop.tests.BaseApiTest;
import com.playwright.toolshop.utils.MockAPI;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.playwright.toolshop.resources.Resources.PRODUCTS_REQUEST_URL;
import static com.playwright.toolshop.resources.data.mocks.ProductsMock.PRODUCTS_SORTED_A_Z;
import static org.junit.jupiter.api.Assertions.assertEquals;


@UsePlaywright(HeadlessChromeOptions.class)
public class ProductAPITests extends BaseApiTest {
    MainPage mainPage;
    LeftNavigationPage navigationPage;
    MockAPI mockAPI;

    @BeforeEach
    void openHomePage(){
        mainPage.goToMainPage();
    }

    @BeforeEach
    void setUp(Page page){
        mainPage = new MainPage(page);
        navigationPage = new LeftNavigationPage(page);
        mockAPI = new MockAPI(page);
    }

    @DisplayName("Checking price and name in the API")
    @ParameterizedTest(name = "Checking product {0}")
    @MethodSource("products")
    void checkKnownProducts(Product product){
        navigationPage.search(product.name);
        mainPage.isFilteredProductByNameAndPriceVisible(product.name, product.price, true);
    }

    @Test
    @DisplayName("Mock product request")
    void mockedResponseSortedAZ() {
        mockAPI.mockRequest(PRODUCTS_REQUEST_URL, PRODUCTS_SORTED_A_Z, 200);
        navigationPage.selectSortBy("Name (A - Z)");
        Assertions.assertThat(mainPage.countSearchedResults()).isEqualTo(9);
    }

    public record Product(String name, Double price) {
    }

    static Stream<Product> products() {
        Stream<Product> allProducts = Stream.empty();
        APIResponse response = requestContext.get("/products?page=1");
        assertEquals(200, response.status());

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
