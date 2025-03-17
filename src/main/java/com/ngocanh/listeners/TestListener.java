package com.ngocanh.listeners;
import com.ngocanh.logs.LogUtils;
import org.testng.*;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext contextStart) {
        LogUtils.info("SETUP TESTING ENVIRONMENT " + contextStart.getStartDate());
    }

    @Override
    public void onFinish(ITestContext contextFinish) {
        LogUtils.info("TEST ENDING " + contextFinish.getStartDate());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Testcase failed with certain success percentage " + result.getName());

    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogUtils.info("TESTCASE " + result.getName() + " IS FAILED");

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogUtils.info("TESTCASE " + result.getName() + " iS SKIPPED");

    }

    @Override
    public void onTestStart(ITestResult result) {
        LogUtils.info("RUNNING TESTCASE : " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtils.info("TESTCASE : " + result.getName() + " iS PASSED");

    }

//    @Override
//    public List<IMethodInstance> intercept(List<IMethodInstance> methodsInstance, ITestContext iTestContext) {
//        List<IMethodInstance> result = new ArrayList<IMethodInstance>();
//        for (IMethodInstance method : methodsInstance) {
//            Test testMethod = method.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class);
//            if (testMethod.priority() == 5) {
//                result.add(method);
//            }
//        }
//        return result;
//    }
}