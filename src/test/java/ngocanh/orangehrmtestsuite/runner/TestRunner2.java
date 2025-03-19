package ngocanh.orangehrmtestsuite.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features/Login.feature",
        glue = {"ngocanh.orangehrmtestsuite.stepdefinitions",
                "ngocanh.orangehrmtestsuite.common"},
        plugin = {"pretty", "html:target/cucumber-reports/cucumber-report.html",
                "json:target/cucumber-reports/cucumber-json-report.json"}
)

public class TestRunner2 extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

}
