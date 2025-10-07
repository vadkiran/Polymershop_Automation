package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
//        WebElement header = shopAppRoot.findElement(By.cssSelector("shop-cart-model"));
//        SearchContext headerRoot = header.getShadowRoot();
        WebElement cartButton = shopAppRoot.findElement(By.cssSelector("a[href='/cart']"));
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
    
//    public String getCartBadgeCount() {
//    	js.executeScript("window.scrollTo(0, 0);");
//        try {
//            waitFor(1000);
//            SearchContext shopAppRoot = getShopAppShadowRoot();
//            WebElement header = shopAppRoot.findElement(By.cssSelector("#header"));
//            SearchContext headerRoot = header.getShadowRoot();
//            
//            WebElement badge = headerRoot.findElement(By.cssSelector("[icon=\"shopping-cart\"]"));
//            SearchContext badgeRoot = badge.getShadowRoot();
//            
//            WebElement count = badgeRoot.findElement(By.cssSelector(".cart-badge"));
//            return getTextSafely(count);
//        } catch (Exception e) {
//            return "0";
//        }
//    }

    public String getCartBadgeCount() {
        try {
            waitFor(1000);
            js.executeScript("window.scrollTo(0, 0);");
            waitFor(500);
            
            SearchContext shopAppRoot = getShopAppShadowRoot();
            
            // Try multiple selector strategies
            try {
                // Strategy 1: Direct cart icon with aria-label
                WebElement cartIcon = shopAppRoot.findElement(By.cssSelector("a[href='/cart']"));
                String ariaLabel = cartIcon.getAttribute("aria-label");
                
                if (ariaLabel != null && ariaLabel.contains("Shopping cart")) {
                    // Extract number from "Shopping cart: 2 items"
                    String[] parts = ariaLabel.split(":");
                    if (parts.length > 1) {
                        String countText = parts[1].trim().split(" ")[0];
                        return countText;
                    }
                }
            } catch (Exception e1) {
                // Strategy 2: Check cart badge element
                try {
                    WebElement cartBadge = shopAppRoot.findElement(
                        By.cssSelector(".cart-badge, [class*='badge']"));
                    String badgeText = getTextSafely(cartBadge);
                    if (!badgeText.isEmpty()) {
                        return badgeText;
                    }
                } catch (Exception e2) {
                    // Strategy 3: JavaScript execution to get cart count
                    Object cartCount = js.executeScript(
                        "var shopApp = document.querySelector('shop-app');" +
                        "if (shopApp && shopApp.cart) {" +
                        "  return shopApp.cart.length || 0;" +
                        "}" +
                        "return 0;"
                    );
                    return String.valueOf(cartCount);
                }
            }
            
            return "0";
            
        } catch (Exception e) {
            System.err.println("Failed to retrieve cart badge count: " + e.getMessage());
            return "0";
        }
    }
}