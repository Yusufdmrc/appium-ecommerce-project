package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.MobileActions;

import java.time.Duration;

public class RegisterPage {
    private final MobileActions mobileActions;

    @FindBy(id = "Sign Up")
    private WebElement goToRegisterScreenButton;

    @iOSXCUITFindBy(iOSNsPredicate = "value == 'First name'")
    private WebElement firstNameInput;

    @iOSXCUITFindBy(iOSNsPredicate = "value == 'Last name'")
    private WebElement lastNameInput;

    @iOSXCUITFindBy(iOSNsPredicate = "value == 'Email'")
    private WebElement emailInput;

    @iOSXCUITFindBy(iOSNsPredicate = "type == 'XCUIElementTypeSecureTextField' AND value == 'Password'")
    private WebElement passwordInput;

    @FindBy(id = "Sign Up")
    private WebElement registerButton;

    @FindBy(id = "Sign In")
    private WebElement goToLoginButton;

    @FindBy(id = "SUMMER SALE")
    private WebElement homeScreenElement;

    public RegisterPage(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
        this.mobileActions = new MobileActions(driver);
    }

    public void navigateToRegisterScreen() {
        goToRegisterScreenButton.click();
    }

    public void clickGoToLogin() {
        goToLoginButton.click();
    }

    public void enterFirstName(String firstName) {
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
    }

    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickRegisterButton() {
        registerButton.click();
    }

    public boolean isRegistrationSuccessful() {
        return mobileActions.checkVisible(homeScreenElement);
    }
}

