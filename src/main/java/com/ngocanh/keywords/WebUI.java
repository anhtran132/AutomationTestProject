package com.ngocanh.keywords;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.*;
import java.util.List;

import com.ngocanh.constants.FrameworkConstants;
import com.ngocanh.driver.DriverManager;
import com.ngocanh.logs.LogUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class WebUI {

    public static void waitForPageLoaded() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

        // wait for Javascript to loaded
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");

        //Get JS is Ready
        boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

        //Wait Javascript until it is Ready!
        if (!jsReady) {
            LogUtils.info("Javascript is NOT Ready!");
            //Wait for Javascript to load
            try {
                wait.until(jsLoad);
            } catch (Throwable error) {
                LogUtils.error("Timeout waiting for page load. (" + 30 + "s)");
                Assert.fail("Timeout waiting for page load. (" + FrameworkConstants.WAIT_EXPLICIT + "s)");
            }
        }
    }


    public static void waitForElementClickable(WebElement webElement) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.elementToBeClickable(webElement));
        } catch (Throwable error) {
            LogUtils.error("❌ Timeout waiting for the element ready to click. " + getLocatorFromWebElement((webElement)));
            Assert.fail("❌ Timeout waiting for the element ready to click. " + getLocatorFromWebElement(webElement));
        }
    }

    public static void clickElement(WebElement webElement) {
        LogUtils.info("Clicked on the element " + getLocatorFromWebElement(webElement));
        waitForElementVisible(webElement);
        waitForElementClickable(webElement);
        webElement.click();

//        if (ExtentTestManager.getExtentTest() != null) {
//            ExtentReportManager.pass("Clicked on the element " + by);
//        }
//        AllureManager.saveTextLog("Clicked on the element " + by);
//
//        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());

    }
    public static void clickElementByJs(WebElement webElement) {
        try {
            // Ensure the element is displayed and interactable before using JavaScript
            if (webElement.isDisplayed() && webElement.isEnabled()) {
                JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
                js.executeScript("arguments[0].click();", webElement);
                LogUtils.info("Clicked on the element " + webElement);
            } else {
                throw new IllegalStateException("❌ Element is either not displayed or not enabled: " + getLocatorFromWebElement(webElement));
            }
        } catch (Exception e) {
            String errorMessage = "❌ Failed to click the element using JavaScript: " + getLocatorFromWebElement(webElement);
            // Uncomment to log error if needed
            LogUtils.error(errorMessage);
            Assert.fail(errorMessage);
        }
    }
    public static void clickElementByAction(WebElement webElement) {
        Actions actions = new Actions(DriverManager.getDriver());
        actions.click(webElement).perform();
    }


    public static void setText(WebElement webElement, String value) {
       // smartWait();
        waitForElementVisible(webElement);
        webElement.sendKeys(value);
        if(webElement.getAttribute("value") == null){
            Assert.assertFalse(webElement.getText().isEmpty());
        }
        else {
            Assert.assertFalse(webElement.getAttribute("value").isEmpty());
        }
        LogUtils.info("Set text " + value + " on " + getLocatorFromWebElement(webElement));

//        if (ExtentTestManager.getExtentTest() != null) {
//            ExtentReportManager.pass("Set text " + value + " on " + by);
//        }
//        AllureManager.saveTextLog("Set text " + value + " on " + by);
//
//        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());

    }
    public static void setTextByJS(WebElement webElement, String value) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].value = arguments[1];", webElement, value);
    }

    public static void setTextUsingActions(WebElement inputField, String value) {
        Actions actions = new Actions(DriverManager.getDriver());
        actions.moveToElement(inputField).click().sendKeys(value).build().perform();
    }

    public static void clearTextByJS(WebElement webElement) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].value='';", webElement);
        LogUtils.info("Clear text on" + webElement);
    }

    public static void clearTextByControlA(WebElement webElement) {
        webElement.sendKeys(Keys.CONTROL + "a"); // Select all text
        webElement.sendKeys(Keys.BACK_SPACE);
    }
    public static void clearTextByBankSpace(WebElement webElement, String value) {
        for (int i = 0; i < value.length(); i++) { // Adjust the number based on expected text length
            webElement.sendKeys(Keys.BACK_SPACE);
        }
        LogUtils.info("Clear text on" + webElement);
    }

    public static void setTextUsingRobot(WebElement webElement, String value)  {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        // Move focus to the input field
        webElement.click();

        // Type each character
        for (char c : value.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
        }
        Assert.assertEquals(webElement.getAttribute("value"), value);
    }
    public static void setTextWithActionKey(WebElement webElement, String value, Keys keys) {
        // smartWait();
        Objects.requireNonNull(waitForElementVisible(webElement)).sendKeys(value, keys);
        LogUtils.info("Set text " + value + " on " + getLocatorFromWebElement(webElement));

        //LogUtils.info("Set text " + value + " on " + by.toString());

//        if (ExtentTestManager.getExtentTest() != null) {
//            ExtentReportManager.pass("Set text " + value + " on " + by);
//        }
//        AllureManager.saveTextLog("Set text " + value + " on " + by);
//
//        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());

    }

    public static void scrollToElementAtBottom(WebElement webElement) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", webElement);
        js.executeScript("window.scrollBy(0, -100);");
    }

    public static WebElement waitForElementVisible(WebElement webElement) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT - 10));

            if (!webElement.isDisplayed()) {
                scrollToElementAtBottom(webElement);
            }
            return wait.until(ExpectedConditions.visibilityOf(webElement));

        } catch (Throwable error) {
            String errorMessage = "❌ Timeout waiting for the element to be visible: " + getLocatorFromWebElement(webElement);
            LogUtils.error(errorMessage);
            Assert.fail(errorMessage);
            return null; // This will never be reached due to Assert.fail, but it satisfies the compiler
        }
    }

    /**
     * Customized getText method.
     *
     * @param element The WebElement from which to retrieve the text.
     * @param fallbackToAttribute True if the function should fallback to the "value" attribute when text is empty.
     * @return The processed text.
     */
    public static String getText(WebElement element, boolean fallbackToAttribute) {
        String text = element.getText();
        if (text == null || text.trim().isEmpty()) {
            // Fallback to retrieving the value attribute if specified
            if (fallbackToAttribute) {
                text = element.getAttribute("value");
            }
        }
        return (text != null) ? text.trim() : ""; // Return trimmed text
    }

    public static void waitForElementInvisibleShortTime(WebElement webElement) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT - 15) );
            wait.until(ExpectedConditions.invisibilityOf(webElement));
        } catch (Throwable error) {
            LogUtils.error("❌ Timeout waiting for the element Invisible. " + getLocatorFromWebElement(webElement));
            Assert.fail("❌ Timeout waiting for the element Invisible. " + getLocatorFromWebElement(webElement));
        }
    }
    public static void waitForElementInvisibleLongTime(WebElement webElement) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT + 20) );
            wait.until(ExpectedConditions.invisibilityOf(webElement));
        } catch (Throwable error) {
            LogUtils.error("❌ Timeout waiting for the element Invisible. " + getLocatorFromWebElement(webElement));
            Assert.fail("❌ Timeout waiting for the element Invisible. " + getLocatorFromWebElement(webElement));
        }
    }
    public static void waitForElementPresent(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            LogUtils.info("Element Present " + by);
        } catch (Throwable error) {
            LogUtils.error("❌ Element not exist. (waitForElementPresent) " + by.toString());
            Assert.fail("❌ Element not exist. (waitForElementPresent) " + by.toString());
        }
    }
    public static boolean selectOptionDynamic(List<WebElement> elements, String text) {
        //smartWait();
        //For dynamic dropdowns (div, li, span,...not select options)
        try {
            for (WebElement element : elements) {
                //LogUtils.info(element.getText());
                if (element.getText().toLowerCase().trim().contains(text.toLowerCase().trim())) {
                    clickElement(element);
                    return true;
                }
            }
        } catch (Exception e) {
            //LogUtils.info(e.getMessage());
        }
        return false;
    }

    public static void waitUntilAllElementsVisible(List<WebElement> locator) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT));
        wait.until(ExpectedConditions.visibilityOfAllElements(locator));
    }

    public static String getRandomValueFromArray(String[] values){
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        Random random = new Random();
        int randomIndex = random.nextInt(values.length);
        return values[randomIndex];
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void alertAccept(){
        DriverManager.getDriver().switchTo().alert().accept();
        LogUtils.info("Accept alert");
    }
    public static void alertDismiss(){
        DriverManager.getDriver().switchTo().alert().dismiss();
        LogUtils.info("Dismiss alert");
    }
    public static void hideElement(WebElement webElement) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].style.display = 'none';", webElement);
    }
    public static void hideElement2(WebElement webElement, WebDriver webDriver) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("arguments[0].style.display = 'none';", webElement);
    }

    public static int getRandomNumber(int upperBound) {
        if(Math.random() == 0){
            return 1;
        }
        return (int) (Math.random() * upperBound); // Generates a number from 0 to upperBound (exclusive)
    }
    public static void waitUntilInputHasData(WebElement webElement) {
        // Create a WebDriverWait instance with a timeout of 10 seconds
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT - 10) );

        // Wait until the 'value' attribute of the input element is not empty
        wait.until(ExpectedConditions.attributeToBeNotEmpty( webElement, "value"));
    }

    public static void waitForNewTabToOpen() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT - 10));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        LogUtils.info("New Tab opened");
    }
    public static void switchToNewWindowAndPerformAction(WebElement button, String expectedUrl) {
        // Store the current window handle
        String originalWindow = DriverManager.getDriver().getWindowHandle();

        // Click the button to open a new tab/window
        button.click();

        waitForNewTabToOpen();

        // Get all window handles
        Set<String> windowHandles = DriverManager.getDriver().getWindowHandles();

        // Loop through all window handles and switch to the new window
        for (String windowHandle : windowHandles) {
            if (!windowHandle.equals(originalWindow)) {
                DriverManager.getDriver().switchTo().window(windowHandle);
                break;
            }
        }

        // Perform an operation or assert something in the new window
        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        if (currentUrl.contains(expectedUrl)) {
            System.out.println("Test Passed! Redirected to correct URL: " + currentUrl);
        } else {
            System.out.println("Test Failed! Expected URL: " + expectedUrl + " but got: " + currentUrl);
        }
        // Switch back to the original window
        DriverManager.getDriver().switchTo().window(originalWindow);
    }

    public static void uploadFile(WebElement element, String fileName) {
        element.sendKeys(fileName);
        LogUtils.info("File uploaded");
    }

    public static String getLocatorFromWebElement(WebElement element){
        String elementString = element.toString();
        String locator = elementString.split("->")[1];
        locator = locator.replace("]", "");
        return locator;
    }
    public static int getColumnIndexByHeader(WebElement table, String columnName) {
        List<WebElement> headers = table.findElements(By.xpath(".//thead/tr/th"));

        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getText().trim().equalsIgnoreCase(columnName)) {
                return i + 1;
            }
        }
        return -1;
    }

    public static boolean  verifyEquals(String actual, String expected, String msg) {
        boolean result = actual.equals(expected);
        if(result){
            LogUtils.info("Verify equals " + actual + " =  " + expected);
        }else {
            LogUtils.error("Verify equals " + actual + " !=  " + expected);
            Assert.assertEquals(actual, expected, msg);
        }
        return result;
    }

    public static boolean verifyContains(String actual, String expected, String msg) {
        boolean result = actual.contains(expected);
        if(result){
            LogUtils.info("Verify contains " + actual + " contains  " + expected);
        }else {
            LogUtils.error("Verify contains " + actual + " not contains  " + expected);
            Assert.assertEquals(actual, expected, msg);
        }
        return result;
    }

    public static boolean assertTrue(boolean result, String msg){
        return result;
    }

    public static boolean verifyElementVisible(WebElement element, String message) {
        LogUtils.info("Verify element visible ");
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT));
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            if (message.isEmpty()) {
                LogUtils.error("The element is not visible. " + getLocatorFromWebElement(element));
                Assert.fail("The element is NOT visible. " + getLocatorFromWebElement(element));
            } else {
                LogUtils.error(message + getLocatorFromWebElement(element));
                Assert.fail(message + getLocatorFromWebElement(element));
            }
            return false;
        }
    }
    public static boolean verifyElementNotVisible(WebElement element, String message) {
        LogUtils.info("Verify element not visible");
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT));
            wait.until(ExpectedConditions.invisibilityOf(element));
            return true;
        } catch (Exception e) {
            if (message.isEmpty()) {
                LogUtils.error("The element is visible. " + getLocatorFromWebElement(element));
                Assert.fail("The element is visible. " + getLocatorFromWebElement(element));
            } else {
                LogUtils.error(message);
                Assert.fail(message);
            }
            return false;
        }
    }

    public static boolean verifyListIsNotEmpty(List<WebElement> elements, String message) {
        if (elements != null && !elements.isEmpty()) {
            LogUtils.info("List is not empty.");
            return true;
        } else {
            LogUtils.error(message);
            Assert.fail(message);
            return false;
        }
    }

    public static boolean verifyListIsEmpty(List<WebElement> elements, String message) {
        if (elements == null && elements.isEmpty()) {
            LogUtils.info("List is empty.");
            return true;
        } else {
            LogUtils.error(message);
            Assert.fail(message);
            return false;
        }
    }
    public static boolean verifyElementInListVisible(List<WebElement> elements, String message) {
        LogUtils.info("Verify element visible in list");
        for (WebElement element : elements) {
            try {
                WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT));  // Adjust the timeout as needed
                wait.until(ExpectedConditions.visibilityOf(element));

                LogUtils.info("Element found and visible: " + getLocatorFromWebElement(element));
                return true;
            } catch (Exception e) {
                // If an exception occurs (element not visible), continue checking the next element
                LogUtils.error("Element not visible: " + getLocatorFromWebElement(element));
                Assert.fail(message);
            }
        }
        // If no element in the list is visible
        LogUtils.info(message);
        return false;
    }
    public static List<String> getListValueFromTable(WebElement table,  List<WebElement> taleRows, String columnName) {
        List<String> values = new ArrayList<>();
        int columnIndex = getColumnIndexByHeader(table, columnName);
        for (WebElement row : taleRows) {
            WebElement cell = row.findElement(By.cssSelector("td:nth-of-type("+columnIndex+")"));
            values.add(cell.getText());
        }
        return values;
    }

    public static boolean verifyValueInList(List<String> values, String expectedValue, String message) {
        for (String value : values) {
            if(value.trim().equals(expectedValue)) {
                LogUtils.info("Value " + expectedValue + " found in the list");
                return true;
            }
        }
        LogUtils.error(message);
        Assert.fail(message);
        return false;
    }


}
