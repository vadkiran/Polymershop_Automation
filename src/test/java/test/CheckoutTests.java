package test;

import managers.DriverManager;
import utils.ConfigReader;
import pages.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class to verify the complete checkout flow of the application.
 * Pre-condition: An item is added to the cart and the user is navigated to the /checkout page.
 */
public class CheckoutTests {
    private WebDriver driver;
    private HomePage homePage;
    private ProductListingPage listingPage;
    private ProductDetailPage detailPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    // Default test data for successful checkout
    private final String TEST_NAME = "Tester John Doe";
    private final String TEST_ADDRESS = "123 Automation Lane";
    private final String TEST_CITY = "Testville";

    @BeforeMethod
    public void setup() {
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.getProperty("url"));
        homePage = new HomePage(driver);
        listingPage = new ProductListingPage(driver);
        detailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);

        // --- Pre-condition: Add an item to the cart and navigate to checkout ---
        homePage.navigateToMenCategory();
        listingPage.selectProduct(0);
        
        // Select size to make 'Add to Cart' active (M is a common default)
        detailPage.selectSize("M"); 
        detailPage.clickAddToCart();
        
        // Navigate to cart and then checkout
        homePage.openCart();
        cartPage.clickCheckout();
    }

    @Test(description = "Verify checkout button functionality")
    public void testCheckoutButtonFunctionality() {
        // 1. Verify URL
        Assert.assertTrue(driver.getCurrentUrl().contains("/checkout"), "Did not navigate to the /checkout URL.");
        
        // 2. Verify main element (shipping form) is displayed
        Assert.assertTrue(checkoutPage.isShippingFormDisplayed(), "The main shipping form is not displayed on the checkout page.");
    }

    @Test(description = "Verify shipping form validation")
    public void testShippingFormValidation() {
        // 1. Attempt to place order without filling any fields
        checkoutPage.placeOrder();
        
        // 2. Assert specific validation error messages are displayed
        // The Polymer shop uses native browser validation. We check for the 'title' attribute which often holds the required prompt.
        String nameError = checkoutPage.getValidationMessage("name");
        String addressError = checkoutPage.getValidationMessage("address");

        Assert.assertFalse(nameError.isEmpty(), "Name field did not show validation error when submitted empty.");
        Assert.assertFalse(addressError.isEmpty(), "Address field did not show validation error when submitted empty.");
    }

    @Test(description = "Verify payment method selection (by checking section presence)")
    public void testPaymentMethodSelection() {
        // In this demo site, payment verification is often limited to checking the UI component presence.
        Assert.assertTrue(checkoutPage.isPaymentSectionDisplayed(), "The payment method selection section is missing from the checkout page.");
        
        // Optional: Fill out form fields to ensure the payment section is visible/interactable
        checkoutPage.fillShippingDetails(TEST_NAME, TEST_ADDRESS, TEST_CITY);
    }

    @Test(description = "Verify order summary in checkout")
    public void testOrderSummaryInCheckout() {
        // 1. Verify the order summary section is displayed
        Assert.assertTrue(checkoutPage.isOrderSummaryDisplayed(), "The order summary section is missing.");

        // 2. Verify the total price is displayed and correctly formatted (e.g., starts with $)
        String orderTotal = checkoutPage.getOrderTotal();
        Assert.assertTrue(orderTotal.startsWith("$"), "Order total is missing or does not start with a '$' sign.");
        
        // 3. Verify at least one item is listed in the summary
        Assert.assertTrue(checkoutPage.isItemInSummaryDisplayed(), "No product item is visible in the order summary.");
    }

    @Test(description = "Verify order confirmation")
    public void testOrderConfirmation() {
        // 1. Fill out all required fields successfully
        checkoutPage.fillShippingDetails(TEST_NAME, TEST_ADDRESS, TEST_CITY);
        
        // 2. Place the order
        checkoutPage.placeOrder();
        
        // 3. Assert the order confirmation page/message is displayed
        Assert.assertTrue(checkoutPage.isOrderConfirmed(), 
            "Order confirmation failed: The confirmation message/page was not displayed after placing the order.");
        Assert.assertTrue(driver.getCurrentUrl().contains("/confirmation"), 
            "Order confirmation failed: Did not navigate to the /confirmation URL.");
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
