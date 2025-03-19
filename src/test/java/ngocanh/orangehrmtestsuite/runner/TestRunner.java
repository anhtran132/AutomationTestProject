package ngocanh.orangehrmtestsuite.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/Test.feature",
        glue = {"ngocanh.orangehrmtestsuite.stepdefinitions",
        "ngocanh.orangehrmtestsuite.common"},
        tags = "@regression",
        plugin = {"pretty", "html:target/cucumber-report.html"}
)
public class TestRunner extends AbstractTestNGCucumberTests {

}
