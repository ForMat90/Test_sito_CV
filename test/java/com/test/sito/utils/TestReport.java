package com.test.sito.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;

public class TestReport {

    private ExtentReports extentReports;
    private ExtentTest extentTest;

    public void setupReport() {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("test-report.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
        extentReports.setSystemInfo("Host Name", "cv-matteo.onrender.com");
    }

    public void startTest(String testName, String description) {
        extentTest = extentReports.createTest(testName, description);
    }

    public void pass(String message) {
        extentTest.log(Status.PASS, message);
    }

    public void fail(String messageFail) {
        try {
            extentTest.fail(messageFail, MediaEntityBuilder.createScreenCaptureFromBase64String(((TakesScreenshot) DriverUtils.getDriver()).getScreenshotAs(OutputType.BASE64)).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tearDown() {
        extentReports.flush();
    }
}
