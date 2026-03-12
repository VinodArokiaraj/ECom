package tests;

import TestComponents.BaseTest;
import TestComponents.Retry;
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

    @Test(groups = {"ErrorHandling"}, retryAnalyzer = Retry.class)
    public void loginErrorValidation() throws IOException, InterruptedException {

        String userEmail = "VinodAV@yopmail.coma";
        String password = "Testing@01";
        String productName = "ZARA COAT 3";
        landingPage.LoginApplication(userEmail, password);
        Assert.assertEquals(landingPage.getErrorMessage(), "Incorrect email or password.");
    }

    @Test
    public void productErrorValidation() throws IOException {

        String userEmail = "VinodAV@yopmail.com";
        String password = "Testing@01";
        String productName = "ZARA COAT 3";

        ProductCatalogue productCatalogue = landingPage.LoginApplication(userEmail, password);
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.verifyProductDisplay(productName);
        Assert.assertTrue(match);

    }
}
