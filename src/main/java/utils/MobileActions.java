package utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class MobileActions {

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public MobileActions(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // -------------------- WAIT METHODS --------------------

    public WebElement presenceElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement visibleElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement visibleElement(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement clickableElement(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement clickableElement(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    // -------------------- CLICK / TAP --------------------

    public void click(By locator) {
        clickableElement(locator).click();
    }

    public void click(WebElement element) {
        clickableElement(element).click();
    }

    // -------------------- SEND KEYS --------------------

    public void sendKeys(By locator, String text) {
        WebElement element = visibleElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    public void sendKeys(WebElement element, String text) {
        WebElement visibleElement = visibleElement(element);
        visibleElement.clear();
        visibleElement.sendKeys(text);
    }

    public void clear(By locator) {
        visibleElement(locator).clear();
    }

    public void clear(WebElement element) {
        visibleElement(element).clear();
    }

    // -------------------- TEXT / ATTRIBUTE --------------------

    public String getText(By locator) {
        return visibleElement(locator).getText();
    }

    public String getText(WebElement element) {
        return visibleElement(element).getText();
    }

    public String getAttribute(By locator, String attributeName) {
        return visibleElement(locator).getAttribute(attributeName);
    }

    public String getAttribute(WebElement element, String attributeName) {
        return visibleElement(element).getAttribute(attributeName);
    }

    // -------------------- VISIBILITY --------------------

    public boolean checkVisible(By locator) {
        return isDisplayed(locator);
    }

    public boolean checkVisible(WebElement element) {
        return isDisplayed(element);
    }

    public boolean isDisplayed(By locator) {
        try {
            return visibleElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDisplayed(WebElement element) {
        try {
            return visibleElement(element).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementDisplayed(By locator) {
        return isDisplayed(locator);
    }

    public boolean isElementDisplayed(WebElement element) {
        return isDisplayed(element);
    }

    // -------------------- SCROLL --------------------

    public void scrollToText(String text) {
        if (isAndroid()) {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".scrollIntoView(new UiSelector().text(\"" + text + "\"));"
            ));
        } else if (isIOS()) {
            ((JavascriptExecutor) driver).executeScript(
                    "mobile: scroll",
                    Map.of(
                            "predicateString",
                            "label == '" + text + "' OR name == '" + text + "' OR value == '" + text + "'",
                            "toVisible",
                            true
                    )
            );
        }
    }

    public void scrollToElement(By locator) {
        if (isIOS()) {
            scrollToElementIOS(locator);
            return;
        }

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

    public void scrollToElement(WebElement element) {
        if (isIOS()) {
            scrollToElementIOS(element);
            return;
        }

        if (!isDisplayed(element)) {
            scrollDown();
        }
    }

    public void scrollToElementIOS(By locator) {
        WebElement element = presenceElement(locator);
        scrollToElementIOS(element);
    }

    public void scrollToElementIOS(WebElement element) {
        Map<String, Object> params = new HashMap<>();
        params.put("element", ((RemoteWebElement) element).getId());
        params.put("direction", "down");

        ((JavascriptExecutor) driver).executeScript("mobile: scroll", params);
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
        } else if (isIOS()) {
            ((JavascriptExecutor) driver).executeScript(
                    "mobile: scroll",
                    Map.of("direction", "down")
            );
            return true;
        }

        return false;
    }

    public void scrollToEnd() {
        if (isAndroid()) {
            boolean canScrollMore;

            do {
                canScrollMore = scrollDown();
            } while (canScrollMore);
        } else if (isIOS()) {
            for (int i = 0; i < 5; i++) {
                scrollDown();
            }
        }
    }

    // -------------------- LONG PRESS --------------------

    public void longPress(By locator) {
        longPress(visibleElement(locator));
    }

    public void longPress(WebElement element) {
        if (isAndroid()) {
            ((JavascriptExecutor) driver).executeScript(
                    "mobile: longClickGesture",
                    Map.of(
                            "elementId", ((RemoteWebElement) element).getId(),
                            "duration", 2000
                    )
            );
        } else if (isIOS()) {
            Map<String, Object> params = new HashMap<>();
            params.put("element", ((RemoteWebElement) element).getId());
            params.put("duration", 2);

            ((JavascriptExecutor) driver).executeScript(
                    "mobile: touchAndHold",
                    params
            );
        }
    }

    // -------------------- SWIPE --------------------

    public void swipe(By locator, String direction) {
        swipe(visibleElement(locator), direction);
    }

    public void swipe(WebElement element, String direction) {
        if (isAndroid()) {
            ((JavascriptExecutor) driver).executeScript(
                    "mobile: swipeGesture",
                    Map.of(
                            "elementId", ((RemoteWebElement) element).getId(),
                            "direction", direction,
                            "percent", 0.75
                    )
            );
        } else if (isIOS()) {
            Map<String, Object> params = new HashMap<>();
            params.put("element", ((RemoteWebElement) element).getId());
            params.put("direction", direction);

            ((JavascriptExecutor) driver).executeScript(
                    "mobile: swipe",
                    params
            );
        }
    }

    // -------------------- DRAG AND DROP --------------------

    public void dragAndDrop(By locator, int endX, int endY) {
        WebElement source = visibleElement(locator);
        dragAndDrop(source, endX, endY);
    }

    public void dragAndDrop(WebElement source, int endX, int endY) {
        if (isAndroid()) {
            ((JavascriptExecutor) driver).executeScript(
                    "mobile: dragGesture",
                    Map.of(
                            "elementId", ((RemoteWebElement) source).getId(),
                            "endX", endX,
                            "endY", endY
                    )
            );
        } else if (isIOS()) {
            Map<String, Object> params = new HashMap<>();
            params.put("element", ((RemoteWebElement) source).getId());
            params.put("toX", endX);
            params.put("toY", endY);

            ((JavascriptExecutor) driver).executeScript(
                    "mobile: dragFromToForDuration",
                    params
            );
        }
    }

    public void dragAndDrop(By sourceLocator, By targetLocator) {
        WebElement source = visibleElement(sourceLocator);
        WebElement target = visibleElement(targetLocator);
        dragAndDrop(source, target);
    }

    public void dragAndDrop(WebElement source, WebElement target) {
        Point targetLocation = target.getLocation();

        dragAndDrop(
                source,
                targetLocation.getX(),
                targetLocation.getY()
        );
    }

    // -------------------- IOS PICKER --------------------

    public void selectIOSPickerValue(By locator, String value) {
        selectIOSPickerValue(visibleElement(locator), value);
    }

    public void selectIOSPickerValue(WebElement picker, String value) {
        if (!isIOS()) {
            throw new UnsupportedOperationException("selectIOSPickerValue is only supported on iOS.");
        }

        visibleElement(picker).sendKeys(value);
    }

    public String getIOSPickerValue(By locator) {
        return getIOSPickerValue(visibleElement(locator));
    }

    public String getIOSPickerValue(WebElement picker) {
        if (!isIOS()) {
            throw new UnsupportedOperationException("getIOSPickerValue is only supported on iOS.");
        }

        return visibleElement(picker).getText();
    }

    // -------------------- IOS SLIDER --------------------

    public void setIOSSliderValue(By locator, String value) {
        setIOSSliderValue(visibleElement(locator), value);
    }

    public void setIOSSliderValue(WebElement slider, String value) {
        if (!isIOS()) {
            throw new UnsupportedOperationException("setIOSSliderValue is only supported on iOS.");
        }

        visibleElement(slider).sendKeys(value);
    }

    public String getIOSSliderValue(By locator) {
        return getIOSSliderValue(visibleElement(locator));
    }

    public String getIOSSliderValue(WebElement slider) {
        if (!isIOS()) {
            throw new UnsupportedOperationException("getIOSSliderValue is only supported on iOS.");
        }

        return visibleElement(slider).getAttribute("value");
    }

    // -------------------- DEVICE KEYS --------------------

    public void pressBack() {
        driver.navigate().back();
    }

    public void pressHome() {
        if (isAndroid()) {
            ((AndroidDriver) driver).pressKey(
                    new KeyEvent(AndroidKey.HOME)
            );
        } else if (isIOS()) {
            ((JavascriptExecutor) driver).executeScript(
                    "mobile: pressButton",
                    Map.of("name", "home")
            );
        }
    }

    public void pressEnter() {
        if (isAndroid()) {
            ((AndroidDriver) driver).pressKey(
                    new KeyEvent(AndroidKey.ENTER)
            );
        } else if (isIOS()) {
            ((JavascriptExecutor) driver).executeScript(
                    "mobile: performEditorAction",
                    Map.of("action", "done")
            );
        }
    }

    // -------------------- PLATFORM --------------------

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