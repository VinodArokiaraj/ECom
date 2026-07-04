# Test Cases Documentation

## Test Case 1: Complete Purchase Flow (Place Order)

**Test Case ID:** TC_01_PlaceOrder

**Test Case Name:** Verify user can successfully place an order with product selection and checkout

**Description:** 
This test validates the complete end-to-end purchase flow in the e-commerce application. It tests user login, product selection, cart management, checkout process, and order confirmation.

**Pre-conditions:**
- Application is accessible at: `https://rahulshettyacademy.com/client/`
- Test data is available in `src/test/java/Data/purchaseOrder.json`
- Browser is set to Chrome (configurable via `GlobalData.properties` or `-Dbrowser` parameter)
- WebDriver is initialized and landing page is loaded

**Test Data:**
Two sets of data are used (data-driven test):
1. **User 1:**
   - Email: `VinodAV@yopmail.com`
   - Password: `Testing@01`
   - Product Name: `ZARA COAT 3`
   - Country: `India`
   - Expected Confirmation Message: `Thankyou for the order.`
   - First Name: `Vinod`
   - Last Name: `Arokiaraj`
   - Mobile: `9999999999`
   - Occupation: `Engineer`
   - Gender: `Male`
   - Accept Terms: `true`

2. **User 2:**
   - Email: `Vayne.nicolas@gmail.com`
   - Password: `Testing@01`
   - Product Name: `ADIDAS ORIGINAL`
   - Country: `India`
   - Expected Confirmation Message: `Thankyou for the order.`
   - First Name: `Vayne`
   - Last Name: `Nicolas`
   - Mobile: `8888888888`
   - Occupation: `Student`
   - Gender: `Female`
   - Accept Terms: `true`

**Test Steps:**

| Step # | Action | Expected Result |
|--------|--------|-----------------|
| 1 | User opens the landing page | Landing page loads successfully with login form displayed |
| 2 | User enters email address (from test data) | Email field is populated with the test data email |
| 3 | User enters password (from test data) | Password field is populated with the test data password |
| 4 | User clicks the Login button | User is logged in and redirected to the Product Catalogue page |
| 5 | System retrieves the product list | Product list is loaded and displayed on the page |
| 6 | User searches for and selects the product (from test data, e.g., "ZARA COAT 3") | The specified product is found and added to cart |
| 7 | User clicks "View Cart" or navigates to cart page | Cart page is displayed showing the added product |
| 8 | System verifies the product is displayed in cart | The product name matches the expected product name from test data |
| 9 | User assertion passes for product display in cart | Test continues (product match verified) |
| 10 | User clicks "Checkout" button | Checkout page is displayed with order summary |
| 11 | User selects country (from test data: "India") | Country is selected in the checkout form |
| 12 | User clicks "Place Order" or "Submit" button | Order is submitted and confirmation page is displayed |
| 13 | System retrieves confirmation message | Confirmation message is extracted from the page |
| 14 | Test verifies confirmation message equals expected message | Assertion passes: Message matches "Thankyou for the order." |
| 15 | Test stores credentials for dependent test (orderHistoryTest) | Email, password, and product name are stored as instance variables |

**Expected Result:**
- Test passes with 0 failures
- Order is successfully placed
- Confirmation message is verified
- User credentials are stored for next test execution

**Test Method Name:** `placeOrder(HashMap<String, String> input)`

**Test Annotations:**
- `@Test(dataProvider = "getData", groups = {"Purchase"})`
- Data provider: `getData()` - reads from `purchaseOrder.json`
- Test group: `"Purchase"` - part of Purchase test suite

**Test Execution:**
```bash
# Run this specific test
mvn test -P Purchase

# Or run using TestNG class
mvn -Dtest=tests.SubmitOrder test
```

---

## Test Case 2: Verify Order History After Placement

**Test Case ID:** TC_02_OrderHistoryTest

**Test Case Name:** Verify user can view placed order in order history page

**Description:**
This test verifies that after successfully placing an order, the user can navigate to the order history page and see the order they just placed. This test depends on the successful execution of TC_01_PlaceOrder.

