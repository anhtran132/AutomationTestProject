package ngocanh.orangehrmtestsuite.common;

import com.ngocanh.constants.FrameworkConstants;
import com.ngocanh.driver.DriverManager;
import com.ngocanh.logs.LogUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    WebDriver driver;
    @Before()
    public void initializeBrowser(Scenario scenario){
        LogUtils.info("Initializing Browser");
        System.out.println(Thread.currentThread().getId() + ": Scenario: " + scenario.getName());

        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : "chrome";

        if(browserName.contains("chrome")){
            ChromeOptions options = new ChromeOptions();
            if(browserName.contains("headless")){
                options.addArguments("headless", "--window-size=1920x1080");
            }
            driver = new ChromeDriver(options);
        }
        else if(browserName.contains("firefox")){
            driver = new FirefoxDriver();
        }
        DriverManager.setDriver(driver);
        driver.get(FrameworkConstants.URL);
        LogUtils.info("Open login page " + FrameworkConstants.URL);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }
    @After()
    public void quitBrowser(Scenario scenario)  {
        if (scenario.isFailed()) {
            // Take a screenshot if the scenario failed
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            String screenshotName = scenario.getName().replaceAll(" ", "_") + ".png";
            File destFile = new File("target/screenshots/" + screenshotName);

            try {
                // Save screenshot to the target directory
                FileUtils.copyFile(srcFile, destFile);
                // Add screenshot to Cucumber report
                scenario.attach(FileUtils.readFileToByteArray(destFile), "image/png", screenshotName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        DriverManager.quit();
        LogUtils.info("Closing Browser");
    }
}
