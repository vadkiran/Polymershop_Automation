package test;



import managers.DriverManager;
import pages.ProductListingPage;
import pages.ProductDetailPage;
import pages.HomePage;
import utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductTests {
    private WebDriver driver;
    private HomePage homePage;
    private ProductListingPage listingPage;
    private ProductDetailPage detailPage;

    @BeforeMethod
    public void setup() {
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.getProperty("url"));
        homePage = new HomePage(driver);
        listingPage = new ProductListingPage(driver);
        detailPage = new ProductDetailPage(driver);
        homePage.navigateToMenCategory();
    }

    @Test(description = "Verify product grid display")
    public void testProductGridDisplay() {
        // Implement logic to check if products are displayed in a grid.
    }

    @Test(description = "Verify product filtering by category")
    public void testProductFiltering() {
        // Implement logic for filtering and verifying results.
    }

    @Test(description = "Verify product details page elements")
    public void testProductDetailPageElements() {
        listingPage.selectProduct(0);
        // Implement assertions to verify elements like image, description, and price.
    }

    @Test(description = "Verify product image gallery")
    public void testProductImageGallery() {
        listingPage.selectProduct(0);
        // Implement assertions to check image gallery functionality.
    }

    @Test(description = "Verify add to cart functionality from details page")
    public void testAddProductToCartFromDetailsPage() {
        listingPage.selectProduct(0);
        detailPage.clickAddToCart();
        // Implement assertion to verify product is added.
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}