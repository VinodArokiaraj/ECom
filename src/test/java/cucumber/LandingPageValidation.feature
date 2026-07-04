Feature: Landing Page Validation

  Scenario: Verify landing page loads successfully
    Given the user enters a valid URL
    Then the landing page should be displayed successfully

  Scenario: Verify navigation links
    Given the user is on the landing page
    When the user clicks on navigation links
    Then the user should be redirected to the correct pages

  Scenario: Verify restricted page access redirects to login
    Given the user is not logged in
    When the user tries to access a restricted page
    Then the user should be redirected to the login page