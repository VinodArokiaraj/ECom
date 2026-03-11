Feature: Purchase the order from ECommerce Website

  Background:
    Given I landed on ECommerce page

    @Regression
  Scenario Outline: Positive test of submitting an order
    Given Logged in with username <userEmail> and password <password>
    When I add product <productName> to Cart
    And Checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed in ConfirmationPage

    Examples:
    |userEmail          |password   |productName|
    |VinodAV@yopmail.com|Testing@01 |ZARA COAT 3|