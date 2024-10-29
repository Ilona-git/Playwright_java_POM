package com.qa.opencart.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class report {


    ExtentReports extent;
    ExtentTest test;

    @BeforeSuite
    public void setup() {
        // Initialize ExtentReports
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @Test
    public void exampleTest() {
        test = extent.createTest("exampleTest", "Sample test for ExtentReports integration");

        // Your Playwright test code here
        test.pass("Test passed successfully.");
    }

    @AfterSuite
    public void tearDown() {
        // Flush ExtentReports
        extent.flush();
    }
}


