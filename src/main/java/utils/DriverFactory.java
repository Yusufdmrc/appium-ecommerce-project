package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.ConfigReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DriverFactory {
    static AppiumDriver driver;
    static Properties properties;
    static DesiredCapabilities capabilities;

    public static AppiumDriver initialize_Driver(String platform){
        properties = ConfigReader.getProperties();
        capabilities = new DesiredCapabilities();
        if(platform.equals("Android")){
            capabilities.setCapability("platformName","Android");
            capabilities.setCapability("udid","emulator-5554");
            capabilities.setCapability("appPackage","");
            capabilities.setCapability("appActivity","");
            capabilities.setCapability("automationName", "UiAutomator2");
        } else if (platform.equals("IOS")) {
            capabilities.setCapability("platformName","IOS");
            capabilities.setCapability("bundleId","com.Imen.ecommerceApp");
            capabilities.setCapability("udid","E3280F00-85DE-4AA0-9586-B11914CFBFD5");
            capabilities.setCapability("automationName", "XCUITest");
        }
        try {
            driver = new AppiumDriver(new URL("http://127.0.0.1:4723"),capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        int impWait =Integer.parseInt(properties.getProperty("implicityWait"));
        driver.manage().timeouts().implicitlyWait(impWait, TimeUnit.SECONDS);
        return getDriver();
    }
    public static AppiumDriver getDriver(){
        return driver;
    }

}