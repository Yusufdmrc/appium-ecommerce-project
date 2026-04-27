package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.RegisterPage;
import utils.DriverFactory;

public class RegisterSteps {
    private RegisterPage registerPage;

    @When("User navigates to the register screen")
    public void userNavigatesToTheRegisterScreen() {
        registerPage = new RegisterPage(DriverFactory.getDriver());
        registerPage.navigateToRegisterScreen();
    }

    @And("User enters first name {string}")
    public void userEntersFirstName(String firstName) {
        registerPage.enterFirstName(firstName);
    }

    @And("User enters last name {string}")
    public void userEntersLastName(String lastName) {
        registerPage.enterLastName(lastName);
    }

    @And("User enters registration email {string}")
    public void userEntersRegistrationEmail(String email) {
        registerPage.enterEmail(email);
    }

    @And("User enters registration password {string}")
    public void userEntersRegistrationPassword(String password) {
        registerPage.enterPassword(password);
    }

    @And("User clicks register button")
    public void userClicksRegisterButton() {
        registerPage.clickRegisterButton();
    }

    @Then("User account should be created successfully")
    public void userAccountShouldBeCreatedSuccessfully() {
        Assert.assertTrue(registerPage.isRegistrationSuccessful(), "Registration was not successful. Home screen element not found.");
    }
}

