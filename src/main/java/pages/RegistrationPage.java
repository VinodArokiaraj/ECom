
package pages;

import AbstractComponent.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

/**
 * Registration page object for the Register form shown in the provided outerHTML.
 * Implements a single action method `registerUser(...)` that fills every input,
 * selects occupation/gender, optionally accepts terms, submits and waits for the toast.
 */
public class RegistrationPage extends AbstractComponents {

    WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "firstName")
    WebElement firstNameField;

    @FindBy(id = "lastName")
    WebElement lastNameField;

    @FindBy(id = "userEmail")
    WebElement emailField;

    @FindBy(id = "userMobile")
    WebElement mobileField;

    @FindBy(css = "select[formcontrolname='occupation']")
    WebElement occupationSelect;

    @FindBy(xpath = "//input[@formcontrolname='gender' and @value='Male']")
    WebElement genderMaleRadio;

    @FindBy(xpath = "//input[@formcontrolname='gender' and @value='Female']")
    WebElement genderFemaleRadio;

    @FindBy(id = "userPassword")
    WebElement passwordField;

    @FindBy(id = "confirmPassword")
    WebElement confirmPasswordField;

    @FindBy(css = "input[type='checkbox'][formcontrolname='required']")
    WebElement termsCheckbox;

    @FindBy(id = "login")
    WebElement registerButton;

    @FindBy(css = "#toast-container")
    WebElement toastContainer;

    /**
     * Fill the registration form and submit.
     * @param fName first name
     * @param lName last name
     * @param email user email
     * @param mobile phone number
     * @param occupation visible occupation text (e.g. "Doctor", "Student", "Engineer", "Scientist")
     * @param gender "Male" or "Female"
     * @param password password and confirmPassword will be set to this value
     * @param acceptTerms whether to tick the 'I am 18 or Older' checkbox
     * @return LandingPage (keeps the project's navigation pattern)
     */
    public LandingPage registerUser(String fName, String lName, String email, String mobile,
                                     String occupation, String gender, String password, boolean acceptTerms) {

        // Fill text inputs
        firstNameField.clear();
        firstNameField.sendKeys(fName);

        lastNameField.clear();
        lastNameField.sendKeys(lName);

        emailField.clear();
        emailField.sendKeys(email);

        mobileField.clear();
        mobileField.sendKeys(mobile);

        // Select occupation by visible text (options render as 'Doctor', 'Student', ...)
        Select sel = new Select(occupationSelect);
        sel.selectByVisibleText(occupation);

        // Select gender radio
        if (gender != null) {
            if (gender.equalsIgnoreCase("male")) {
                if (!genderMaleRadio.isSelected()) genderMaleRadio.click();
            } else {
                if (!genderFemaleRadio.isSelected()) genderFemaleRadio.click();
            }
        }

        // Passwords
        passwordField.clear();
        passwordField.sendKeys(password);

        confirmPasswordField.clear();
        confirmPasswordField.sendKeys(password);

        // Terms checkbox
        if (acceptTerms && !termsCheckbox.isSelected()) {
            termsCheckbox.click();
        }

        // Submit
        registerButton.click();

        // Wait for toast/feedback to appear before returning (re-uses AbstractComponents helper)
        waitForElementToAppear(toastContainer);

        return new LandingPage(driver);
    }

    public String getToastMessage() {
        waitForElementToAppear(toastContainer);
        return toastContainer.getText();
    }

}

