package tests;

import TestComponents.BaseTest;
import TestComponents.DataProviderUtils;
import TestComponents.Retry;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckOutPage;
import pages.ConfirmationPage;
import pages.ProductCatalogue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ErrorValidations extends BaseTest {

    @Test(dataProvider = "errorValidationData", dataProviderClass = DataProviderUtils.class, groups = {"ErrorHandling"}, retryAnalyzer = Retry.class)
    public void loginErrorValidation(HashMap<String, String> input) throws IOException, InterruptedException {

        landingPage.LoginApplication(
                input.get("userEmail"),
                input.get("password")
        );

        Assert.assertEquals(
                landingPage.getErrorMessage(),
                input.get("errorMsg")
        );
    }

    @Test(dataProvider = "errorValidationData", dataProviderClass = DataProviderUtils.class, groups = {"ErrorHandling"}, retryAnalyzer = Retry.class)
    public void productErrorValidation(HashMap<String, String> input) throws IOException {

        ProductCatalogue productCatalogue = landingPage.LoginApplication(input.get("userEmail"), input.get("password"));
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(input.get("productName"));
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.verifyProductDisplay(input.get("productName"));
        Assert.assertTrue(match);

    }
}
