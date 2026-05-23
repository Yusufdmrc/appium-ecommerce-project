package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class ElementHelper {

    private final AppiumDriver driver;
    private final WebDriverWait wait;
    private final Actions action;

    public ElementHelper(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.action = new Actions(driver);
    }

    public WebElement presenceElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement visibleElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement clickableElement(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement findElement(By locator) {
        return presenceElement(locator);
    }

    public void click(By locator) {
        clickableElement(locator).click();
    }

    public void sendKeys(By locator, String text) {
        WebElement element = visibleElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    public String getElementText(By locator) {
        return visibleElement(locator).getText();
    }

    public String getAttribute(By locator, String attributeName) {
        return findElement(locator).getAttribute(attributeName);
    }

    public boolean checkVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementDisplayed(By locator) {
        try {
            return visibleElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void scrollToText(String text) {
        if (isAndroid()) {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".scrollIntoView(new UiSelector().text(\"" + text + "\"));"
            ));
        } else {
            ((JavascriptExecutor) driver).executeScript("mobile: scroll", Map.of(
                    "predicateString", "label == '" + text + "' OR name == '" + text + "' OR value == '" + text + "'",
                    "toVisible", true
            ));
        }
    }

    public void scrollToElement(By locator) {
        boolean canScrollMore;

        do {
            try {
                WebElement element = driver.findElement(locator);

                if (element.isDisplayed()) {
                    return;
                }

            } catch (Exception ignored) {
            }

            canScrollMore = scrollDown();

        } while (canScrollMore);

        throw new NoSuchElementException("Element not found after scrolling: " + locator);
    }

    public boolean scrollDown() {
        if (isAndroid()) {
            return (Boolean) ((JavascriptExecutor) driver).executeScript(
                    "mobile: scrollGesture",
                    Map.of(
                            "left", 100,
                            "top", 300,
                            "width", 800,
                            "height", 1200,
                            "direction", "down",
                            "percent", 1.0
                    )
            );
        } else {
            ((JavascriptExecutor) driver).executeScript("mobile: scroll", Map.of(
                    "direction", "down"
            ));
            return true;
        }
    }

    public void scrollToEnd() {
        boolean canScrollMore;

        do {
            canScrollMore = scrollDown();
        } while (canScrollMore);
    }

    public void longPress(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "mobile: longClickGesture",
                Map.of(
                        "elementId", ((RemoteWebElement) element).getId(),
                        "duration", 2000
                )
        );
    }

    public void longPress(By locator) {
        WebElement element = visibleElement(locator);
        longPress(element);
    }

    public void swipe(WebElement element, String direction) {
        ((JavascriptExecutor) driver).executeScript(
                "mobile: swipeGesture",
                Map.of(
                        "elementId", ((RemoteWebElement) element).getId(),
                        "direction", direction,
                        "percent", 0.75
                )
        );
    }

    public void swipe(By locator, String direction) {
        WebElement element = visibleElement(locator);
        swipe(element, direction);
    }

    public void tap(By locator) {
        click(locator);
    }

    public boolean isAndroid() {
        String platformName = String.valueOf(
                driver.getCapabilities().getCapability("platformName")
        );
        return platformName.equalsIgnoreCase("Android");
    }

    public boolean isIOS() {
        String platformName = String.valueOf(
                driver.getCapabilities().getCapability("platformName")
        );
        return platformName.equalsIgnoreCase("iOS");
    }
}