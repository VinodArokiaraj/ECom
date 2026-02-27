package tests;

import TestComponents.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckOutPage;
import pages.ConfirmationPage;
import pages.ProductCatalogue;

import java.io.IOException;
import java.util.List;

public class ErrorValidations extends BaseTest {

    @Test
    public void loginErrorValidation() throws IOException, InterruptedException {

        String userEmail = "VinodAV@yopmail.coma";
        String password = "Testing@01";
        String productName = "ZARA COAT 3";
        landingPage.LoginApplication(userEmail, password);
        Assert.assertEquals("Incorrect email or password.",landingPage.getErrorMessage());
    }

    @Test
    public void productErrorValidation() throws IOException {

        String userEmail = "VinodAV@yopmail.com";
        String password = "Testing@02";
        String productName = "ZARA COAT 3";

        ProductCatalogue productCatalogue = landingPage.LoginApplication(userEmail, password);
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.verifyProductDisplay(productName);
        Assert.assertFalse(match);

    }
}
