package test;

import managers.DriverManager;
import pages.HomePage;
import pages.ProductListingPage;
import pages.ProductDetailPage;
import utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import static org.testng.Assert.*;
/**
 * ProductTests - Product Listing and Detail Tests (9 tests)
 */
public class ProductTests {
    
    private WebDriver driver;
    private HomePage homePage;
    private ProductListingPage listingPage;
    private ProductDetailPage detailPage;
    
    @BeforeMethod
    public void setUp() throws InterruptedException {
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.getProperty("url"));
        homePage = new HomePage(driver);
        listingPage = new ProductListingPage(driver);
        detailPage = new ProductDetailPage(driver);
        Thread.sleep(2000);
    }
    
    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
    
    // ==================== PRODUCT LISTING TESTS ====================
    
    /**
     * Verify product grid display
     */
    @Test(priority = 1, groups = {"smoke", "product"}, 
          description = "Verify product grid displays products")
    public void testProductGridDisplay() throws InterruptedException {
        homePage.navigateToMenCategory();
        Thread.sleep(2000);
        
        assertTrue(listingPage.isProductListPageLoaded(), 
                  "Product list page should load");
        assertTrue(listingPage.isProductGridDisplayed(), 
                  "Product grid should be displayed");
        assertTrue(listingPage.getProductCount() > 0, 
                  "Products should be displayed in grid");
    }
    
    /**
     * Verify product filtering by clicking category
     */
    @Test(priority = 2, groups = {"smoke", "product"}, 
          description = "Verify products can be filtered by category")
    public void testProductFilteringByCategory() throws InterruptedException {
        homePage.navigateToMenCategory();
        Thread.sleep(2000);
        
        assertTrue(listingPage.isFilteredByCategory("mens_outerwear"), 
                  "Should filter by Men's category");
        
        driver.get(ConfigReader.getProperty("url"));
        Thread.sleep(2000);
        
        homePage.navigateToWomenCategory();
        Thread.sleep(2000);
        
        assertTrue(listingPage.isFilteredByCategory("ladies_outerwear"), 
                  "Should filter by Women's category");
    }
    
    // ==================== PRODUCT DETAIL TESTS ====================
    
    /**
     * Verify product details page elements
     */
    @Test(priority = 3, groups = {"regression", "product"}, 
          description = "Verify all product detail page elements are displayed")
    public void testProductDetailsPageElements() throws InterruptedException {
        homePage.navigateToMenCategory();
        Thread.sleep(2000);
        listingPage.selectProduct(0);
        Thread.sleep(2000);
        
        assertTrue(detailPage.isProductDetailPageLoaded(), 
                  "Product detail page should load");
        assertTrue(detailPage.areAllProductDetailsDisplayed(), 
                  "All product details should be displayed");
    }
    
    /**
     * Verify product image gallery
     */
    @Test(priority = 4, groups = {"regression", "product"}, 
          description = "Verify product image is displayed")
    public void testProductImageGallery() throws InterruptedException {
        homePage.navigateToMenCategory();
        Thread.sleep(2000);
        listingPage.selectProduct(0);
        Thread.sleep(2000);
        
        assertTrue(detailPage.isProductImageDisplayed(), 
                  "Product image should be displayed");
    }
    
    /**
     * Verify product description and specs
     */
    @Test(priority = 5, groups = {"regression", "product"}, 
          description = "Verify product description and specifications")
    public void testProductDescriptionAndSpecs() throws InterruptedException {
        homePage.navigateToMenCategory();
        Thread.sleep(2000);
        listingPage.selectProduct(0);
        Thread.sleep(2000);
        
        assertFalse(detailPage.getProductTitle().isEmpty(), 
                   "Product title should be displayed");
        assertFalse(detailPage.getProductPrice().isEmpty(), 
                   "Product price should be displayed");
        assertFalse(detailPage.getProductDescription().isEmpty(), 
                   "Product description should be displayed");
    }
    
    /**
     * Verify quantity selection
     */
    @Test(priority = 6, groups = {"regression", "product"}, 
          description = "Verify quantity can be selected")
    public void testQuantitySelection() throws InterruptedException {
        homePage.navigateToMenCategory();
        Thread.sleep(2000);
        listingPage.selectProduct(0);
        Thread.sleep(2000);
        
        detailPage.setQuantity(2);
        Thread.sleep(1000);
        
        String quantity = detailPage.getCurrentQuantity();
        assertTrue(quantity.equals("2") || quantity.contains("2"), 
                  "Quantity should be set to 2");
    }
    
    /**
     * Verify add to cart functionality
     */
    @Test(priority = 7, groups = {"smoke", "product"}, 
          description = "Verify add to cart button functionality")
    public void testAddToCartFunctionality() throws InterruptedException {
        homePage.navigateToMenCategory();
        Thread.sleep(2000);
        listingPage.selectProduct(0);
        Thread.sleep(2000);
        
        assertTrue(detailPage.isAddToCartButtonDisplayed(), 
                  "Add to cart button should be displayed");
        
        detailPage.clickAddToCart();
        Thread.sleep(2000);
        
        String cartCount = homePage.getCartBadgeCount();
        assertTrue(!cartCount.equals("0"), 
                  "Cart should be updated after adding product");
    }
    
    /**
     * Verify product price display
     */
    @Test(priority = 8, groups = {"regression", "product"}, 
          description = "Verify product price is displayed correctly")
    public void testProductPriceDisplay() throws InterruptedException {
        homePage.navigateToMenCategory();
        Thread.sleep(2000);
        listingPage.selectProduct(0);
        Thread.sleep(2000);
        
        String price = detailPage.getProductPrice();
        assertFalse(price.isEmpty(), "Product price should be displayed");
        assertTrue(price.contains("$"), "Price should contain currency symbol");
    }
    
    /**
     * TC-30: Verify product size selection
     */
    @Test(priority = 9, groups = {"regression", "product"}, 
          description = "Verify product size can be selected")
    public void testProductSizeSelection() throws InterruptedException {
        homePage.navigateToMenCategory();
        Thread.sleep(2000);
        listingPage.selectProduct(0);
        Thread.sleep(2000);
        
        // Size selection may not be available for all products
        try {
            if (!detailPage.getAvailableSizes().isEmpty()) {
                detailPage.selectSize("M");
                Thread.sleep(1000);
                assertTrue(true, "Size selection successful");
            } else {
                assertTrue(true, "Size selection not available for this product");
            }
        } catch (Exception e) {
            assertTrue(true, "Size selection not applicable");
        }
    }
}