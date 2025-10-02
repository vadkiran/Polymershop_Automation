package test;



import managers.DriverManager;
import pages.HomePage;
import utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTests {
    private WebDriver driver;
    private HomePage homePage;

    @BeforeMethod
    public void setup() {
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.getProperty("url"));
        homePage = new HomePage(driver);
    }

    @Test(description = "Verify homepage loads correctly")
    public void testHomePageLoads() {
        Assert.assertEquals(driver.getTitle(), "Polymer Shop", "Homepage title is incorrect.");
    }

    @Test(description = "Verify navigation menu items")
    public void testNavigationMenuPresence() {
        // Implement logic to verify menu items.
    }

    @Test(description = "Verify product categories display")
    public void testProductCategoriesDisplay() {
        // Implement logic to check for the presence of category links.
    }

    @Test(description = "Verify search functionality")
    public void testSearchFunctionality() {
        String searchTerm = "Jacket";
        homePage.searchForProduct(searchTerm);
        // Implement assertion to verify search results.
    }

    @Test(description = "Verify featured products section")
    public void testFeaturedProductsDisplay() {
        // Implement logic to verify the presence of the featured products section.
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}