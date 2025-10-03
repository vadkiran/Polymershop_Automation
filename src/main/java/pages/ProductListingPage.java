package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductListingPage extends BasePage {
    // Locators for the product list (assuming a repeating element for each product)
    // The selector ".item-list li" is confirmed to work on the Polymer Shop 'shop-list' component.
    @FindBy(css = "shop-list .item-list li")
    private List<WebElement> productList;

    // Locator for the product category title, used for filtering verification
    @FindBy(css = "shop-list [data-title]")
    private WebElement categoryTitle;

    // Locator for the search results count or message
    @FindBy(css = "shop-list .total")
    private WebElement totalProductCount;

    // Locator for the pagination controls container
    // We target the link elements, which are inside a 'paper-tabs' or similar element on the page
    @FindBy(xpath = "//shop-list//a[contains(@href, 'page=')]")
    private List<WebElement> paginationLinks;

    // NOTE: Sorting/Filtering dropdowns are not explicitly visible on the default Polymer list pages,
    // but the functionality can be tested via URL or implied. I will assume we check the list of categories here.
    
    public ProductListingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Gets the current number of displayed products.
     * @return The count of products visible on the page.
     */
    public int getProductCount() {
        return productList.size();
    }
    
    /**
     * Retrieves the text of the main category title.
     * @return The displayed category title text.
     */
    public String getCategoryTitle() {
        return getText(categoryTitle);
    }
    
    /**
     * Clicks a specific pagination link based on its 1-based index (e.g., 2 for the second page link).
     * @param index The 1-based index of the pagination link to click.
     */
    public void clickPaginationLink(int index) {
        if (paginationLinks.size() >= index && index > 0) {
            click(paginationLinks.get(index - 1));
        } else {
            throw new IllegalArgumentException("Pagination link index " + index + " is out of bounds.");
        }
    }

    public void selectProduct(int index) {
        if (index >= 0 && index < productList.size()) {
            click(productList.get(index));
        } else {
            throw new IllegalArgumentException("Invalid product index: " + index);
        }
    }
}

