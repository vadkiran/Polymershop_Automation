package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends BasePage {
    @FindBy(css = ".shopping-cart-container shop-cart-item")
    private WebElement cartItem;

    @FindBy(css = "#subtotal")
    private WebElement cartTotal;

    @FindBy(css = "#checkout-link")
    private WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isProductAdded() {
        return cartItem.isDisplayed();
    }

    public String getCartTotal() {
        return cartTotal.getText();
    }

    // This method would be a placeholder, the actual calculation logic needs to be implemented.
    public String calculateExpectedTotal() {
        return "$65.00"; // Placeholder value
    }

    public void clickCheckout() {
        click(checkoutButton);
    }
}