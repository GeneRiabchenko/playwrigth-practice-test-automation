package com.playwright.toolshop.tests.product;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.playwright.toolshop.HeadlessChromeOptions;
import com.playwright.toolshop.fixtures.BaseTestRunner;
import com.playwright.toolshop.pages.MainPage;
import com.playwright.toolshop.pages.ProductPage;
import com.playwright.toolshop.utils.enums.Specs;
import net.serenitybdd.annotations.Feature;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Story;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.playwright.junit5.SerenityPlaywrightExtension;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
@ExtendWith(SerenityPlaywrightExtension.class)
@UsePlaywright(HeadlessChromeOptions.class)
public class ProductPageTests extends BaseTestRunner {
    @Steps MainPage mainPage;
    @Steps ProductPage productPage;

    @BeforeEach
    public void setUp(Page page){
        mainPage.navigate();
    }

    @Feature("Product Catalog")
    @Story("Viewing product specifications")
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
