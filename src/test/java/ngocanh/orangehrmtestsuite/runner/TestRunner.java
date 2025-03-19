package ngocanh.orangehrmtestsuite.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/Test.feature",
        glue = {"ngocanh.orangehrmtestsuite.stepdefinitions",
                "ngocanh.orangehrmtestsuite.common"},
        plugin = {"pretty", "html:target/cucumber-reports/cucumber-report.html", "json:target/cucumber-reports/cucumber-json-report.json"},
        tags = "@valid"
)

public class TestRunner extends AbstractTestNGCucumberTests {

}
