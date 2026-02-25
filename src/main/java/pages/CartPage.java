package pages;

import AbstractComponent.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends AbstractComponents {

    @FindBy(css = ".totalRow button")
    WebElement checkOut;

    @FindBy(css = ".cartSection h3")
    private List<WebElement> productTitles;


    WebDriver driver;

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public Boolean verifyProductDisplay(String productName) {
        Boolean match = productTitles.stream().anyMatch(product -> product.getText().equalsIgnoreCase(productName));
        return match;
    }

    public CheckOutPage goToCheckOut() {
        checkOut.click();
        return new CheckOutPage(driver);
    }

}
