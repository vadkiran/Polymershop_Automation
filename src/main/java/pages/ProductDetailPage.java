package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductDetailPage extends BasePage {
    @FindBy(css = ".product-details-button-container iron-selector")
    private WebElement sizeSelector;

    @FindBy(css = "shop-item-detail > .main-container > [aria-label='Add to Cart']")
    private WebElement addToCartButton;

    public ProductDetailPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void selectSize(String size) {
        WebElement sizeElement = sizeSelector.findElement(By.xpath("//span[text()='" + size + "']"));
        click(sizeElement);
    }

    public void clickAddToCart() {
        click(addToCartButton);
    }
}