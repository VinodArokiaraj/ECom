# 🛒 ECom – E-Commerce Test Automation Framework

A Java-based **web test automation project** for an e-commerce application, built to demonstrate real-world **QA, UI automation, BDD, test design, and framework development practices**.

The project combines **Selenium WebDriver, TestNG, Cucumber BDD, Maven, Page Object Model, data-driven testing, reusable framework components, parallel execution, retry handling, failure screenshots, and Extent reporting**.

---

## 📌 Project Overview

The project automates key e-commerce user journeys such as:

* User registration
* User login
* Product browsing
* Product selection
* Add to cart
* Checkout
* Order placement
* Order confirmation
* Order history

The automation framework contains both **TestNG-based tests** and **Cucumber BDD scenarios integrated with TestNG**.

---

# 🏗️ Automation Framework

The framework follows a layered architecture:

```text
                    Automation Framework
                            │
             ┌──────────────┴──────────────┐
             │                             │
          TestNG                        Cucumber
             │                             │
        Test Classes                  Feature Files
             │                             │
             │                       Step Definitions
             │                             │
             └──────────────┬──────────────┘
                            │
                       Page Objects
                            │
                    Abstract Components
                            │
                    Selenium WebDriver
                            │
                         Browser
```

### Execution Flow

```text
TestNG Test
    │
    └──► Page Object
              │
              └──► Selenium WebDriver
                        │
                        └──► Browser
```

For Cucumber:

```text
Feature File
    │
    └──► Step Definition
              │
              └──► Page Object
                        │
                        └──► Selenium WebDriver
                                  │
                                  └──► Browser
```

---

# 🛠️ Tech Stack

| Technology                    | Purpose                         |
| ----------------------------- | ------------------------------- |
| **Java 17**                   | Programming language            |
| **Selenium WebDriver 4.18.1** | Browser automation              |
| **TestNG 7.9.0**              | Test execution                  |
| **Cucumber 7.14.0**           | BDD / Gherkin scenarios         |
| **Maven**                     | Build and dependency management |
| **Jackson**                   | JSON test-data processing       |
| **WebDriverManager**          | Browser driver management       |
| **Apache POI**                | Excel-related data handling     |
| **ExtentReports**             | HTML test reporting             |
| **Log4j**                     | Logging                         |
| **Git / GitHub**              | Version control                 |

---

# 🧱 Design Pattern – Page Object Model

The framework follows the **Page Object Model (POM)** design pattern.

Page-specific locators and actions are encapsulated inside dedicated page classes.

### Page Objects

```text
LandingPage
RegistrationPage
ProductCatalogue
CartPage
CheckOutPage
ConfirmationPage
OrderPage
```

This provides:

* Separation of test logic and UI interaction
* Reusable page actions
* Reduced code duplication
* Improved maintainability
* Easier maintenance when UI elements change

Example:

```java
landingPage.LoginApplication(email, password);

productCatalogue.addProductToCart(productName);

cartPage.goToCheckOut();

checkOutPage.submitOrder();
```

---

# 🥒 Cucumber BDD

The framework supports BDD using **Cucumber and Gherkin**.

Feature files include:

```text
ErrorValidations.feature
LandingPageValidation.feature
LoginFunctionality.feature
SubmitOrder.feature
```

Example:

```gherkin
Feature: Submit Order

  Scenario: Place an order successfully
    Given I landed on the Ecommerce Page
    When I logged in with valid credentials
    And I add product "ZARA COAT 3" to Cart
    And I checkout "ZARA COAT 3"
    Then I should see the confirmation message
```

The Gherkin scenarios are mapped to Java step definitions.

```text
Feature File
      ↓
Step Definition
      ↓
Page Object
      ↓
Selenium WebDriver
      ↓
Browser
```

Cucumber is integrated with TestNG through the Cucumber TestNG runner.

---

# 🧪 TestNG

The framework also contains direct TestNG automation tests.

TestNG is used for:

* Test execution
* Test configuration
* Assertions
* Data providers
* Test dependencies
* Listeners
* Retry handling
* Parallel execution
* Test suite management

