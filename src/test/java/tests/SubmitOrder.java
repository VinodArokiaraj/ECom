package tests;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import TestComponents.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class SubmitOrder extends BaseTest {

    String userEmail = "VinodAV@yopmail.com";
    String password = "Testing@01";
    String userCountry = "India";
    String confirmMsg = "Thankyou for the order.";

    String productName = "ZARA COAT 3";

        @Test
        public void placeOrder() throws IOException {

        ProductCatalogue productCatalogue = landingPage.LoginApplication(userEmail, password);
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.verifyProductDisplay(productName);
        Assert.assertTrue(match);
        CheckOutPage checkOut = cartPage.goToCheckOut();
        checkOut.country(userCountry);
        ConfirmationPage confirmationPage = checkOut.submitOrder();
        String confirmationMsg = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmationMsg.equalsIgnoreCase(confirmMsg));

    }

    @Test (dependsOnMethods = {"placeOrder"})
    public void orderHistoryTest() {
        ProductCatalogue productCatalogue = landingPage.LoginApplication(userEmail, password);
        OrderPage orderPage = productCatalogue.goToOrdersPage();
        Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
    }
}