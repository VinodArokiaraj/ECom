# SubmitOrder Step Definitions Documentation

## Overview

**File:** `src/test/java/stepDefinitions/SubmitOrderStepDefinitions.java`

The `SubmitOrderStepDefinitions` class implements all Gherkin step definitions for the `SubmitOrder.feature` file using Behavior-Driven Development (BDD) approach.

This class bridges the gap between Gherkin (human-readable feature file) and Java automation code, making tests readable for both technical and non-technical stakeholders.

---

## Architecture

### Design Pattern
- **BDD (Behavior-Driven Development):** Tests are written in plain English using Gherkin syntax
- **Page Object Model (POM):** Each step interacts with page objects, not directly with Selenium
- **Separation of Concerns:** Step definitions are separate from page objects and test automation code

### Class Hierarchy
```
StepDefinitionImpl extends BaseTest
└── Inherits from BaseTest:
    ├── WebDriver driver (shared browser instance)
    ├── LandingPage landingPage (initialized in @BeforeMethod)
    ├── Test setup/teardown methods
    └── Utility methods (screenshots, JSON data loading)
```

---

## Steps Implemented

### 1. Background Step: Landing Page Initialization

**Gherkin:**
```gherkin
Given I landed on ECommerce page
```

**Step Definition:**
```java
@Given("I landed on ECommerce page")
public void i_landed_on_ecommerce_page() throws IOException
```

**Purpose:** Initializes the application by launching the browser and navigating to the landing page.

**What It Does:**
1. Calls `launchApplication()` from BaseTest
2. Initializes the WebDriver
3. Navigates to the application URL
4. Returns a LandingPage page object

**Pre-requisite:** BaseTest must be initialized (driver setup)

---

### 2. Login Step

**Gherkin:**
```gherkin
Given Logged in with username <userEmail> and password <password>
```

**Example from Feature:**
```gherkin
Given Logged in with username VinodAV@yopmail.com and password Testing@01
```

**Step Definition:**
```java
@Given("^Logged in with username (.+) and password (.+)$")
public void logged_in_with_username_and_password(String userEmail, String password)
```

**Purpose:** Performs user authentication and navigates to the product catalog.

**What It Does:**
1. Accepts email and password parameters (extracted from feature file via regex)
2. Calls `LoginApplication()` on the landing page
3. Returns ProductCatalogue page object

**Regex Pattern:** `(.+)` captures any character sequence (emails and passwords)

**Example Full Flow:**
```
Input: "VinodAV@yopmail.com" and "Testing@01"
      ↓
Process: landingPage.LoginApplication("VinodAV@yopmail.com", "Testing@01")
      ↓
Output: productCatalogue (ProductCatalogue page object)
```

---

### 3. Add Product to Cart Step

**Gherkin:**
```gherkin
When I add product <productName> to Cart
```

**Example from Feature:**
```gherkin
When I add product ZARA COAT 3 to Cart
```

**Step Definition:**
```java
@When("^I add product (.+) to Cart$")
public void i_add_product_to_cart(String productName)
```

**Purpose:** Searches for a product and adds it to the shopping cart.

**What It Does:**
1. Retrieves the product list (triggers Selenium waits for page load)
2. Calls `addProductToCart(productName)` which:
   - Searches for product by name using Java streams
   - Throws RuntimeException if product not found
   - Clicks "Add to Cart" button
   - Waits for toast message to disappear

**Regex Pattern:** `(.+)` captures product name with any characters

**Example:**
```
Input: "ZARA COAT 3"
      ↓
Process: productCatalogue.getProductList()
         productCatalogue.addProductToCart("ZARA COAT 3")
      ↓
Output: Product added, toast message shown and cleared
```

---

### 4. Checkout and Submit Order Step

**Gherkin:**
```gherkin
And Checkout <productName> and submit the order
```

**Example from Feature:**
```gherkin
And Checkout ZARA COAT 3 and submit the order
```

**Step Definition:**
```java
@And("^Checkout (.+) and submit the order$")
public void checkout_and_submit_the_order(String productName)
```

**Purpose:** Completes the entire checkout flow from cart to order submission.

**What It Does:**
1. Navigates to cart page: `productCatalogue.goToCartPage()`
2. Verifies product is in cart: `cartPage.verifyProductDisplay(productName)`
   - Uses streams to search for product
   - Asserts product is found
3. Navigates to checkout: `cartPage.goToCheckOut()`
4. Selects country: `checkOutPage.country("India")`
   - Selects from dropdown
   - Waits for options to appear
5. Submits order: `checkOutPage.submitOrder()`
   - Returns ConfirmationPage object

**Assertions Built-in:**
- `Assert.assertTrue(isProductInCart, ...)` - Fails test if product not found

**Example Flow Diagram:**
```
ProductCatalogue
     ↓
goToCartPage()
     ↓
CartPage
     ↓
verifyProductDisplay() [Assert]
     ↓
goToCheckOut()
     ↓
CheckOutPage
     ↓
country("India")
     ↓
submitOrder()
     ↓
ConfirmationPage
```

---

### 5. Verification Step: Confirmation Message

**Gherkin:**
```gherkin
Then "<message>" message is displayed in ConfirmationPage
```

**Example from Feature:**
```gherkin
Then "THANKYOU FOR THE ORDER." message is displayed in ConfirmationPage
```

**Step Definition:**
```java
@Then("^\"([^\"]*)\" message is displayed in ConfirmationPage$")
public void confirmation_message_is_displayed(String expectedMessage)
```

**Purpose:** Verifies the order confirmation message and closes the browser.