Test classes include:

```text
SubmitOrder
Order
ErrorValidations
dataDrivenTest
```

---

# 📊 Data-Driven Testing

The framework supports external test data using JSON.

Test-data files include:

```text
purchaseOrder.json
ErrorValidation.json
```

Jackson is used to read and map JSON data into Java objects/collections.

TestNG `DataProvider` is then used to supply the data to the tests.

```text
JSON Test Data
      ↓
Jackson
      ↓
Data Reader
      ↓
TestNG DataProvider
      ↓
Test
```

This allows the same test logic to execute against multiple datasets.

---

# 🌐 Multi-Browser Testing

The framework supports:

* Chrome
* Chrome Headless
* Firefox
* Edge
* Safari

The browser can be supplied through the `browser` system property.

Example:

```bash
mvn test -P Purchase -Dbrowser=chrome
```

Headless Chrome:

```bash
mvn test -P Purchase -Dbrowser=chromeheadless
```

---

# ⚡ Parallel Execution

The TestNG suite supports parallel execution.

The main suite is configured to execute tests in parallel using multiple threads.

This helps reduce overall execution time when running independent tests.

---

# 🔁 Retry Mechanism

The framework contains a custom TestNG retry implementation.

```text
TestComponents/Retry.java
```

The retry mechanism allows failed tests to be re-executed when appropriate, which can help identify and handle transient failures.

---

# 👂 TestNG Listeners

A custom TestNG listener is implemented in:

```text
TestComponents/Listeners.java
```

The listener is used for:

* Test lifecycle events
* Test success/failure handling
* Extent report updates
* Failure screenshots
* Reporting integration

---

# 📸 Failure Screenshots

When a test fails, the framework captures a screenshot and attaches it to the test report.

This makes failures easier to investigate without manually reproducing the failure.

---

# 📈 Test Reporting

The framework uses **ExtentReports** for HTML reporting.

Reports and test artifacts are generated during test execution.

The project also produces:

* TestNG/Surefire reports
* Cucumber HTML reports
* Failure screenshots

---

# 🗂️ Project Structure

```text
selenium-automation/
│
├── src/
│   │
│   ├── main/
│   │   └── java/
│   │       ├── AbstractComponent/
│   │       │   └── AbstractComponents.java
│   │       │
│   │       ├── pages/
│   │       │   ├── LandingPage.java
│   │       │   ├── RegistrationPage.java
│   │       │   ├── ProductCatalogue.java
│   │       │   ├── CartPage.java
│   │       │   ├── CheckOutPage.java
│   │       │   ├── ConfirmationPage.java
│   │       │   └── OrderPage.java
│   │       │
│   │       └── resources/
│   │           └── ExtentReporterNG.java
│   │
│   └── test/
│       └── java/
│           ├── Data/
│           │   ├── DataFileResolver.java
│           │   ├── DataReader.java
│           │   ├── FilePaths.java
│           │   ├── TestDataType.java
│           │   ├── dataDriven.java
│           │   ├── purchaseOrder.json
│           │   └── ErrorValidation.json
│           │
│           ├── TestComponents/
│           │   ├── BaseTest.java
│           │   ├── DataProviderUtils.java
│           │   ├── Listeners.java
│           │   └── Retry.java
│           │
│           ├── cucumber/
│           │   ├── ErrorValidations.feature
│           │   ├── LandingPageValidation.feature
│           │   ├── LoginFunctionality.feature
│           │   ├── SubmitOrder.feature
│           │   └── TestNGTestRunner.java
│           │
│           ├── stepDefinitions/
│           │   └── SubmitOrderStepDefinitions.java
│           │
│           └── tests/
│               ├── ErrorValidations.java
│               ├── Order.java
│               ├── SubmitOrder.java
│               └── dataDrivenTest.java
│
├── testSuites/
│   ├── testng.xml
│   ├── Purchase.xml
│   └── ErrorValidationTests.xml
│
├── pom.xml
├── README.md
├── TEST_CASES.md
├── TEST_CASE_QUICK_REFERENCE.md
├── STEP_DEFINITIONS_GUIDE.md
└── STEP_DEFINITIONS_IMPLEMENTATION_SUMMARY.md
```

