package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class ProductDetailPage extends BasePage {
    
    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }
    
    private SearchContext getShopDetailShadowRoot() {
        SearchContext shopAppRoot = getShopAppShadowRoot();
        WebElement shopDetail = shopAppRoot.findElement(By.cssSelector("shop-detail"));
        return shopDetail.getShadowRoot();
    }
    
    public boolean isProductDetailPageLoaded() {
        try {
            SearchContext shopAppRoot = getShopAppShadowRoot();
            WebElement shopDetail = shopAppRoot.findElement(By.cssSelector("shop-detail"));
            return shopDetail.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getProductTitle() {
        try {
            SearchContext detailRoot = getShopDetailShadowRoot();
            WebElement title = detailRoot.findElement(By.cssSelector(".title, h1"));
            return getTextSafely(title);
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getProductPrice() {
        try {
            SearchContext detailRoot = getShopDetailShadowRoot();
            WebElement price = detailRoot.findElement(By.cssSelector(".price"));
            return getTextSafely(price);
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getProductDescription() {
        try {
            SearchContext detailRoot = getShopDetailShadowRoot();
            WebElement description = detailRoot.findElement(By.cssSelector(".description, #desc"));
            return getTextSafely(description);
        } catch (Exception e) {
            return "";
        }
    }
    
    public boolean isProductImageDisplayed() {
        try {
            SearchContext detailRoot = getShopDetailShadowRoot();
            WebElement image = detailRoot.findElement(By.cssSelector("img, .image"));
            return image.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void selectSize(String size) {
        try {
            SearchContext detailRoot = getShopDetailShadowRoot();
            WebElement sizeSelect = detailRoot.findElement(By.cssSelector("select[id*='size'], #sizeSelect"));
            Select select = new Select(sizeSelect);
            select.selectByVisibleText(size);
            waitFor(500);
        } catch (Exception e) {
            // Size selection might not be available
        }
    }
    
    public List<WebElement> getAvailableSizes() {
        try {
            SearchContext detailRoot = getShopDetailShadowRoot();
            WebElement sizeSelect = detailRoot.findElement(By.cssSelector("select[id*='size']"));
            return sizeSelect.findElements(By.tagName("option"));
        } catch (Exception e) {
            return List.of();
        }
    }
    
    public void setQuantity(int quantity) {
        try {
            SearchContext detailRoot = getShopDetailShadowRoot();
            WebElement quantityInput = detailRoot.findElement(
                By.cssSelector("input[id*='quantity'], #quantityInput, select[id*='quantity']"));
            
            if (quantityInput.getTagName().equals("select")) {
                Select select = new Select(quantityInput);
                select.selectByValue(String.valueOf(quantity));
            } else {
                quantityInput.clear();
                quantityInput.sendKeys(String.valueOf(quantity));
            }
            waitFor(500);
        } catch (Exception e) {
            // Quantity might not be changeable
        }
    }
    
    public String getCurrentQuantity() {
        try {
            SearchContext detailRoot = getShopDetailShadowRoot();
            WebElement quantityInput = detailRoot.findElement(
                By.cssSelector("input[id*='quantity'], select[id*='quantity']"));
            
            if (quantityInput.getTagName().equals("select")) {
                Select select = new Select(quantityInput);
                return select.getFirstSelectedOption().getText();
            } else {
                return quantityInput.getAttribute("value");
            }
        } catch (Exception e) {
            return "1";
        }
    }
    
    public void clickAddToCart() {
        try {
            SearchContext detailRoot = getShopDetailShadowRoot();
            WebElement addToCartBtn = detailRoot.findElement(
                By.cssSelector("button[aria-label*='Add'], .add-to-cart"));
            
            scrollToElement(addToCartBtn);
            clickElement(addToCartBtn);
            waitFor(1000);
        } catch (Exception e) {
            throw new RuntimeException("Add to cart button not found");
        }
    }
    
    public boolean isAddToCartButtonDisplayed() {
        try {
            SearchContext detailRoot = getShopDetailShadowRoot();
            WebElement addToCartBtn = detailRoot.findElement(
                By.cssSelector("button[aria-label*='Add'], .add-to-cart"));
            return addToCartBtn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean areAllProductDetailsDisplayed() {
        return isProductImageDisplayed() && 
               !getProductTitle().isEmpty() && 
               !getProductPrice().isEmpty() &&
               isAddToCartButtonDisplayed();
    }
}