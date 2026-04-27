package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ElementHelper;

import java.time.Duration;

public class RegisterPage {
    private final ElementHelper elementHelper;

    @FindBy(id = "Sign Up")
    private WebElement goToRegisterScreenButton;

    @FindBy(xpath = "//XCUIElementTypeTextField[@value=\"First name\"]")
    private WebElement firstNameInput;

    @FindBy(xpath = "//XCUIElementTypeTextField[@value=\"Last name\"]")
    private WebElement lastNameInput;

    @FindBy(xpath = "//XCUIElementTypeTextField[@value=\"Email\"]")
    private WebElement emailInput;

    @FindBy(className = "//XCUIElementTypeSecureTextField[@value=\"Password\"]" )
    private WebElement passwordInput;

    @FindBy(id = "Sign Up")
    private WebElement registerButton;

    @FindBy(id = "SUMMER SALE")
    private WebElement homeScreenElement;

    public RegisterPage(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
        this.elementHelper = new ElementHelper(driver);
    }

    public void navigateToRegisterScreen() {
        goToRegisterScreenButton.click();
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
        return elementHelper.checkVisible(homeScreenElement);
    }
}

