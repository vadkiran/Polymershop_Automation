package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import java.time.Duration;

public class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    private static final int DEFAULT_TIMEOUT = 15;
    
    // Wait times from config
    protected long SHORT_WAIT;
    protected long MEDIUM_WAIT;
    protected long LONG_WAIT;
    protected long PAGE_LOAD_WAIT;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        this.js = (JavascriptExecutor) driver;
        
        // Load wait times from config
        try {
            SHORT_WAIT = Long.parseLong(ConfigReader.getProperty("short.wait", "1000"));
            MEDIUM_WAIT = Long.parseLong(ConfigReader.getProperty("medium.wait", "2000"));
            LONG_WAIT = Long.parseLong(ConfigReader.getProperty("long.wait", "3000"));
            PAGE_LOAD_WAIT = Long.parseLong(ConfigReader.getProperty("page.load.wait", "2000"));
        } catch (Exception e) {
            // Use defaults if config fails
            SHORT_WAIT = 1000;
            MEDIUM_WAIT = 2000;
            LONG_WAIT = 3000;
            PAGE_LOAD_WAIT = 2000;
        }
    }
    
    protected SearchContext getShopAppShadowRoot() {
        WebElement shopApp = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("shop-app")));
        return shopApp.getShadowRoot();
    }
    
    protected SearchContext getShadowRoot(WebElement element) {
        return element.getShadowRoot();
    }
    
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
    
    protected void clickElement(WebElement element) {
        try {
            waitForElementClickable(element);
            element.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", element);
        }
    }
    
    protected void waitForElementClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    protected void waitForUrlContains(String urlFragment) {
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }
    
    protected void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        waitFor(500);
    }
    
    protected void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        waitFor(SHORT_WAIT);
    }
    
    protected void waitForPageLoad() {
        wait.until(driver -> 
            js.executeScript("return document.readyState").equals("complete"));
    }
    
    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    
    protected String getTextSafely(WebElement element) {
        try {
            return element.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }
    
    protected void waitFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    protected void navigateTo(String url) {
        driver.get(url);
        waitForPageLoad();
    }
}