package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductListingPage extends BasePage {
    @FindBy(css = ".item-list li")
    private List<WebElement> productList;

    public ProductListingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void selectProduct(int index) {
        if (index >= 0 && index < productList.size()) {
            click(productList.get(index));
        } else {
            throw new IllegalArgumentException("Invalid product index: " + index);
        }
    }
}
