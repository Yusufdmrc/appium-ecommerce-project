package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.LoginPage;
import utils.DriverFactory;

public class LoginSteps {
    private LoginPage loginPage;

    @Given("User launches the application")
    public void userLaunchesTheApplication() {
        loginPage = new LoginPage(DriverFactory.getDriver());
    }

    @When("User enters email {string}")
    public void userEntersEmail(String email) {
        loginPage.enterEmail(email);
    }

    @And("User enters password {string}")
    public void userEntersPassword(String password) {
        loginPage.enterPassword(password);
    }

    @And("User clicks login button")
    public void userClicksLoginButton() {
        loginPage.clickLoginButton();
    }

    @Then("User should be logged in successfully")
    public void userShouldBeLoggedInSuccessfully() {
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login was not successful. Home screen element not found.");
    }
}

