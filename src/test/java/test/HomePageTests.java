package test;

import managers.DriverManager;
import pages.HomePage;
import utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * HomePageTests - Test cases for Home Page (3 tests)
 */
public class HomePageTests {
    
    private WebDriver driver;
    private HomePage homePage;
    
    @BeforeMethod
    public void setUp() throws InterruptedException {
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.getProperty("url"));
        homePage = new HomePage(driver);
        Thread.sleep(2000);
    }
    
    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
    
    /**
     * Verify homepage loads correctly
     */
    @Test(priority = 1, 
          description = "Verify homepage loads correctly")
    public void testHomepageLoadsCorrectly() throws InterruptedException {
        assertTrue(homePage.isPageLoaded(), "Homepage should load successfully");
        assertTrue(homePage.isShopAppPresent(), "Shop-app element should be present");
        assertTrue(driver.getTitle().toUpperCase().contains("SHOP"), 
                  "Page title should contain 'SHOP'");
    }
    
    /**
     * Verify navigation menu items
     */
    @Test(priority = 2, 
          description = "Verify navigation menu items are displayed")
    public void testNavigationMenuItems() throws InterruptedException {
        assertTrue(homePage.isNavigationMenuDisplayed(), 
                  "Navigation menu should display category links");
        
        // Test Men's category navigation
        homePage.navigateToMenCategory();
        assertTrue(driver.getCurrentUrl().contains("mens_outerwear"), 
                  "Should navigate to Men's category");
    }
    
    /**
     * Verify product categories display
     */
    @Test(priority = 3,
          description = "Verify product categories are accessible")
    public void testProductCategoriesDisplay() throws InterruptedException {
        homePage.navigateToMenCategory();
        Thread.sleep(2000);
        assertTrue(driver.getCurrentUrl().contains("list"), 
                  "Should navigate to category list");
        
        driver.get(ConfigReader.getProperty("url"));
        Thread.sleep(2000);
        
        homePage.navigateToWomenCategory();
        Thread.sleep(2000);
        assertTrue(driver.getCurrentUrl().contains("ladies_outerwear"), 
                  "Should navigate to Women's category");
    }
}