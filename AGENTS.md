# AGENTS.md — Quick guide for AI coding agents

Purpose: give an AI (or new contributor) the condensed, actionable knowledge to be immediately productive in this repository.

1) Big picture (what to read first)
- This is a Selenium + TestNG/Cucumber test automation project using the Page Object Model (POM).
- Key directories: `src/main/java/pages` (page objects), `src/main/java/AbstractComponent` (shared waits/navigation), `src/test/java/TestComponents` (test infrastructure), `src/test/java/tests` (TestNG tests), `src/test/java/cucumber` (features + runner) and `src/test/java/stepDefinitions`.

2) Core design patterns / conventions to follow
- Page Objects: Every page class (e.g. `ProductCatalogue.java`, `CartPage.java`) extends `AbstractComponents.AbstractComponents` and uses `PageFactory.initElements(driver, this)` in the constructor.
- Navigation methods return page objects (fluent style). Example: `public CheckOutPage goToCheckOut() { checkOut.click(); return new CheckOutPage(driver); }` (`CartPage.java`).
- Waits/Sync: shared wait helpers live in `AbstractComponents/AbstractComponents.java` (e.g. `waitForElementToAppear(By)`, `waitForElementToDisappear(By)`) — use them before asserting or interacting.
- Locators use CSS selectors heavily (see `ProductCatalogue` where `By productsBy = By.cssSelector(".mb-3")`).
- Test data: JSON-driven tests live in `src/test/java/Data` and are loaded via `BaseTest.getJsonDataToMap(...)`.

3) Test / build / run workflows (practical commands)
- Standard Maven build: `mvn clean test` (uses default surefire configuration). Use these profiles for targeted runs:
  - Purchase suite: `mvn test -P Purchase` (runs `testSuites/Purchase.xml`)
  - Regression: `mvn test -P Regression` (runs `testSuites/testng.xml`)
  - Error validation: `mvn test -P ErrorValidation`
- Cucumber TestNG runner (uses tags): `mvn test -P CucumberTests` — runner is `src/test/java/cucumber/TestNGTestRunner.java` and it uses `@CucumberOptions(tags="@Regression")`.
- Override browser at runtime: the `BaseTest.initializeDriver()` reads `System.getProperty("browser")` OR `src/main/resources/GlobalData.properties`. Example: to run headless Chrome: `mvn test -Dbrowser=chromeheadless -P Purchase` (code checks `contains("headless")`).

4) Environment / external dependencies
- Uses WebDriverManager to auto-download drivers (`io.github.bonigarcia:webdrivermanager`) — internet access is required on first run.
- Browsers supported: Chrome, Firefox, Edge, Safari (see `BaseTest.initializeDriver`).
- Test reporting: extent reports saved to `/reports/index.html` via `resources/ExtentReporterNG.java`. Cucumber output at `target/cucumber.html`.

5) Project-specific behaviors and pitfalls
- Hard-coded application URL: `landingPage.goTo("https://rahulshettyacademy.com/client/")` (in `BaseTest.launchApplication()`). Change here to point tests to another environment.
- Data-driven tests expect JSON arrays of maps (see `purchaseOrder.json`). `getData()` in `SubmitOrder` yields rows using `getJsonDataToMap(...)`.
- Locating product by name: `ProductCatalogue.getProductsByName(...)` throws `RuntimeException("Product not found: ...")` if not present — agents should patch tests to handle failures gracefully if needed.
- Inconsistent package naming: `AbstractComponent` vs `pages` (lowercase) — be careful with imports when refactoring.

6) Files to open first for feature work or debugging
- `src/test/java/TestComponents/BaseTest.java` — driver + setup + utility helpers (screenshots, json loader).
- `src/main/java/AbstractComponent/AbstractComponents.java` — wait helpers and navigation used across pages.
- `src/main/java/pages/ProductCatalogue.java` and `CartPage.java` — contains most interaction logic for purchase flow.
- `src/test/java/tests/SubmitOrder.java` — canonical end-to-end TestNG test to follow the happy path.
- `pom.xml` — profiles for running suites and declared dependencies.

7) How to add a new E2E test (minimal checklist for an agent)
- Add a new page object in `src/main/java/pages` following the existing constructor pattern (extend `AbstractComponents` and call `PageFactory.initElements`).
- Add necessary locators as private `By` or `@FindBy` fields, reuse wait helpers from `AbstractComponents`.
- Add a TestNG test class in `src/test/java/tests` or a Cucumber step/feature pair in `src/test/java/cucumber` & `src/test/java/stepDefinitions`.
- If data-driven, add JSON in `src/test/java/Data` and call `getJsonDataToMap` from `BaseTest`.
- Run locally: `mvn -Dtest=tests.SubmitOrder test` or use the appropriate profile.

8) Quick debugging tips
- If driver can't start: check `-Dbrowser` value and WebDriverManager logs; first run downloads binaries.
- If element lookup fails: check waits in `AbstractComponents` and that `PageFactory.initElements` is called in the page constructor.
- Screenshots for failing tests are written to `/reports/{testCaseName}.png` via `BaseTest.getScreenshot`.

9) Where reports & artifacts land
- Screenshots & extent reports: `/reports/` (project root). Example: `reports/index.html`, `reports/placeOrder.png`.
- Surefire/TestNG reports: `target/surefire-reports/` and `target/cucumber.html` for cucumber plugin output.

If you need more details (explain a test flow, add convenience scripts, or generate a checklist for PR reviewers), tell me which area to expand.

