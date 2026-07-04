
Feature: Error validations

  @ErrorValidation
  Scenario Outline: Positive test of submitting an order
    Given I landed on ECommerce page
    When Logged in with username <userEmail> and password <password>
    Then "Incorrect email  password." message is displayed

    Examples:
      |userEmail          |password   |
      |VinodAV@yopmail.|Testing@02 |