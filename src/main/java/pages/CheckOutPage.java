package pages;

import AbstractComponent.AbstractComponents;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CheckOutPage extends AbstractComponents {

    WebDriver driver;

    public CheckOutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".action__submit")
    private WebElement placeOrder;

    @FindBy(css = "[placeholder*='Country']")
    private WebElement countryField;

    @FindBy(xpath = "//button/span")
    private List<WebElement> selectCountry;

    private By results = By.cssSelector(".ta-results");

    private By checkOutButton = By.xpath("//button/span");

    public void country(String userCountry) {
        countryField.sendKeys(userCountry);
        visibilityOfElementLocated(checkOutButton);
        selectCountry.stream()
                .filter(country -> country.getText().equalsIgnoreCase(userCountry))
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public ConfirmationPage submitOrder() {
        placeOrder.click();
        return new ConfirmationPage(driver);
    }

}
