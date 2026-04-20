package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ElementHelper {
    private final AppiumDriver driver;
    private final WebDriverWait wait;
    private final Actions action;

    public ElementHelper(AppiumDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.action = new Actions(driver);
    }

    // Element presence check
    public WebElement presenceElement(By locator){
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // Find element
    public WebElement findElement(By locator){
        return presenceElement(locator);
    }

    // Click on element
    public void click(By locator){
        WebElement element = findElement(locator);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    // Send keys to element
    public void sendKeys(By locator, String text){
        WebElement element = findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    // Check element visibility by locator
    public boolean checkVisible(By locator){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Check element visibility by WebElement (useful for Page Factory fields)
    public boolean checkVisible(WebElement element){
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Scroll to element
    public void scrollToElement(By locator) {
        WebElement element = findElement(locator);
        action.moveToElement(element).perform();
    }

    // Get text from element
    public String getElementText(By locator) {
        return findElement(locator).getText();
    }

    // Check if element is displayed
    public boolean isElementDisplayed(By locator) {
        try {
            return findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
