package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductDetailPage extends BasePage {
    // Product Attributes Locators
    @FindBy(css = "shop-item-detail .detail-container h1")
    private WebElement productTitle;

    @FindBy(css = "shop-item-detail .detail-container .price")
    private WebElement productPrice;

    @FindBy(css = "shop-item-detail .detail-container .description")
    private WebElement productDescription;

    // Quantity Locators
    @FindBy(css = "shop-item-detail > .main-container > paper-icon-button[icon='add']")
    private WebElement increaseQuantityButton;

    @FindBy(css = "shop-item-detail > .main-container > input[type='number']")
    private WebElement quantityInput;
    
    // Size/Specs Selector Locators
    @FindBy(css = "shop-item-detail .product-details-button-container iron-selector")
    private WebElement sizeSelector;

    // Image Gallery Locators
    @FindBy(css = "shop-item-detail #imageCarousel paper-icon-button")
    private List<WebElement> imageGalleryControls; // Left/Right arrows

    // Action Button Locators
    @FindBy(xpath = "//shop-item-detail//button[@aria-label='Add to Cart']")
    private WebElement addToCartButton; 

    public ProductDetailPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // --- Getter Methods for Test Assertions ---

    public String getProductTitle() {
        return getText(productTitle);
    }
    
    public String getProductPrice() {
        return getText(productPrice);
    }
    
    public String getProductDescription() {
        return getText(productDescription);
    }
    
    /**
     * Retrieves the current value (quantity) from the input field.
     * Used by testQuantitySelection for verification.
     * @return The current quantity value as a String.
     */
    public String getQuantityInputValue() {
        // We use getAttribute("value") to read the current text content of the input field
        return quantityInput.getAttribute("value");
    }
    
    public int getImageGalleryControlCount() {
        // Returns the number of visible controls (e.g., 2 for left and right arrows)
        return imageGalleryControls.size();
    }

    // --- Action Methods for Test Steps ---

    public void selectSize(String size) {
        // Finds the specific size option using XPath by its text content within the selector
        // E.g., size="M" finds a span/div containing "M"
        WebElement sizeElement = sizeSelector.findElement(By.xpath("./*//span[text()='" + size + "']"));
        click(sizeElement);
    }

    public void setQuantity(int quantity) {
        // Clear the input and enter the new quantity
        type(quantityInput, String.valueOf(quantity));
    }

    public void increaseQuantity() {
        click(increaseQuantityButton);
    }
    
    public void clickAddToCart() {
        click(addToCartButton);
    }
}
