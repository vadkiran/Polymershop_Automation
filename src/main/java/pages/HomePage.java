package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class HomePage extends BasePage {
    
    public HomePage(WebDriver driver) {
        super(driver);
    }
    
    protected SearchContext getShopAppShadowRoot() {
        waitForPageLoad();
        waitFor(1500); // Critical wait for Shadow DOM initialization
        WebElement shopApp = wait.until(driver -> 
            driver.findElement(By.cssSelector("shop-app"))
        );
        return shopApp.getShadowRoot();
    }
    
    public boolean isPageLoaded() {
        try {
            waitForPageLoad();
            waitFor(2000); // Wait for lazy loading
            SearchContext shopAppRoot = getShopAppShadowRoot();
            WebElement toolbar = shopAppRoot.findElement(By.cssSelector("app-toolbar"));
            return toolbar.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isShopAppPresent() {
        try {
            waitForPageLoad();
            waitFor(1000);
            return isElementPresent(By.cssSelector("shop-app"));
        } catch (Exception e) {
            return false;
        }
    }
    
    public void navigateToMenCategory() {
        waitFor(1000); // Ensure page is ready
        SearchContext shopAppRoot = getShopAppShadowRoot();
        WebElement shopHome = shopAppRoot.findElement(By.cssSelector("shop-home"));
        SearchContext shopHomeRoot = shopHome.getShadowRoot();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement menLink = wait.until(driver1 -> {
            try {
                return shopHomeRoot.findElement(By.cssSelector("a[href*='mens_outerwear']"));
            } catch (Exception e) {
                return null;
            }
        });
        
        if (menLink != null) {
            scrollToElement(menLink);
            clickElement(menLink);
            waitForUrlContains("/mens_outerwear");
            waitFor(1500);
        } else {
            throw new RuntimeException("Men's category link not found.");
        }
    }
    
    public void navigateToWomenCategory() {
        waitFor(1000); // Ensure page is ready
        SearchContext shopAppRoot = getShopAppShadowRoot();
        WebElement shopHome = shopAppRoot.findElement(By.cssSelector("shop-home"));
        SearchContext shopHomeRoot = shopHome.getShadowRoot();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement womenLink = wait.until(driver1 -> {
            try {
                return shopHomeRoot.findElement(By.cssSelector("a[href*='ladies_outerwear']"));
            } catch (Exception e) {
                return null;
            }
        });
        
        if (womenLink != null) {
            scrollToElement(womenLink);
            clickElement(womenLink);
            waitForUrlContains("/ladies_outerwear");
            waitFor(1500);
        } else {
            throw new RuntimeException("Women's category link not found.");
        }
    }
    
    public void openCart() {
        waitFor(1000); // Ensure page is ready
        SearchContext shopAppRoot = getShopAppShadowRoot();
        WebElement header = shopAppRoot.findElement(By.cssSelector("shop-header"));
        SearchContext headerRoot = header.getShadowRoot();
        
        WebElement cartButton = headerRoot.findElement(By.cssSelector("a[href='/cart']"));
        clickElement(cartButton);
        waitForUrlContains("/cart");
        waitFor(1500);
    }
    
    public List<WebElement> getCategoryLinks() {
        waitFor(1000); // Ensure page is ready
        SearchContext shopAppRoot = getShopAppShadowRoot();
        WebElement shopHome = shopAppRoot.findElement(By.cssSelector("shop-home"));
        SearchContext shopHomeRoot = shopHome.getShadowRoot();
        
        return shopHomeRoot.findElements(By.cssSelector("a[href*='list']"));
    }
    
    public boolean isNavigationMenuDisplayed() {
        try {
            waitFor(1500); // Wait for navigation menu to load
            List<WebElement> links = getCategoryLinks();
            return links.size() >= 2;
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getCartBadgeCount() {
        try {
            waitFor(1000);
            SearchContext shopAppRoot = getShopAppShadowRoot();
            WebElement header = shopAppRoot.findElement(By.cssSelector("shop-header"));
            SearchContext headerRoot = header.getShadowRoot();
            
            WebElement badge = headerRoot.findElement(By.cssSelector("shop-cart-button"));
            SearchContext badgeRoot = badge.getShadowRoot();
            
            WebElement count = badgeRoot.findElement(By.cssSelector(".badge"));
            return getTextSafely(count);
        } catch (Exception e) {
            return "0";
        }
    }

}