package testRunners;
import io.appium.java_client.AppiumDriver;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features ={"src/test/java/features"},
        glue = {"stepDefinitions","utils"},
        tags = "@register",
        plugin = {
                "summary","pretty","html:Reports/CucumberReport/Reports.html"
        }

)

public class TestRunner extends AbstractTestNGCucumberTests {
    static AppiumDriver driver = utils.DriverFactory.getDriver();

}