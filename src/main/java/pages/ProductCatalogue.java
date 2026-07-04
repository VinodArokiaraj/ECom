package pages;

import AbstractComponent.AbstractComponents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class ProductCatalogue extends AbstractComponents {

    WebDriver driver;

    public ProductCatalogue(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".mb-3")
    List<WebElement> products;

    @FindBy(css = ".cartSection h3")
    List<WebElement> cart;

    @FindBy(css = ".totalRow button")
    WebElement checkOut;

    By productsBy = By.cssSelector(".mb-3");
    By addToCart = By.cssSelector(".card-body button:last-of-type");
    By toastMessage = By.cssSelector("#toast-container");

    public List<WebElement> getProductList() {
        waitForElementToAppear(productsBy);
        return products;
    }

    public WebElement getProductsByName(String orderItem) {
        WebElement prod = products.stream()
                .filter(product -> product.findElement(By.cssSelector("b"))
                        .getText().equalsIgnoreCase(orderItem))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found: " + orderItem));
        return prod;
    }

    public void addProductToCart(String productName) {
        WebElement prod = getProductsByName(productName);
        prod.findElement(addToCart).click();
        waitForElementToAppear(productsBy);
        waitForElementToDisappear(toastMessage);
    }


    public List<String> cartItems() {
        return cart.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

}