**Pre-conditions:**
- Test Case TC_01_PlaceOrder must execute successfully first
- User credentials from TC_01 are stored in instance variables
- Product name from TC_01 is stored for verification
- Same browser session is maintained

**Test Data:**
Uses stored data from previous test execution:
- Email address (from `lastUserEmail`)
- Password (from `lastPassword`)
- Product Name (from `lastProductName`)

**Test Steps:**

| Step # | Action | Expected Result |
|--------|--------|-----------------|
| 1 | Test retrieves stored email and password from previous test | Stored credentials are available (lastUserEmail, lastPassword) |
| 2 | User logs in using stored credentials | User is logged in successfully |
| 3 | System displays Product Catalogue page | Product Catalogue page is loaded |
| 4 | User navigates to "My Orders" or Order History page | Order History page is displayed |
| 5 | System retrieves the list of user's orders | Previous orders are loaded and displayed |
| 6 | Test searches for the product placed in TC_01 (stored in lastProductName) | Product order is found in the order history |
| 7 | Test assertion verifies the product is displayed in order history | Assertion passes: Product name matches expected product |

**Expected Result:**
- Test passes with 0 failures
- User can successfully view their placed order in order history
- Product name from the placed order is visible in the order history page

**Test Method Name:** `orderHistoryTest()`

**Test Annotations:**
- `@Test(dependsOnMethods = {"placeOrder"})`
- Dependency: This test only runs after `placeOrder` completes successfully
- If `placeOrder` fails, this test is skipped

**Test Execution:**
```bash
# Run tests together (both Purchase suite tests)
mvn test -P Purchase

# Or run using TestNG class
mvn -Dtest=tests.SubmitOrder test
```

---

## Data Provider Information

**Data Provider Method:** `getData()`

**Purpose:** Provides test data in a parameterized format for running the same test with multiple datasets

**Data Source:** `src/test/java/Data/purchaseOrder.json`

**Format:** Returns `Object[][]` where each row contains a HashMap with all required test parameters

**Test Data Fields:**
- `userEmail` - User's login email
- `password` - User's login password
- `productName` - Name of the product to purchase
- `firstName` - User's first name (for registration)
- `lastName` - User's last name (for registration)
- `mobile` - User's mobile number (for registration)
- `occupation` - User's occupation from dropdown (for registration)
- `gender` - User's gender selection (for registration)
- `userCountry` - Country to select during checkout
- `confirmMsg` - Expected order confirmation message
- `acceptTerms` - Whether to accept terms (for registration)

---

## Test Execution Summary

### Running All Tests
```bash
mvn clean test
```

### Running Purchase Suite Only
```bash
mvn test -P Purchase
```

### Running Specific Test Class
```bash
mvn -Dtest=tests.SubmitOrder test
```

### Running with Headless Chrome
```bash
mvn test -P Purchase -Dbrowser=chromeheadless
```

### Running with Firefox
```bash
mvn test -P Purchase -Dbrowser=firefox
```

---

## Test Artifacts & Reports

**Screenshots:** Generated on test failure
- Location: `/reports/{testCaseName}.png`
- Example: `/reports/placeOrder.png`

**Test Reports:** 
- Extent Report: `/reports/index.html`
- Surefire Report: `/target/surefire-reports/`
- Cucumber Report (if using Cucumber): `/target/cucumber.html`

---

## Notes

1. **Registration Flow:**
   - Registration code is currently commented out in the `placeOrder` method
   - To enable registration, uncomment the registration block and provide the registration page URL
   - Registration page object: `RegistrationPage.java` is ready to use

2. **Test Dependencies:**
   - `orderHistoryTest` depends on successful execution of `placeOrder`
   - If `placeOrder` fails, `orderHistoryTest` will be automatically skipped

3. **Data-Driven Testing:**
   - Test runs twice (once for each dataset in `purchaseOrder.json`)
   - Each iteration uses different user credentials and product names

4. **Browser Management:**
   - Default browser: Chrome (configured in `GlobalData.properties`)
   - Override at runtime using `-Dbrowser=` parameter
   - Supported browsers: Chrome, Firefox, Edge, Safari