**What It Does:**
1. Extracts confirmation message from page: `confirmationPage.getConfirmationMessage()`
2. Asserts message matches (case-insensitive comparison)
   - Uses `equalsIgnoreCase()` for flexibility
3. Closes browser after verification: `driver.close()`

**Regex Pattern:** `\"([^\"]*)\"` captures text between double quotes

**Example:**
```
Input: "THANKYOU FOR THE ORDER."
      ↓
Expected: "THANKYOU FOR THE ORDER."
      ↓
Actual: "Thankyou for the order." (from page)
      ↓
Comparison: "Thankyou for the order.".equalsIgnoreCase("THANKYOU FOR THE ORDER.")
      ↓
Result: ✓ PASS (case-insensitive match)
```

---

## Feature File Integration

### SubmitOrder.feature File
```gherkin
Feature: Purchase the order from ECommerce Website

  Background:
    Given I landed on ECommerce page      ← Step 1

    @Regression
  Scenario Outline: Positive test of submitting an order
    Given Logged in with username <userEmail> and password <password>  ← Step 2
    When I add product <productName> to Cart                           ← Step 3
    And Checkout <productName> and submit the order                    ← Step 4
    Then "THANKYOU FOR THE ORDER." message is displayed in ConfirmationPage ← Step 5

    Examples:
    |userEmail          |password   |productName|
    |VinodAV@yopmail.com|Testing@01 |ZARA COAT 3|
```

### Data-Driven Execution
The feature file uses **Scenario Outline** with **Examples**, which means:
- Same scenario runs for each row in Examples
- Each column value is substituted into the corresponding `<placeholder>`
- Total test runs: 1 scenario × 1 example = 1 execution

---

## Error Handling & Assertions

### Built-in Assertions

1. **Product in Cart Verification**
   ```java
   Assert.assertTrue(isProductInCart, 
       "Product '" + productName + "' not found in cart");
   ```
   - Fails if product is not found in cart
   - Test execution stops at this point

2. **Confirmation Message Verification**
   ```java
   Assert.assertTrue(
       actualMessage.equalsIgnoreCase(expectedMessage),
       "Expected message: '" + expectedMessage + "' but got: '" + actualMessage + "'"
   );
   ```
   - Fails if confirmation message doesn't match
   - Message comparison is case-insensitive

### Exception Handling

**RuntimeException from ProductCatalogue.getProductsByName():**
```java
.orElseThrow(() -> new RuntimeException("Product not found: " + productName));
```
- If product is not found in the catalog
- Test fails with a clear error message

---

## Page Objects Used

| Page Object | Methods Called | Purpose |
|-------------|----------------|---------|
| LandingPage | `LoginApplication(email, password)` | User login |
| ProductCatalogue | `getProductList()`, `addProductToCart(productName)`, `goToCartPage()` | Product browsing & cart |
| CartPage | `verifyProductDisplay(productName)`, `goToCheckOut()` | Cart management |
| CheckOutPage | `country(countryName)`, `submitOrder()` | Order processing |
| ConfirmationPage | `getConfirmationMessage()` | Order confirmation |

### Page Object Hierarchy
```
AbstractComponents (base class with shared waits)
    ├── LandingPage
    ├── ProductCatalogue
    ├── CartPage
    ├── CheckOutPage
    └── ConfirmationPage
```

---

## Running the Tests

### Using Maven (Recommended)

**Run Cucumber tests only (SubmitOrder feature):**
```bash
mvn test -P CucumberTests
```

**Run with specific tags:**
```bash
# Run only @Regression tagged scenarios
mvn test -P CucumberTests -Dtags="@Regression"
```

**Run with headless Chrome:**
```bash
mvn test -P CucumberTests -Dbrowser=chromeheadless
```

**Run with Firefox:**
```bash
mvn test -P CucumberTests -Dbrowser=firefox
```

### Expected Output

```
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

---

## Test Reports

### Cucumber Report
- **Location:** `/target/cucumber.html`
- **Contains:** Feature file, scenarios, steps, timing
- **View:** Open in browser after test execution

### Screenshots
- **Location:** `/reports/{stepName}.png`
- **Created:** Only on test failure
- **Example:** `/reports/placeOrder.png`

### Surefire Report
- **Location:** `/target/surefire-reports/`
- **Contains:** TestNG summary, failures, timing

---

## Best Practices Demonstrated

1. **Regex Patterns:** Steps use regex to extract parameters from feature file text
2. **Fluent API:** Page objects return other page objects for method chaining
3. **Assertions:** Built into step methods, fail fast on issues
4. **Error Messages:** Descriptive messages help debug failures
5. **Case-Insensitive Matching:** Production code often has inconsistent casing
6. **Waits:** Syncs with page loads before assertions
7. **Clean-up:** Browser closed after test completion

---

## Troubleshooting

| Issue | Cause | Solution |
|-------|-------|----------|
| "Product not found: ZARA COAT 3" | Product doesn't exist in catalog | Verify product name spelling in feature file |
| "Product ... not found in cart" | Item wasn't added to cart | Check `addProductToCart()` logic |
| Confirmation message mismatch | Expected/actual message differs | Check expected message in feature file |
| Element not found exception | Page didn't load | Increase wait timeout in AbstractComponents |
| Browser won't close | Session already closed | Check driver.close() isn't called twice |

---

## Key Takeaways

- **SubmitOrderStepDefinitions** bridges Gherkin and Java automation
- Each Gherkin step has a corresponding Java method with `@Given`, `@When`, `@Then` annotations
- Regex patterns extract parameters from feature file steps
- Page objects handle all UI interaction (Selenium)
- Assertions validate behavior within step methods
- Tests are data-driven via Scenario Outline/Examples in feature file