---

# 🧪 Test Coverage

The automation suite covers key e-commerce workflows.

### Authentication

* Valid login
* Invalid login
* Error validation

### Product

* Product catalogue validation
* Product selection
* Add product to cart

### Cart

* Product verification
* Cart navigation

### Checkout

* Checkout flow
* Country selection
* Order submission

### Order

* Order confirmation
* Order history

### Registration

* User registration flow

---

# 📋 Test Scenarios

| Test         | Scenario                     |
| ------------ | ---------------------------- |
| Login        | Login with valid credentials |
| Login        | Validate invalid login       |
| Product      | Add product to cart          |
| Cart         | Verify selected product      |
| Checkout     | Complete checkout            |
| Order        | Validate order confirmation  |
| Order        | Validate order history       |
| Registration | Register a new user          |

---

# ▶️ Setup

## Prerequisites

Install:

* JDK 17
* Maven
* Git
* A supported web browser

Verify Java:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

---

# 📥 Clone the Repository

```bash
git clone https://github.com/VinodArokiaraj/selenium-automation.git
```

Navigate to the project:

```bash
cd selenium-automation
```

---

# 🚀 Running the Tests

### Run all tests

```bash
mvn clean test
```

### Purchase suite

```bash
mvn test -P Purchase
```

### Regression suite

```bash
mvn test -P Regression
```

### Error Validation suite

```bash
mvn test -P ErrorValidation
```

### Cucumber BDD tests

```bash
mvn test -P CucumberTests
```

### Run Cucumber with Firefox

```bash
mvn test -P CucumberTests -Dbrowser=firefox
```

### Run Cucumber with headless Chrome

```bash
mvn test -P CucumberTests -Dbrowser=chromeheadless
```

---

# 📊 Maven Test Profiles

The project uses Maven profiles to execute different groups of tests.

```text
CucumberTests
     ↓
Cucumber + TestNG Runner

Regression
     ↓
testng.xml

ErrorValidation
     ↓
ErrorValidationTests.xml

Purchase
     ↓
Purchase.xml
```

This allows different test suites to be executed independently.

---

# 🎯 Key Framework Features

* Selenium WebDriver automation
* Java 17
* Page Object Model
* Cucumber BDD
* Cucumber + TestNG integration
* TestNG automation
* Maven-based execution
* JSON-based test data
* TestNG DataProviders
* Multi-browser testing
* Chrome headless execution
* Parallel execution
* TestNG listeners
* Retry mechanism
* Failure screenshots
* Extent HTML reporting
* Reusable framework components
* Externalized test data

---

# 📚 Documentation

Additional project documentation:

* `TEST_CASES.md` – Detailed test cases
* `TEST_CASE_QUICK_REFERENCE.md` – Test execution reference
* `STEP_DEFINITIONS_GUIDE.md` – Cucumber step-definition documentation
* `STEP_DEFINITIONS_IMPLEMENTATION_SUMMARY.md` – Step-definition implementation details

---

# 🔮 Potential Future Enhancements

Possible future improvements include:

* CI/CD integration
* Docker-based execution
* Cloud browser execution
* API automation integration
* Enhanced logging
* Additional cross-browser coverage
* Improved environment configuration
* Advanced reporting and dashboards

---

# 👨‍💻 Author

**Vinod Arokiaraj**

Test Automation Engineer

**Skills demonstrated in this project:**

`Java` `Selenium` `TestNG` `Cucumber` `Maven` `Page Object Model` `Git` `GitHub`

---

## ⭐ Project Highlights

This project demonstrates practical experience with:

* Designing a maintainable Selenium automation framework
* Implementing Page Object Model
* Building Cucumber BDD scenarios
* Integrating Cucumber with TestNG
* Implementing data-driven testing
* Managing multiple browser configurations
* Running tests in parallel
* Implementing retry and listener mechanisms
* Capturing screenshots on failures
* Generating automated test reports
* Managing the project using Git and Maven
