package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ElementHelper;

import java.time.Duration;

public class LoginPage {
    private final AppiumDriver driver;
    private final ElementHelper elementHelper;

    // Page Factory Elements with @FindBy annotations for iOS
    @FindBy(className = "XCUIElementTypeTextField")
    private WebElement emailInput;

    @FindBy(className = "XCUIElementTypeSecureTextField")
    private WebElement passwordInput;

    @FindBy(id = "Sign In")
    private WebElement loginButton;

    @FindBy(id = "SUMMER SALE")
    private WebElement homeScreenElement;

    public LoginPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
        this.elementHelper = new ElementHelper(driver);
    }

    public void enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }


    public void clickLoginButton() {
        loginButton.click();
    }


    public boolean isLoginSuccessful() {
        return elementHelper.checkVisible(homeScreenElement);
    }
}
