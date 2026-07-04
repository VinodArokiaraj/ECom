# Test Case Quick Reference

## TC_01: Place Order (Purchase Flow)
**What it does:** Complete end-to-end order placement (login → add product → checkout → confirm)

**Steps:**
1. Login with test credentials
2. Search & add product to cart
3. Navigate to cart and verify product
4. Checkout with country selection
5. Submit order and verify confirmation message
6. Store credentials for next test

**Data Used:** From `purchaseOrder.json` (2 test scenarios)
- Scenario 1: Vinod + ZARA COAT 3
- Scenario 2: Vayne + ADIDAS ORIGINAL

**Expected Result:** ✅ Pass with confirmation message "Thankyou for the order."

**Run Command:**
```bash
mvn test -P Purchase
```

---

## TC_02: View Order History
**What it does:** Verify placed order appears in order history

**Steps:**
1. Reuse credentials from TC_01
2. Login
3. Navigate to "My Orders" page
4. Verify product is visible in order history

**Pre-requisite:** TC_01 must pass first (depends on its data)

**Expected Result:** ✅ Pass - Product found in order history

**Run Command:**
```bash
mvn test -P Purchase
# (Runs after TC_01 if it passes)
```

---

## Test Data Mapping

| Field | TC_01 (Vinod) | TC_01 (Vayne) | TC_02 |
|-------|---------------|---------------|-------|
| Email | VinodAV@yopmail.com | Vayne.nicolas@gmail.com | Reused |
| Password | Testing@01 | Testing@01 | Reused |
| Product | ZARA COAT 3 | ADIDAS ORIGINAL | Reused |
| Country | India | India | - |
| Confirmation | Thankyou for the order. | Thankyou for the order. | - |

---

## Key Commands

```bash
# Run all tests
mvn clean test

# Run Purchase suite only (TC_01 + TC_02)
mvn test -P Purchase

# Run with headless Chrome
mvn test -P Purchase -Dbrowser=chromeheadless

# Run specific test
mvn -Dtest=tests.SubmitOrder test

# View reports
open reports/index.html          # Extent Report
open target/surefire-reports/    # TestNG Report
```

---

## Test Flow Diagram

```
Start
  ↓
TC_01: placeOrder (Scenario 1)
  ├─ Login (Vinod)
  ├─ Add ZARA COAT 3 to cart
  ├─ Checkout
  ├─ Verify Confirmation ✓
  └─ Store Email, Password, Product
     ↓
    TC_02: orderHistoryTest
    └─ Verify product in order history ✓
       ↓
TC_01: placeOrder (Scenario 2)
  ├─ Login (Vayne)
  ├─ Add ADIDAS ORIGINAL to cart
  ├─ Checkout
  ├─ Verify Confirmation ✓
  └─ Store Email, Password, Product
     ↓
    TC_02: orderHistoryTest
    └─ Verify product in order history ✓
       ↓
    End ✓
```

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Tests fail to find elements | Check if page URL is correct in `BaseTest.launchApplication()` |
| Timeout on element wait | Increase wait duration in `AbstractComponents` (currently 5 seconds) |
| Browser won't launch | Try `mvn test -Dbrowser=firefox` or ensure ChromeDriver is available |
| Tests skip (TC_02 skipped) | Verify TC_01 passed; TC_02 depends on it |

---

## File Locations

- **Test Class:** `src/test/java/tests/SubmitOrder.java`
- **Test Data:** `src/test/java/Data/purchaseOrder.json`
- **Page Objects:** `src/main/java/pages/*.java`
- **Base Test Setup:** `src/test/java/TestComponents/BaseTest.java`
- **Test Reports:** `/reports/` and `/target/surefire-reports/`

---

## Notes

- **Data-Driven:** Test runs for each row in `purchaseOrder.json` (2 iterations)
- **Dependent Tests:** TC_02 only runs if TC_01 passes
- **Registration (Commented):** uncomment registration block when registration page URL is available
- **Browser Default:** Chrome (override with `-Dbrowser=firefoxor chromeheadless`)

