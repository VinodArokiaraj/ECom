package tests;

import TestComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProductCatalogue;

import java.io.IOException;

public class ErrorValidations extends BaseTest {

    @Test
    public void placeOrder() throws IOException, InterruptedException {

        String userEmail = "VinodAV@yopmail.com";
        String password = "Testing@01";
        String productName = "ZARA COAT 3";
        landingPage.LoginApplication(userEmail, password);
        Assert.assertEquals("Incorrect email or password.","landingPage.getErrorMessage()");
    }
}
