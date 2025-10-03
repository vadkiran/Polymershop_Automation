package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends BasePage {
    // --- Locators for Form and Sections ---
    
    // The main form container for shipping details
    @FindBy(css = "iron-form#checkout-form")
    private WebElement shippingForm; 

    // Specific input fields within the form
    @FindBy(css = "input[name='name']")
    private WebElement nameInput;
    
    @FindBy(css = "input[name='address']")
    private WebElement addressInput;

    @FindBy(css = "input[name='city']")
    private WebElement cityInput;

    // The entire payment section (for presence check)
    @FindBy(css = "#payment-section")
    private WebElement paymentSection; 
    
    // The 'Place Order' button
    @FindBy(css = ".submit-button button")
    private WebElement placeOrderButton;

    // --- Locators for Order Summary and Confirmation ---

    // Total price in the order summary
    @FindBy(css = ".order-summary .total-price")
    private WebElement orderTotalElement;
    
    // An element indicating a product is present in the summary
    @FindBy(css = ".order-summary .item-name")
    private WebElement itemInSummary;

    // Confirmation text/page element after submission
    @FindBy(css = "shop-checkout > .main-container h1")
    private WebElement confirmationHeader;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // --- Verification Methods ---

    public boolean isShippingFormDisplayed() {
        return shippingForm.isDisplayed();
    }
    
    public boolean isPaymentSectionDisplayed() {
        return paymentSection.isDisplayed();
    }
    
    public boolean isOrderSummaryDisplayed() {
        // We can use the total element as a proxy for the entire summary being present
        return orderTotalElement.isDisplayed(); 
    }
    
    public String getOrderTotal() {
        return getText(orderTotalElement);
    }

    public boolean isItemInSummaryDisplayed() {
        return itemInSummary.isDisplayed();
    }
    
    /**
     * Checks if the order confirmation page is displayed by looking at the header text.
     * Assumes successful navigation to /confirmation.
     */
    public boolean isOrderConfirmed() {
        // This confirms the page has loaded and the confirmation message is visible.
        return getText(confirmationHeader).contains("Thank you");
    }

    // --- Action Methods ---

    public void fillShippingDetails(String name, String address, String city) {
        type(nameInput, name);
        type(addressInput, address);
        type(cityInput, city);
    }
    
    /**
     * Clicks the 'Place Order' button.
     */
    public void placeOrder() {
        click(placeOrderButton);
    }
    
    /**
     * Retrieves the validation message shown for a specific field name.
     * Note: This relies on the field's title or attribute as visible validation is challenging 
     * in the Polymer component model. We retrieve the title attribute which often holds the required prompt.
     * @param fieldName The name of the input field (e.g., "name", "address").
     * @return The validation message (or field title) as a string.
     */
    public String getValidationMessage(String fieldName) {
        // Locate the specific input element by its name
        WebElement field = driver.findElement(By.cssSelector("input[name='" + fieldName + "']"));
        // For Polymer forms, checking the 'title' attribute can sometimes confirm a required state
        // or a validation property is set, as native browser validation prevents element interaction.
        return field.getAttribute("title"); 
    }
}
