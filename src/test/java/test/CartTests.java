package test;

import managers.DriverManager;
import pages.CartPage;
import pages.ProductListingPage;
import pages.ProductDetailPage;
import pages.HomePage;
import utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTests {
    private WebDriver driver;
    private HomePage homePage;
    private ProductListingPage listingPage;
    private ProductDetailPage detailPage;
    private CartPage cartPage;

    @BeforeMethod
    public void setup() {
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.getProperty("url"));
        homePage = new HomePage(driver);
        listingPage = new ProductListingPage(driver);
        detailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);
        
        // Pre-condition: Add an item to the cart for most tests
        homePage.navigateToMenCategory();
        listingPage.selectProduct(0);
        detailPage.clickAddToCart();
        homePage.openCart();
    }

    @Test(description = "Verify adding products to cart")
    public void testAddingProductsToCart() {
        Assert.assertTrue(cartPage.isProductAdded(), "Product was not added to the cart.");
    }

    @Test(description = "Verify updating product quantity in cart")
    public void testUpdateProductQuantity() {
        // Implement logic to update quantity and verify.
    }

    @Test(description = "Verify removing products from cart")
    public void testRemovingProductsFromCart() {
        // Implement logic to remove a product and verify cart is empty.
    }

    @Test(description = "Verify cart total calculation")
    public void testCartTotalCalculation() {
        // This test requires two or more products in the cart
        // Add another product
        homePage.navigateToWomenCategory();
        listingPage.selectProduct(0);
        detailPage.clickAddToCart();
        homePage.openCart();
        
        // Assert total calculation
        Assert.assertEquals(cartPage.getCartTotal(), cartPage.calculateExpectedTotal(),
                "Cart total is incorrect.");
    }

    @Test(description = "Verify empty cart message")
    public void testEmptyCartMessage() {
        // Implement logic to empty cart and verify message.
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
