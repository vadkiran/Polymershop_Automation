package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CartPage extends BasePage {
    
    public CartPage(WebDriver driver) {
        super(driver);
    }
    
    private SearchContext getShopCartShadowRoot() {
        SearchContext shopAppRoot = getShopAppShadowRoot();
        WebElement shopCart = shopAppRoot.findElement(By.cssSelector("shop-cart"));
        return shopCart.getShadowRoot();
    }
    
    public boolean isCartPageLoaded() {
        try {
            SearchContext shopAppRoot = getShopAppShadowRoot();
            WebElement shopCart = shopAppRoot.findElement(By.cssSelector("shop-cart"));
            return shopCart.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<WebElement> getCartItems() {
        try {
            SearchContext cartRoot = getShopCartShadowRoot();
            return cartRoot.findElements(By.cssSelector("shop-cart-item"));
        } catch (Exception e) {
            return List.of();
        }
    }
    
    public int getCartItemCount() {
        return getCartItems().size();
    }
    
    public boolean isCartEmpty() {
        return getCartItemCount() == 0;
    }
    
    public String getEmptyCartMessage() {
        try {
            SearchContext cartRoot = getShopCartShadowRoot();
            WebElement emptyMessage = cartRoot.findElement(
                By.cssSelector(".empty-cart, p[class*='empty']"));
            return getTextSafely(emptyMessage);
        } catch (Exception e) {
            return "";
        }
    }
    
    public boolean isEmptyCartMessageDisplayed() {
        return !getEmptyCartMessage().isEmpty();
    }
    
    public String getCartTotal() {
        try {
            SearchContext cartRoot = getShopCartShadowRoot();
            WebElement total = cartRoot.findElement(
                By.cssSelector(".total, [class*='total']"));
            return getTextSafely(total);
        } catch (Exception e) {
            return "$0.00";
        }
    }
    
    public void updateProductQuantity(int itemIndex, int newQuantity) {
        try {
            List<WebElement> items = getCartItems();
            if (itemIndex < items.size()) {
                WebElement item = items.get(itemIndex);
                SearchContext itemRoot = item.getShadowRoot();
                
                WebElement quantitySelect = itemRoot.findElement(
                    By.cssSelector("select[id*='quantity']"));
                
                js.executeScript("arguments[0].value = arguments[1];", quantitySelect, String.valueOf(newQuantity));
                js.executeScript("arguments[0].dispatchEvent(new Event('change'));", quantitySelect);
                waitFor(1000);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update quantity: " + e.getMessage());
        }
    }
    
    public void removeProductFromCart(int itemIndex) {
        try {
            List<WebElement> items = getCartItems();
            if (itemIndex < items.size()) {
                WebElement item = items.get(itemIndex);
                SearchContext itemRoot = item.getShadowRoot();
                
              WebElement removeBtn = itemRoot.findElement(
            		  	By.cssSelector("button[name='delete'], .delete-button, .remove"));
                
                clickElement(removeBtn);
                waitFor(1000);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to remove item: " + e.getMessage());
        }
    }

    
    // THIS METHOD WAS MISSING - CAUSING THE ERROR
    public void clickCheckout() {
        try {
            SearchContext cartRoot = getShopCartShadowRoot();
            WebElement checkoutBtn = cartRoot.findElement(
                By.cssSelector("a[href='/checkout'], .checkout-button"));
            
            clickElement(checkoutBtn);
            waitForUrlContains("/checkout");
        } catch (Exception e) {
            throw new RuntimeException("Checkout button not found");
        }
    }
    
    public boolean isCheckoutButtonDisplayed() {
        try {
            SearchContext cartRoot = getShopCartShadowRoot();
            WebElement checkoutBtn = cartRoot.findElement(
                By.cssSelector("a[href='/checkout'], .checkout-button"));
            return checkoutBtn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getCartProductTitle(int itemIndex) {
        try {
            List<WebElement> items = getCartItems();
            if (itemIndex < items.size()) {
                WebElement item = items.get(itemIndex);
                SearchContext itemRoot = item.getShadowRoot();
                WebElement title = itemRoot.findElement(By.cssSelector(".title, a"));
                return getTextSafely(title);
            }
        } catch (Exception e) {
            // Return empty
        }
        return "";
    }
    
    public double calculateExpectedTotal() {
        double total = 0.0;
        try {
            List<WebElement> items = getCartItems();
            for (WebElement item : items) {
                SearchContext itemRoot = item.getShadowRoot();
                WebElement price = itemRoot.findElement(By.cssSelector(".price"));
                String priceText = getTextSafely(price).replace("$", "").trim();
                total += Double.parseDouble(priceText);
            }
        } catch (Exception e) {
            // Return 0 if calculation fails
        }
        return total;
    }
}