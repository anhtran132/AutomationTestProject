package com.ngocanh.listeners;

import com.aventstack.extentreports.Status;
import com.ngocanh.driver.DriverManager;
import com.ngocanh.logs.LogUtils;
import com.ngocanh.reports.extentreports.ExtentTestManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static com.ngocanh.reports.extentreports.ExtentManager.getExtentReports;

public class ReportListener implements ITestListener{

        public String getTestName(ITestResult result) {
            return result.getTestName() != null ? result.getTestName()
                    : result.getMethod().getConstructorOrMethod().getName();
        }

        public String getTestDescription(ITestResult result) {
            return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
        }

        @Override
        public void onStart(ITestContext iTestContext) {
            LogUtils.info("Start testing ");
//            //Gọi hàm startRecord video trong CaptureHelpers class
//            try {
//                CaptureHelpers.startRecord(iTestContext.getName());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public void onFinish(ITestContext iTestContext) {
            LogUtils.info("End testing ");
            //Kết thúc và thực thi Extents Report
            getExtentReports().flush();
//            //Gọi hàm stopRecord video trong CaptureHelpers class
//            CaptureHelpers.stopRecord();
        }

        @Override
        public void onTestStart(ITestResult iTestResult) {
            LogUtils.info(getTestName(iTestResult) + " test is starting...");
            ExtentTestManager.saveToReport(iTestResult.getName(), iTestResult.getTestName());
        }

        @Override
        public void onTestSuccess(ITestResult iTestResult) {
            LogUtils.info(getTestName(iTestResult) + " test is passed.");
            //ExtentReports LogUtils operation for passed tests.
            ExtentTestManager.logMessage(Status.PASS, getTestDescription(iTestResult));
        }

        @Override
        public void onTestFailure(ITestResult iTestResult) {
            LogUtils.error(getTestName(iTestResult) + " test is failed.");

            ExtentTestManager.addScreenShot(Status.FAIL, getTestName(iTestResult));

            ExtentTestManager.logMessage(Status.FAIL, iTestResult.getThrowable().toString());
            ExtentTestManager.logMessage(Status.FAIL, iTestResult.getName() + " is failed.");
        }

        @Override
        public void onTestSkipped(ITestResult iTestResult) {
            LogUtils.warn(getTestName(iTestResult) + " test is skipped.");
            ExtentTestManager.logMessage(Status.SKIP, getTestName(iTestResult) + " test is skipped.");
        }

        @Override
        public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
            LogUtils.error("Test failed but it is in defined success ratio " + getTestName(iTestResult));
            ExtentTestManager.logMessage("Test failed but it is in defined success ratio " + getTestName(iTestResult));
        }
}
