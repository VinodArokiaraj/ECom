Feature: Login Functionality

  Scenario: Successful login with valid credentials
    Given the user is on the login page
    When the user enters valid username and password
    And clicks on login button
    Then the user should be redirected to the dashboard

  Scenario: Login with invalid username
    Given the user is on the login page
    When the user enters invalid username and valid password
    And clicks on login button
    Then an error message should be displayed

  Scenario: Login with invalid password
    Given the user is on the login page
    When the user enters valid username and invalid password
    And clicks on login button
    Then an error message should be displayed

  Scenario: Login with empty fields
    Given the user is on the login page
    When the user clicks login without entering credentials
    Then validation messages should be displayed

  Scenario: Login with special character input
    Given the user is on the login page
    When the user enters special characters in username and password
    And clicks on login button
    Then login should fail with an error message

  Scenario: Submit with valid inputs
    Given the user is on the login page
    When the user enters valid username and password
    Then the login should be successful

  Scenario: Submit with one field empty
    Given the user is on the login page
    When the user enters only username
    And clicks on login button
    Then a validation message should be displayed

  Scenario: Submit with both fields empty
    Given the user is on the login page
    When the user submits the form without entering any data
    Then validation messages should be displayed