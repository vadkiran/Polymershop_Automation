package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutPage extends BasePage {
    
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }
    
    private SearchContext getShopCheckoutShadowRoot() {
        SearchContext shopAppRoot = getShopAppShadowRoot();
        WebElement shopCheckout = shopAppRoot.findElement(By.cssSelector("shop-checkout"));
        return shopCheckout.getShadowRoot();
    }
    
    public boolean isCheckoutPageLoaded() {
        try {
            SearchContext shopAppRoot = getShopAppShadowRoot();
            WebElement shopCheckout = shopAppRoot.findElement(By.cssSelector("shop-checkout"));
            return shopCheckout.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void fillShippingForm(String email, String phone, String address, 
                                   String city, String state, String zip, String country) {
        try {
            SearchContext checkoutRoot = getShopCheckoutShadowRoot();
            
            fillField(checkoutRoot, "input[id*='email']", email);
            fillField(checkoutRoot, "input[id*='phone']", phone);
            fillField(checkoutRoot, "input[id*='address']", address);
            fillField(checkoutRoot, "input[id*='city']", city);
            fillField(checkoutRoot, "input[id*='state']", state);
            fillField(checkoutRoot, "input[id*='zip']", zip);
            fillField(checkoutRoot, "input[id*='country']", country);
            
            waitFor(500);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fill shipping form: " + e.getMessage());
        }
    }
    
    private void fillField(SearchContext context, String selector, String value) {
        try {
            WebElement field = context.findElement(By.cssSelector(selector));
            field.clear();
            field.sendKeys(value);
        } catch (Exception e) {
            // Field might not exist
        }
    }
    
    public void selectPaymentMethod(String method) {
        try {
            SearchContext checkoutRoot = getShopCheckoutShadowRoot();
            WebElement paymentOption = checkoutRoot.findElement(
                By.cssSelector("input[value='" + method + "']"));
            clickElement(paymentOption);
            waitFor(500);
        } catch (Exception e) {
            // Payment method might not be selectable
        }
    }
    
    public String getOrderSummary() {
        try {
            SearchContext checkoutRoot = getShopCheckoutShadowRoot();
            WebElement summary = checkoutRoot.findElement(
                By.cssSelector(".order-summary, [class*='summary']"));
            return getTextSafely(summary);
        } catch (Exception e) {
            return "";
        }
    }
    
    public String getOrderTotal() {
        try {
            SearchContext checkoutRoot = getShopCheckoutShadowRoot();
            WebElement total = checkoutRoot.findElement(
                By.cssSelector(".total, [class*='total']"));
            return getTextSafely(total);
        } catch (Exception e) {
            return "$0.00";
        }
    }
    
    public void clickPlaceOrder() {
        try {
            SearchContext checkoutRoot = getShopCheckoutShadowRoot();
            WebElement placeOrderBtn = checkoutRoot.findElement(
                By.cssSelector("input[type='button'][value*='Place'], button[aria-label*='Place']"));
            
            scrollToElement(placeOrderBtn);
            clickElement(placeOrderBtn);
            waitFor(2000);
        } catch (Exception e) {
            throw new RuntimeException("Place order button not found");
        }
    }
    
    public boolean isPlaceOrderButtonDisplayed() {
        try {
            SearchContext checkoutRoot = getShopCheckoutShadowRoot();
            WebElement placeOrderBtn = checkoutRoot.findElement(
                By.cssSelector("input[type='button'][value*='Place'], button[aria-label*='Place']"));
            return placeOrderBtn.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean hasValidationErrors() {
        try {
            SearchContext checkoutRoot = getShopCheckoutShadowRoot();
            WebElement errorMsg = checkoutRoot.findElement(
                By.cssSelector(".error, [class*='error'], .invalid"));
            return errorMsg.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isOrderConfirmationDisplayed() {
        try {
            return getCurrentUrl().contains("confirmation") || 
                   getCurrentUrl().contains("success");
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getConfirmationMessage() {
        try {
            SearchContext checkoutRoot = getShopCheckoutShadowRoot();
            WebElement confirmation = checkoutRoot.findElement(
                By.cssSelector(".confirmation, .success-message"));
            return getTextSafely(confirmation);
        } catch (Exception e) {
            return "";
        }
    }
    
    public boolean areShippingFieldsPresent() {
        try {
            SearchContext checkoutRoot = getShopCheckoutShadowRoot();
            checkoutRoot.findElement(By.cssSelector("input[id*='email']"));
            checkoutRoot.findElement(By.cssSelector("input[id*='address']"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}