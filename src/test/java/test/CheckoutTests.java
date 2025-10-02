package test;



import managers.DriverManager;
import utils.ConfigReader;
import pages.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTests {
    private WebDriver driver;
    private HomePage homePage;
    private ProductListingPage listingPage;
    private ProductDetailPage detailPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @BeforeMethod
    public void setup() {
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.getProperty("url"));
        homePage = new HomePage(driver);
        listingPage = new ProductListingPage(driver);
        detailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);

        // Pre-condition: Add an item to the cart and navigate to checkout
        homePage.navigateToMenCategory();
        listingPage.selectProduct(0);
        detailPage.clickAddToCart();
        homePage.openCart();
        cartPage.clickCheckout();
    }

    @Test(description = "Verify checkout button functionality")
    public void testCheckoutButtonFunctionality() {
        Assert.assertTrue(checkoutPage.isShippingFormDisplayed(), "Checkout page was not loaded.");
    }

    @Test(description = "Verify shipping form validation")
    public void testShippingFormValidation() {
        // Implement logic to submit empty or invalid form and verify error messages.
    }

    @Test(description = "Verify payment method selection")
    public void testPaymentMethodSelection() {
        // Implement logic to select payment method and verify.
    }

    @Test(description = "Verify order summary in checkout")
    public void testOrderSummaryInCheckout() {
        // Implement logic to verify product details and total in the order summary.
    }

    @Test(description = "Verify order confirmation")
    public void testOrderConfirmation() {
        // After form submission, verify that an order confirmation message or page is displayed.
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
