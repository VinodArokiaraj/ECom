package tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import TestComponents.BaseTest;
import TestComponents.DataProviderUtils;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;

public class SubmitOrder extends BaseTest {

    @Test(dataProvider = "purchaseData", dataProviderClass = DataProviderUtils.class)//, groups = {"Purchase"}
    public void placeOrder(HashMap<String, String> input) throws IOException {

        ProductCatalogue productCatalogue = landingPage.LoginApplication(input.get("userEmail"), input.get("password"));
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(input.get("productName"));
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.verifyProductDisplay(input.get("productName"));
        Assert.assertTrue(match);
        CheckOutPage checkOut = cartPage.goToCheckOut();
        checkOut.country(input.get("userCountry"));
        ConfirmationPage confirmationPage = checkOut.submitOrder();
        String confirmationMsg = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmationMsg.equalsIgnoreCase(input.get("confirmMsg")));

    }

    @Test(dependsOnMethods = "placeOrder", dataProvider = "purchaseData", dataProviderClass = DataProviderUtils.class)
    public void orderHistoryTest(HashMap<String, String> input) throws IOException{

        ProductCatalogue productCatalogue = landingPage.LoginApplication(input.get("userEmail"), input.get("password"));
        OrderPage orderPage = productCatalogue.goToOrdersPage();
        Assert.assertTrue(orderPage.verifyOrderDisplay(input.get("productName")));

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