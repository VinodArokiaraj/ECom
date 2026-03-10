package tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import TestComponents.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;

public class SubmitOrder extends BaseTest {

    String userEmail = "VinodAV@yopmail.com";
    String password = "Testing@01";
    String userCountry = "India";
    String confirmMsg = "Thankyou for the order.";

    String productName = "ZARA COAT 3";

    @Test(dataProvider = "getData", groups = {"Purchase"})
    public void placeOrder(HashMap<String, String> input) throws IOException {
//public void placeOrder(String userEmail, String password, String productName) throws IOException {
        ProductCatalogue productCatalogue = landingPage.LoginApplication(input.get("userEmail"), input.get("password"));
        //ProductCatalogue productCatalogue = landingPage.LoginApplication(userEmail, password);
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(input.get("productName"));
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.verifyProductDisplay(input.get("productName"));
        Assert.assertTrue(match);
        CheckOutPage checkOut = cartPage.goToCheckOut();
        checkOut.country(userCountry);
        ConfirmationPage confirmationPage = checkOut.submitOrder();
        String confirmationMsg = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmationMsg.equalsIgnoreCase(confirmMsg));

    }

    @Test(dependsOnMethods = {"placeOrder"})
    public void orderHistoryTest() {
        ProductCatalogue productCatalogue = landingPage.LoginApplication(userEmail, password);
        OrderPage orderPage = productCatalogue.goToOrdersPage();
        Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
    }

    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "/src/test/java/Data/purchaseOrder.json");
        return new Object[][]{{data.get(0)}, {data.get(1)}};
    }

    /* @DataProvider
    public Object[][] getData() {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("userEmail", "VinodAV@yopmail.com");
        map.put("password", "Testing@01");
        map.put("productName", "ZARA COAT 3");

        HashMap<String,String> map1 = new HashMap<String,String>();
        map1.put("userEmail", "Vayne.nicolas@gmail.com");
        map1.put("password", "Testing@01");
        map1.put("productName", "ADIDAS ORIGINAL");

        return new Object[][]{{map}, {map1}};
        //return new Object[][]{{"VinodAV@yopmail.com", "Testing@01", "ZARA COAT 3"}, {"Vayne.nicolas@gmail.com", "Testing@01", "ADIDAS ORIGINAL"}};
    }   */
}