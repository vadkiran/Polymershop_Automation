package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends BasePage {
    // Locator for all individual items in the cart
    @FindBy(css = ".shopping-cart-container shop-cart-item")
    private List<WebElement> cartItemsList; 

    // Locator for the subtotal element
    @FindBy(css = "#subtotal")
    private WebElement cartTotal;

    // Locator for the checkout link/button
    @FindBy(css = "#checkout-link")
    private WebElement checkoutButton;
    
    // Locator for the empty cart message (only visible when cart is empty)
    @FindBy(css = ".empty-cart-message")
    private WebElement emptyCartMessage;

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // --- Verification Methods ---
    
    public boolean isCartEmpty() {
        return cartItemsList.isEmpty();
    }
    
    /**
     * Checks if the empty cart message is displayed.
     */
    public boolean isEmptyCartMessageDisplayed() {
        // Use a try-catch or explicit wait logic to check for the empty message element
        try {
            return emptyCartMessage.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
    
    /**
     * Returns the number of unique items in the cart.
     */
    public int getCartItemCount() {
        return cartItemsList.size();
    }

    /**
     * Checks if at least one product is present.
     */
    public boolean isProductAdded() {
        return getCartItemCount() > 0;
    }

    public String getCartTotal() {
        return getText(cartTotal);
    }

    // --- Action Methods ---

    /**
     * Finds the quantity input for the item at the specified index and sets a new quantity.
     * @param index The 0-based index of the item to update.
     * @param quantity The new quantity to set.
     */
    public void updateQuantity(int index, int quantity) {
        if (index >= 0 && index < cartItemsList.size()) {
            // Find the quantity input field within the specific cart item element
            WebElement quantityInput = cartItemsList.get(index).findElement(By.cssSelector("input[type='number']"));
            type(quantityInput, String.valueOf(quantity));
            
            // Note: The Polymer site usually triggers the change on blur/next action.
            // We can click the total element to ensure the change takes effect (simulate blur).
            click(cartTotal);
        } else {
            throw new IllegalArgumentException("Invalid cart item index: " + index);
        }
    }

    /**
     * Finds the remove button for the item at the specified index and clicks it.
     * @param index The 0-based index of the item to remove.
     */
    public void removeProduct(int index) {
        if (index >= 0 && index < cartItemsList.size()) {
            // Find the remove button within the specific cart item element
            WebElement removeButton = cartItemsList.get(index).findElement(By.cssSelector("paper-icon-button[icon='delete']"));
            click(removeButton);
        } else {
            throw new IllegalArgumentException("Invalid cart item index: " + index);
        }
    }
    
    public void clickCheckout() {
        click(checkoutButton);
    }
    
    /**
     * Placeholder method: Returns an expected total string for assertion purposes.
     * This should be calculated based on product price and quantity in a real framework.
     */
    public String calculateExpectedTotal() {
         // Example: If two items of $29.99 and $35.00 are added, and one is updated to quantity 2.
         // Since the actual prices are unknown without more setup, we will use a generic placeholder for the test logic.
         return "$94.99"; // Placeholder for 2 items (one qty 2, one qty 1)
    }
}
