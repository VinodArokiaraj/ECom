package stepDefinitions;

import TestComponents.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.*;

import java.io.IOException;

/**
 * Step Definitions for SubmitOrder.feature
 *
 * This class contains all Gherkin step implementations for the purchase order submission feature.
 * It follows BDD (Behavior-Driven Development) approach and creates a readable bridge between
 * Gherkin scenarios and Java automation code.
 *
 * Feature: Purchase the order from ECommerce Website
 * - Background: Initialize landing page
 * - Scenario Outline: Positive test of submitting an order (data-driven with Examples)
 *
 * @author QA Team
 * @version 1.0
 */
public class SubmitOrderStepDefinitions extends BaseTest {

    // Page object references
    private LandingPage landingPage;
    private ProductCatalogue productCatalogue;
    private CartPage cartPage;
    private CheckOutPage checkOutPage;
    private ConfirmationPage confirmationPage;

    /**
     * Background Step: I landed on ECommerce page
     *
     * Initializes the application and launches the landing page.
     * This step runs before each scenario to set up the test environment.
     *
     * @throws IOException if there's an error during application initialization
     */
    @Given("I landed on ECommerce page")
    public void i_landed_on_ecommerce_page() throws IOException {
        landingPage = launchApplication();
    }

    /**
     * Given Step: Logged in with username {userEmail} and password {password}
     *
     * Performs login with provided credentials and navigates to the product catalogue.
     * Uses regex pattern to extract email and password from the Gherkin step.
     *
     * @param userEmail the email address for login
     * @param password the password for login
     */
    @Given("^Logged in with username (.+) and password (.+)$")
    public void logged_in_with_username_and_password(String userEmail, String password) {
        // Perform login and get ProductCatalogue page object
        productCatalogue = landingPage.LoginApplication(userEmail, password);
    }

    /**
     * When Step: I add product {productName} to Cart
     *
     * Searches for the specified product in the product list and adds it to the shopping cart.
     *
     * @param productName the name of the product to add to cart (e.g., "ZARA COAT 3")
     */
    @When("^I add product (.+) to Cart$")
    public void i_add_product_to_cart(String productName) {
        // Retrieve and display product list (triggers wait for elements to load)
        productCatalogue.getProductList();

        // Add the specified product to cart
        productCatalogue.addProductToCart(productName);
    }

    /**
     * And Step: Checkout {productName} and submit the order
     *
     * Performs the complete checkout process:
     * 1. Navigates to cart page
     * 2. Verifies the product is in the cart
     * 3. Proceeds to checkout
     * 4. Selects country for delivery
     * 5. Submits the order
     *
     * @param productName the name of the product to verify in cart
     */
    @And("^Checkout (.+) and submit the order$")
    public void checkout_and_submit_the_order(String productName) {
        // Navigate to cart page
        cartPage = productCatalogue.goToCartPage();

        // Verify the product is displayed in the cart
        Boolean isProductInCart = cartPage.verifyProductDisplay(productName);
        Assert.assertTrue(isProductInCart, "Product '" + productName + "' not found in cart");

        // Navigate to checkout page
        checkOutPage = cartPage.goToCheckOut();

        // Select delivery country
        checkOutPage.country("India");

        // Submit the order and get confirmation page
        confirmationPage = checkOutPage.submitOrder();
    }

    /**
     * Then Step: "{expectedMessage}" message is displayed in ConfirmationPage
     *
     * Extracts and verifies the confirmation message after order submission.
     * Asserts that the actual message matches the expected message (case-insensitive).
     *
     * @param expectedMessage the expected confirmation message (e.g., "THANKYOU FOR THE ORDER.")
     */
    @Then("^\"([^\"]*)\" message is displayed in ConfirmationPage$")
    public void confirmation_message_is_displayed(String expectedMessage) {
        // Get the actual confirmation message from the page
        String actualMessage = confirmationPage.getConfirmationMessage();

        // Verify the message matches (case-insensitive comparison)
        Assert.assertTrue(
            actualMessage.equalsIgnoreCase(expectedMessage),
            "Expected message: '" + expectedMessage + "' but got: '" + actualMessage + "'"
        );

        // Close the browser after successful order verification
        driver.close();
    }
}

