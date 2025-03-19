package ngocanh.orangehrmtestsuite.stepdefinitions;

import com.ngocanh.constants.FrameworkConstants;
import com.ngocanh.driver.DriverManager;
import com.ngocanh.logs.LogUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import ngocanh.orangehrmtestsuite.common.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class MyStepdefs  {

    
    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        System.out.println("I am on the login page");
    }


    @When("I enter the username {string} and password {string}")
    public void iEnterTheUsernameAndPassword(String arg0, String arg1) {
        WebElement username = DriverManager.getDriver()
                .findElement(By.xpath("//input[@name = 'username']"));
        username.sendKeys(arg0);
        WebElement password = DriverManager.getDriver()
                .findElement(By.xpath("//input[@name = 'password']"));
        password.sendKeys(arg1);
        System.out.println("I enter the username " + arg0 +" and password " + arg1);
       // Assert.fail("test failed");
    }

    @And("I click the login button")
    public void iClickTheLoginButton() {
        WebElement loginBtn = DriverManager.getDriver()
                .findElement(By.xpath("//button"));
        loginBtn.click();
        System.out.println("I click the login button");
       // Assert.fail("test failed");
    }

    @Then("I should see an error message {string}")
    public void iShouldSeeAnErrorMessage(String arg0) {
        System.out.println("I should see an error message " + arg0);
    }

    @Then("I should be redirected to the dashboard page")
    public void iShouldBeRedirectedToTheDashboardPage() {
        System.out.println("I should be redirected to the dashboard page");
    }

    @And("I should see the welcome message {string}")
    public void iShouldSeeTheWelcomeMessage(String arg0) {
        System.out.println("I should see the welcome message " + arg0);
    }

    @When("I leave the username field empty and the password field empty")
    public void iLeaveTheUsernameFieldEmptyAndThePasswordFieldEmpty() {
        System.out.println("I leave the username field empty and the password field empty");

    }

    @Then("I should see an error message {string} and {string}")
    public void iShouldSeeAnErrorMessageAnd(String arg0, String arg1) {
        System.out.println("I should see an error message " + arg0 +" and " + arg1);
    }

    @Then("I should see the error message {string}")
    public void iShouldSeeTheErrorMessage(String arg0) {
        System.out.println("I should see the error message " + arg0);
    }
}
