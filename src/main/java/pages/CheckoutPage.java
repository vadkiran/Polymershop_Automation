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
            waitFor(MEDIUM_WAIT);
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
            waitFor(MEDIUM_WAIT);
            
            // Try multiple strategies to fill fields
            fillFieldWithMultipleStrategies(checkoutRoot, new String[]{
                "input[id*='email']", 
                "iron-input[id*='email'] input",
                "paper-input[name='email'] input"
            }, email);
            
            fillFieldWithMultipleStrategies(checkoutRoot, new String[]{
                "input[id*='phone']",
                "iron-input[id*='phone'] input",
                "paper-input[name='phone'] input"
            }, phone);
            
            fillFieldWithMultipleStrategies(checkoutRoot, new String[]{
                "input[id*='address']",
                "iron-input[id*='address'] input",
                "paper-input[name='address'] input",
                "textarea[id*='address']"
            }, address);
            
            fillFieldWithMultipleStrategies(checkoutRoot, new String[]{
                "input[id*='city']",
                "iron-input[id*='city'] input"
            }, city);
            
            fillFieldWithMultipleStrategies(checkoutRoot, new String[]{
                "input[id*='state']",
                "iron-input[id*='state'] input"
            }, state);
            
            fillFieldWithMultipleStrategies(checkoutRoot, new String[]{
                "input[id*='zip']",
                "input[id*='postcode']",
                "iron-input[id*='zip'] input"
            }, zip);
            
            fillFieldWithMultipleStrategies(checkoutRoot, new String[]{
                "input[id*='country']",
                "iron-input[id*='country'] input"
            }, country);
            
            waitFor(SHORT_WAIT);
        } catch (Exception e) {
            System.out.println("Failed to fill shipping form: " + e.getMessage());
        }
    }
    
    private void fillFieldWithMultipleStrategies(SearchContext context, String[] selectors, String value) {
        for (String selector : selectors) {
            try {
                WebElement field = context.findElement(By.cssSelector(selector));
                if (field.isDisplayed() && field.isEnabled()) {
                    field.clear();
                    field.sendKeys(value);
                    return; // Success, exit
                }
            } catch (Exception e) {
                // Try next selector
            }
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
            waitFor(SHORT_WAIT);
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
            
            WebElement placeOrderBtn = null;
            
            // Try multiple selectors
            String[] selectors = {
                "input[type='button'][value*='Place']",
                "button[aria-label*='Place']",
                "paper-button.place-order",
                ".place-order-button",
                "input[value*='Place Order']"
            };
            
            for (String selector : selectors) {
                try {
                    placeOrderBtn = checkoutRoot.findElement(By.cssSelector(selector));
                    if (placeOrderBtn.isDisplayed()) {
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            if (placeOrderBtn != null) {
                scrollToElement(placeOrderBtn);
                waitForElementClickable(placeOrderBtn);
                clickElement(placeOrderBtn);
                waitFor(MEDIUM_WAIT);
            }
        } catch (Exception e) {
            throw new RuntimeException("Place order button not found: " + e.getMessage());
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
            waitFor(LONG_WAIT);
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
            waitFor(LONG_WAIT); // Increased wait for form to load
            
            // Check for ANY checkout form fields
            boolean hasFields = false;
            
            String[] fieldSelectors = {
                "input[id*='email']", "iron-input[id*='email'] input",
                "input[id*='address']", "iron-input[id*='address'] input",
                "input[id*='phone']", "iron-input[id*='phone'] input",
                "textarea", "paper-input input"
            };
            
            for (String selector : fieldSelectors) {
                try {
                    WebElement field = checkoutRoot.findElement(By.cssSelector(selector));
                    if (field.isDisplayed()) {
                        hasFields = true;
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
            return hasFields;
            
        } catch (Exception e) {
            return false;
        }
    }
}