 package pages;

import org.openqa.selenium.WebDriver;
import pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
    @FindBy(css = "a[href='/list']")
    private WebElement menCategoryLink;

    @FindBy(css = "a[href='/list/women']")
    private WebElement womenCategoryLink;

    @FindBy(css = "a[href='/cart']")
    private WebElement cartIcon;

    @FindBy(css = "paper-icon-button[icon='search']")
    private WebElement searchIcon;

    @FindBy(css = "input[aria-label='Search products']")
    private WebElement searchInput;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void navigateToMenCategory() {
        click(menCategoryLink);
    }

    public void navigateToWomenCategory() {
        click(womenCategoryLink);
    }

    public void openCart() {
        click(cartIcon);
    }

    public void searchForProduct(String product) {
        click(searchIcon);
        type(searchInput, product);
    }
}