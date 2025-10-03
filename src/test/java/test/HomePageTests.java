package test;

import managers.DriverManager;
import pages.HomePage;
import utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class HomePageTests {
    private WebDriver driver;
    private HomePage homePage;
    private final String expectedBaseUrl = "https://shop.polymer-project.org/";

    @BeforeMethod
    public void setup() {
        driver = DriverManager.getDriver();
        // Assumes ConfigReader.getProperty("url") returns "https://shop.polymer-project.org/"
        driver.get(ConfigReader.getProperty("url")); 
        homePage = new HomePage(driver);
    }

    @Test(description = "Verify homepage loads correctly")
    public void testHomePageLoads() {
        Assert.assertEquals(driver.getTitle(), "Polymer Shop", "Homepage title is incorrect.");
        Assert.assertEquals(driver.getCurrentUrl(), expectedBaseUrl, "Page URL mismatch.");
        
        // Additional check: Verify the presence of the main app container element
        WebElement shopApp = driver.findElement(By.tagName("shop-app"));
        Assert.assertTrue(shopApp.isDisplayed(), "The main <shop-app> component is not displayed, indicating a failed load.");
    }

    @Test(description = "Verify navigation menu items")
    public void testNavigationMenuPresence() {
        // Verify Men's link presence using the specific CSS locator
        List<WebElement> menLink = driver.findElements(By.cssSelector("a[href='/list']"));
        Assert.assertFalse(menLink.isEmpty(), "Men's category link is not present in the navigation menu.");

        // Verify Women's link presence using the specific CSS locator
        List<WebElement> womenLink = driver.findElements(By.cssSelector("a[href='/list/women']"));
        Assert.assertFalse(womenLink.isEmpty(), "Women's category link is not present in the navigation menu.");

        // Verify Cart icon/link presence using the specific CSS locator
        List<WebElement> cartIcon = driver.findElements(By.cssSelector("a[href='/cart']"));
        Assert.assertFalse(cartIcon.isEmpty(), "Cart icon/link is not present in the navigation menu.");

        // Verify that the links are visible
        Assert.assertTrue(menLink.get(0).isDisplayed(), "Men's link is not visible.");
        Assert.assertTrue(womenLink.get(0).isDisplayed(), "Women's link is not visible.");
    }

    @Test(description = "Verify product categories display")
    public void testProductCategoriesDisplay() {
        // Find category links using the locators defined in HomePage
        WebElement menLink = driver.findElement(By.cssSelector("a[href='/list']"));
        WebElement womenLink = driver.findElement(By.cssSelector("a[href='/list/women']"));
        
        // Verify that the links have the expected text and are visible/enabled
        Assert.assertTrue(menLink.getText().contains("Men's"), "Men's category label is missing or incorrect.");
        Assert.assertTrue(womenLink.getText().contains("Women's"), "Women's category label is missing or incorrect.");
        
        Assert.assertTrue(menLink.isEnabled(), "Men's link is not enabled/interactable.");
        Assert.assertTrue(womenLink.isEnabled(), "Women's link is not enabled/interactable.");
    }

    @Test(description = "Verify search functionality")
    public void testSearchFunctionality() {
        String searchTerm = "Jacket";
        
        // 1. Perform the search action using the Page Object Model method
        homePage.searchForProduct(searchTerm);

        // 2. Assert that the URL has changed to the search results page
        // The URL pattern for search results is usually "/list/search?q={searchTerm}"
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/list/search"), "URL did not navigate to the search results list page.");
        Assert.assertTrue(currentUrl.contains("q=" + searchTerm), "URL does not contain the search term in the query parameter.");
        
        // 3. Verify a product list or search results message appears
        List<WebElement> productItems = driver.findElements(By.cssSelector("shop-list .item"));
        Assert.assertTrue(productItems.size() > 0, "No product items were displayed after searching for: " + searchTerm);
    }

    @Test(description = "Verify featured products section")
    public void testFeaturedProductsDisplay() {
        // Featured products are generally found in the main content section, often within shop-home
        
        // 1. Locate the featured products container using a common CSS selector
        List<WebElement> featuredContainers = driver.findElements(By.cssSelector("shop-home > section[class='featured']"));
        
        Assert.assertFalse(featuredContainers.isEmpty(), "The main featured products section container is not found.");
        
        WebElement featuredSection = featuredContainers.get(0);
        Assert.assertTrue(featuredSection.isDisplayed(), "Featured products section is present but not visible.");

        // 2. Verify that actual featured product items are loaded within the section
        List<WebElement> featuredItems = featuredSection.findElements(By.cssSelector(".item"));
        Assert.assertTrue(featuredItems.size() >= 1, "No featured product items are displayed, suggesting an incomplete load.");
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
