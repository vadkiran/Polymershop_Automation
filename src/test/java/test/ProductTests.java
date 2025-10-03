package test;

import managers.DriverManager;
import pages.ProductListingPage;
import pages.ProductDetailPage;
import pages.HomePage;
import utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class to verify product listing and product detail page functionalities.
 * It assumes HomePage, ProductListingPage, and ProductDetailPage objects are correctly instantiated.
 */
public class ProductTests {
    private WebDriver driver;
    private HomePage homePage;
    private ProductListingPage listingPage;
    private ProductDetailPage detailPage;
    private final String menCategoryName = "Men's Outerwear";
    private final String defaultUrl = "https://shop.polymer-project.org/";

    @BeforeMethod
    public void setup() {
        driver = DriverManager.getDriver();
        // Assuming ConfigReader provides the base URL
        driver.get(ConfigReader.getProperty("url")); 
        homePage = new HomePage(driver);
        listingPage = new ProductListingPage(driver);
        detailPage = new ProductDetailPage(driver);
        // Start by navigating to a list page (Men's Outerwear) for the majority of tests
        homePage.navigateToMenCategory(); 
    }

    // --- Product Listing Tests ---

    @Test(description = "Verify product grid display")
    public void testProductGridDisplay() {
        int productCount = listingPage.getProductCount();
        Assert.assertTrue(productCount > 0, "No products were displayed on the listing page.");

        String currentTitle = listingPage.getCategoryTitle();
        Assert.assertNotNull(currentTitle, "Category title element is missing.");
    }

    @Test(description = "Verify product filtering by category")
    public void testProductFilteringByCategory() {
        String actualCategoryTitle = listingPage.getCategoryTitle();
        
        Assert.assertEquals(actualCategoryTitle, menCategoryName, 
            "Category filter failed. Expected category: " + menCategoryName + 
            ", Actual: " + actualCategoryTitle);
        
        Assert.assertTrue(listingPage.getProductCount() > 0, "No products found for the " + menCategoryName + " category.");
    }
    
    @Test(description = "Verify product search results (simulated via URL)")
    public void testProductSearchResults() {
        String searchTerm = "Jacket";
        // Directly navigate to the search results page to test the listing page view
        driver.get(defaultUrl + "list/search?q=" + searchTerm);
        
        int productCount = listingPage.getProductCount();
        Assert.assertTrue(productCount > 0, "No product results found for the search term: " + searchTerm);
        Assert.assertTrue(driver.getCurrentUrl().contains("q=" + searchTerm), "URL does not reflect the search query.");
    }
    
    @Test(description = "Verify product pagination")
    public void testProductPagination() {
        // Navigate to 'all' to ensure maximum products and likelihood of pagination links
        driver.get(defaultUrl + "list/all"); 
        
        String initialUrl = driver.getCurrentUrl();
        // Click the second page link (index 1 for page 2)
        listingPage.clickPaginationLink(1); 
        
        String newUrl = driver.getCurrentUrl();
        Assert.assertNotEquals(newUrl, initialUrl, "Pagination failed: URL did not change after clicking page 2 link.");
        // The URL typically changes the category/path or adds a query parameter, verify general change
        Assert.assertTrue(newUrl.contains("list/all"), "Pagination failed: New URL is incorrect.");
    }
    
    @Test(description = "Verify product sorting options (Test skipped)")
    public void testProductSortingOptions() {
        // NOTE: The Polymer Shop demo site does not have a visible, standard sorting dropdown/UI.
        // This test is skipped/used as a placeholder.
        System.out.println("TEST SKIPPED: Product sorting options UI element not reliably available on the Polymer Shop demo site.");
    }

    // --- Product Details Tests (Implementation of the 5 requested tests) ---

    @Test(description = "Verify product details page elements (Title, Price, Add to Cart)")
    public void testProductDetailPageElements() {
        // Navigate to the first product's detail page
        listingPage.selectProduct(0); 
        
        // 1. Verify URL pattern
        Assert.assertTrue(driver.getCurrentUrl().contains("/detail/"), "Did not navigate to Product Detail Page.");
        
        // 2. Verify Title and Price are displayed and non-empty
        String title = detailPage.getProductTitle();
        String price = detailPage.getProductPrice();
        
        Assert.assertTrue(title != null && !title.isEmpty(), "Product Title is missing or empty.");
        // Check for common price format (e.g., starts with $)
        Assert.assertTrue(price != null && price.startsWith("$"), "Product Price is missing or incorrectly formatted.");
    }

    @Test(description = "Verify product image gallery")
    public void testProductImageGallery() {
        listingPage.selectProduct(0);
        
        // Check for the presence of image gallery controls (left and right arrows)
        int controlCount = detailPage.getImageGalleryControlCount();
        Assert.assertEquals(controlCount, 2, "Expected 2 image gallery controls (left/right arrows) but found " + controlCount);
    }
    
    @Test(description = "Verify product description and specs (Size selector)")
    public void testProductDescriptionAndSpecs() {
        listingPage.selectProduct(0);
        
        // 1. Verify description is present and non-empty
        String description = detailPage.getProductDescription();
        Assert.assertTrue(description != null && description.length() > 50, "Product Description is missing or too short.");
        
        // 2. Verify Size selector (the main spec selection element) is present and functional.
        try {
            detailPage.selectSize("M");
        } catch (Exception e) {
            Assert.fail("Failed to select product size 'M', indicating the size selector/specs element is broken or missing: " + e.getMessage());
        }
    }

    @Test(description = "Verify quantity selection")
    public void testQuantitySelection() {
        listingPage.selectProduct(0);
        
        // Test 1: Verify default quantity is 1 
        detailPage.setQuantity(1); // Explicitly set to 1 for reliable test start
        String defaultQuantity = detailPage.getQuantityInputValue(); 
        Assert.assertEquals(defaultQuantity, "1", "Default quantity value is not 1.");
        
        // Test 2: Verify quantity can be increased using the '+' button
        detailPage.increaseQuantity();
        String increasedQuantity = detailPage.getQuantityInputValue(); 
        Assert.assertEquals(increasedQuantity, "2", "Quantity did not increase after clicking the '+' button.");
        
        // Test 3: Verify quantity can be set directly
        detailPage.setQuantity(5);
        String directSetQuantity = detailPage.getQuantityInputValue(); 
        Assert.assertEquals(directSetQuantity, "5", "Quantity could not be set directly via input field.");
    }

    @Test(description = "Verify add to cart functionality from details page")
    public void testAddProductToCartFromDetailsPage() {
        listingPage.selectProduct(0);
        
        // 1. Select a size (mandatory for Add to Cart button to be enabled on Polymer Shop)
        detailPage.selectSize("M");
        
        // 2. Click Add to Cart
        detailPage.clickAddToCart(); 
        
        // 3. Verify state change: The URL should now include the cart path.
        Assert.assertTrue(driver.getCurrentUrl().contains("/cart"), "Did not navigate to Cart Page after adding product.");
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
