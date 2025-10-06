package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class ProductListingPage extends BasePage {
    
    public ProductListingPage(WebDriver driver) {
        super(driver);
    }
    
    private SearchContext getShopListShadowRoot() {
        SearchContext shopAppRoot = getShopAppShadowRoot();
        WebElement shopList = shopAppRoot.findElement(By.cssSelector("shop-list"));
        return shopList.getShadowRoot();
    }
    
    public boolean isProductListPageLoaded() {
        try {
            SearchContext shopAppRoot = getShopAppShadowRoot();
            WebElement shopList = shopAppRoot.findElement(By.cssSelector("shop-list"));
            return shopList.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<WebElement> getAllProducts() {
        SearchContext shopListRoot = getShopListShadowRoot();
        return shopListRoot.findElements(By.cssSelector("shop-list-item"));
    }
    
    public int getProductCount() {
        waitFor(2000);
        scrollToBottom();
        waitFor(1000);
        return getAllProducts().size();
    }
    
    public boolean isProductGridDisplayed() {
        try {
            List<WebElement> products = getAllProducts();
            return products.size() > 0 && products.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void selectProduct(int index) {
        List<WebElement> products = getAllProducts();
        if (index < products.size()) {
            WebElement product = products.get(index);
            scrollToElement(product);
            clickElement(product);
            waitFor(2000);
        } else {
            throw new RuntimeException("Product index out of bounds: " + index);
        }
    }
    
    public String getProductTitle(int index) {
        try {
            List<WebElement> products = getAllProducts();
            if (index < products.size()) {
                WebElement product = products.get(index);
                SearchContext productRoot = product.getShadowRoot();
                WebElement title = productRoot.findElement(By.cssSelector(".title"));
                return getTextSafely(title);
            }
        } catch (Exception e) {
            // Return empty if not found
        }
        return "";
    }
    
    public String getProductPrice(int index) {
        try {
            List<WebElement> products = getAllProducts();
            if (index < products.size()) {
                WebElement product = products.get(index);
                SearchContext productRoot = product.getShadowRoot();
                WebElement price = productRoot.findElement(By.cssSelector(".price"));
                return getTextSafely(price);
            }
        } catch (Exception e) {
            // Return empty if not found
        }
        return "";
    }
    
    public boolean isFilteredByCategory(String category) {
        return getCurrentUrl().contains(category);
    }
    
    public boolean isFirstProductVisible() {
        try {
            List<WebElement> products = getAllProducts();
            return products.size() > 0 && products.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}