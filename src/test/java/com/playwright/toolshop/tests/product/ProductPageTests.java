package com.playwright.toolshop.tests.product;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.HeadlessChromeOptions;
import com.playwright.toolshop.pages.MainPage;
import com.playwright.toolshop.pages.ProductPage;
import com.playwright.toolshop.tests.BaseTestRunner;
import com.playwright.toolshop.utils.enums.Specs;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UsePlaywright(HeadlessChromeOptions.class)
public class ProductPageTests extends BaseTestRunner {
    MainPage mainPage;
    ProductPage productPage;

    @BeforeEach
    public void setUp(Page page){
        mainPage = new MainPage(page);
        productPage = new ProductPage(page);
        mainPage.navigate();
    }

    @Test
    void productSpecificationsShouldMatch(){
        mainPage.openProductByName("Pliers");
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(productPage.getSpecValueByName(Specs.HANDLE_MATERIAL).equals("Rubber"));
            softAssertions.assertThat(productPage.getSpecValueByName(Specs.LENGTH).equals("180mm"));
            softAssertions.assertThat(productPage.getSpecValueByName(Specs.MATERIAL).equals("Carbon Steel"));
            softAssertions.assertThat(productPage.getSpecValueByName(Specs.WARRANTY).equals("1years"));
            softAssertions.assertThat(productPage.getSpecValueByName(Specs.WEIGHT).equals("280g"));
        });
    }

}
