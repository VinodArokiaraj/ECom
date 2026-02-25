package tests;

import java.time.Duration;
import java.util.List;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ECom {

    @Test
    public void EComm() {

        String userEmail = "VinodAV@yopmail.com";
        String password = "Testing@01";
        String orderItem = "ZARA COAT 3";
        String userCountry = "India";
        String confirmMsg = "Thankyou for the order.";

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();


            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            driver.get("https://rahulshettyacademy.com/client/");

            // Login
            driver.findElement(By.id("userEmail")).sendKeys(userEmail);
            driver.findElement(By.id("userPassword")).sendKeys(password);
            driver.findElement(By.id("login")).click();

            // Wait for products
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
            List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

            // Find product safely
            WebElement prod = products.stream()
                    .filter(product -> product.findElement(By.cssSelector("b"))
                            .getText().equalsIgnoreCase(orderItem))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Product not found: " + orderItem));

            // Add to cart
            prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

            // Wait for toast to disappear safely
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#toast-container")));

            // Go to cart
            driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

            // Validate cart item
            String cartItem = driver.findElement(By.cssSelector(".cartSection h3")).getText();
            System.out.println("Cart Item: " + cartItem);

            if (!cartItem.equalsIgnoreCase(orderItem)) {
                throw new RuntimeException("Item mismatch in cart!");
            }

            // Checkout
            driver.findElement(By.cssSelector(".totalRow button")).click();

            driver.findElement(By.cssSelector("[placeholder*='Country']")).sendKeys(userCountry);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button/span")));

            List<WebElement> countries = driver.findElements(By.xpath("//button/span"));
            countries.stream()
                    .filter(country -> country.getText().equalsIgnoreCase(userCountry))
                    .findFirst()
                    .ifPresent(WebElement::click);

            // Place order
            driver.findElement(By.cssSelector(".action__submit")).click();

            // Confirmation
            String orderConfirmationMsg =
                    driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(orderConfirmationMsg.equalsIgnoreCase(confirmMsg));

        }
    }
