package com.ngocanh.driver;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static final ThreadLocal<WebDriver> tdriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return tdriver.get();
    }

    public static void setDriver(WebDriver driver) {
        tdriver.set(driver);
    }

    public static void quit() {
        DriverManager.getDriver().quit();
        tdriver.remove();
    }
}
