package com.playwright.toolshop.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.playwright.toolshop.utils.ProductSummary;
import net.serenitybdd.annotations.Step;

import java.util.List;
import java.util.stream.IntStream;

import static com.playwright.toolshop.testresources.Resources.*;

@UsePlaywright
public class CartPage extends BasePage {
    private static final char NON_BREAKING_SPACE = 0x00A0;

    private final Page page;

    private final Locator PRODUCT_TITLE;
    private final Locator PRODUCT_UNIT_PRICE;
    private final Locator CART_TOTAL;
    private final Locator ROW;
    private final Locator EMPTY_CART_MESSAGE;
    private final Locator CONTINUE_SHOPPING_BUTTON;
    private final Locator PROCEED_TO_CHECKOUT_BUTTON;

    public CartPage(Page page) {
        super(page);
        this.page = page;
        this.PRODUCT_TITLE = page.getByTestId("product-title");
        this.PRODUCT_UNIT_PRICE = page.getByTestId("product-price");
        this.CART_TOTAL = page.getByTestId("cart-total");
        this.ROW = page.getByRole(AriaRole.ROW);
        this.EMPTY_CART_MESSAGE = page.getByText("The cart is empty. Nothing to display.");
        this.CONTINUE_SHOPPING_BUTTON = page.getByTestId("continue-shopping");
        this.PROCEED_TO_CHECKOUT_BUTTON = page.getByTestId("proceed-1");
    }

    private Locator getRowByProductName(String productName) {
        return ROW.filter(new Locator.FilterOptions().setHas(page.getByText(productName, new Page.GetByTextOptions().setExact(true))));
    }

    @Override
    @Step("Return cart page url")
    protected String getUrl() {
        return CART_URL;
    }

    @Override
    @Step("Go to cart page")
    public void navigate() {
        page.waitForResponse(CART_REQUEST_URL, super::navigate);
    }

    @Step("Return product summaries shown in the cart")
    public List<ProductSummary> getProductSummaries() {
        List<String> titles = PRODUCT_TITLE.allInnerTexts();
        List<String> prices = PRODUCT_UNIT_PRICE.allInnerTexts();
        return IntStream.range(0, titles.size())
                .mapToObj(i -> new ProductSummary(normalize(titles.get(i)), normalize(prices.get(i))))
                .toList();
    }

    @Step("Return cart total")
    public String getCartTotal() {
        return CART_TOTAL.innerText();
    }

    @Step("Wait until cart total equals {0}")
    public void waitForCartTotal(String expectedTotal) {
        page.waitForCondition(() -> CART_TOTAL.innerText().equals(expectedTotal));
    }

    @Step("Update quantity of {0} to {1}")
    public void setProductQuantity(String productName, String quantity) {
        Locator quantityInput = page.getByLabel("Quantity for " + productName);
        Locator lineTotal = getRowByProductName(productName).getByTestId("line-price");
        String previousLineTotal = lineTotal.innerText();
        page.waitForResponse(QUANTITY_URL, () -> {
            quantityInput.fill(quantity);
            quantityInput.press("Tab");
        });
        page.waitForCondition(() -> !lineTotal.innerText().equals(previousLineTotal));
    }

    @Step("Return quantity of {0}")
    public String getProductQuantity(String productName) {
        return page.getByLabel("Quantity for " + productName).inputValue();
    }

    @Step("Return line total for {0}")
    public String getLineTotal(String productName) {
        return getRowByProductName(productName).getByTestId("line-price").innerText();
    }

    @Step("Remove {0} from the cart")
    public void removeProduct(String productName) {
        Locator row = getRowByProductName(productName);
        Locator removeButton = row.locator(".btn-danger");
        page.waitForResponse(CART_REQUEST_URL, removeButton::click);
        row.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.DETACHED));
    }

    @Step("Check that the cart is empty")
    public boolean isCartEmpty() {
        page.waitForCondition(EMPTY_CART_MESSAGE::isVisible);
        return EMPTY_CART_MESSAGE.isVisible();
    }

    @Step("Return empty cart message")
    public String getEmptyCartMessage() {
        return EMPTY_CART_MESSAGE.innerText();
    }

    @Step("Click continue shopping")
    public void clickContinueShopping() {
        CONTINUE_SHOPPING_BUTTON.click();
        page.waitForURL(MAIN_URL);
    }

    @Step("Click proceed to checkout")
    public void clickProceedToCheckout() {
        PROCEED_TO_CHECKOUT_BUTTON.click();
    }

    private static String normalize(String text) {
        return text.replace(NON_BREAKING_SPACE, ' ').strip();
    }
}
