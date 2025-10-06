package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * BasePage - Core page object with Shadow DOM utilities
 * Handles common operations and Shadow DOM navigation
 */
public class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    private static final int DEFAULT_TIMEOUT = 15;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        this.js = (JavascriptExecutor) driver;
    }
    
    /**
     * Get shop-app shadow root (main container)
     */
    protected SearchContext getShopAppShadowRoot() {
        WebElement shopApp = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("shop-app")));
        return shopApp.getShadowRoot();
    }
    
    /**
     * Get shadow root of any element
     */
    protected SearchContext getShadowRoot(WebElement element) {
        return element.getShadowRoot();
    }
    
    /**
     * Find element in shadow DOM with explicit wait
     */
    protected WebElement findElementInShadow(SearchContext shadowRoot, String cssSelector) {
        return wait.until(driver -> {
            try {
                WebElement element = shadowRoot.findElement(By.cssSelector(cssSelector));
                if (element != null && element.isDisplayed()) {
                    return element;
                }
            } catch (Exception e) {
                // Continue waiting
            }
            return null;
        });
    }
    
    /**
     * Click with retry and JS fallback
     */
    protected void clickElement(WebElement element) {
        try {
            waitForElementClickable(element);
            element.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", element);
        }
    }
    
    /**
     * Wait for element to be clickable
     */
    protected void waitForElementClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    /**
     * Wait for URL to contain text
     */
    protected void waitForUrlContains(String urlFragment) {
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }
    
    /**
     * Scroll to element using JS (for lazy loading)
     */
    protected void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        waitFor(500);
    }
    
    /**
     * Scroll to bottom (trigger lazy loading)
     */
    protected void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        waitFor(1000);
    }
    
    /**
     * Wait for page load complete
     */
    protected void waitForPageLoad() {
        wait.until(driver -> 
            js.executeScript("return document.readyState").equals("complete"));
    }
    
    /**
     * Check if element is present
     */
    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    
    /**
     * Get element text safely
     */
    protected String getTextSafely(WebElement element) {
        try {
            return element.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Simple wait
     */
    protected void waitFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Get current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    /**
     * Navigate to URL
     */
    protected void navigateTo(String url) {
        driver.get(url);
        waitForPageLoad();
    }
}