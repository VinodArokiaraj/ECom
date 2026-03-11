package stepDefinitions;

import TestComponents.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.*;

import java.io.IOException;
import java.util.List;

public class StepDefinitionImpl extends BaseTest {

    public LandingPage landingPage;
    public ProductCatalogue productCatalogue;
    public ConfirmationPage confirmationPage;

    @Given("I landed on ECommerce page")
    public void I_landed_on_ECommerce_page() throws IOException {
        landingPage = launchApplication();
    }

    @Given("^Logged in with username (.+) and password (.+)$")
    public void Logged_in_with_username_and_password(String username, String password) {
        productCatalogue = landingPage.LoginApplication(username, password);
    }

    @When("^I add product (.+) to Cart$")
    public void i_add_product_to_Cart(String productName) {
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
    }

    @When("^Checkout (.+) and submit the order$")
    public void i_submit_the_order(String productName) {
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.verifyProductDisplay(productName);
        Assert.assertTrue(match);
        CheckOutPage checkOutPage = cartPage.goToCheckOut();
        checkOutPage.country("India");
        confirmationPage = checkOutPage.submitOrder();
    }

    @Then("{string} message is displayed in ConfirmationPage")
    public void message_Is_Displayed_In_ConfirmationPage(String string) {
        String confirmationMsg = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmationMsg.equalsIgnoreCase(string));
        driver.close();
    }

    @Then("{string} message is displayed")
    public void message_Is_Displayed(String message) {
        Assert.assertEquals(landingPage.getErrorMessage(), message);
        driver.close();
    }
}
