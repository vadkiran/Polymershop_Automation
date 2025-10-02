package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends BasePage {
    @FindBy(css = "iron-form#checkout-form")
    private WebElement shippingForm;

    @FindBy(css = "#payment-section")
    private WebElement paymentSection;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isShippingFormDisplayed() {
        return shippingForm.isDisplayed();
    }

    public void fillShippingDetails(String name, String address, String city) {
        // Implementation to fill out the form
    }
}
