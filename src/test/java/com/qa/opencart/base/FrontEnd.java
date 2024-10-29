package com.qa.opencart.base;

import com.microsoft.playwright.*;
import com.qa.opencart.pages.ScorePage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.nio.file.Paths;
import java.util.Random;

import static org.testng.internal.Utils.log;


@Slf4j
public class FrontEnd {

    public Random randomNum = new Random();
    public String displayName;
    public Browser browser;
    public BrowserContext context;
    public Page page;
    public ScorePage scorePage;


    @BeforeEach
    public void setupTest(TestInfo testInfo) {
        displayName = testInfo.getDisplayName();

        // Initialize Playwright and launch the browser
        Playwright playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        context = browser.newContext();
        page = context.newPage();

        // Maximize the browser window
        page.setViewportSize(1920, 1080);

        // Navigate to URL
        String scoreUrl = "https://proxy.happygrass-a98828c8.eastus2.azurecontainerapps.io/manager/manager";
        page.navigate(scoreUrl);
        log("Navigated to URL: " + scoreUrl);

        // Wait for the login page and log in
        page.waitForSelector("#email");
        log("Email field is visible");

        // Log in to the page
        loginToPage("admin@srh.care", "rareunicorn");
        log("Successfully logged in.");


        // Save the login state to a file
        context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("applogin.json")));

        scorePage = new ScorePage(page);

    }

    public void loginToPage(String email, String password) {
        // Fill out the login form and submit
        page.fill("#email", email);
        page.fill("#password", password);
        page.click("button[type='submit']");

        // Wait for some element after login
        //page.waitForSelector("your-selector-after-login");
    }


    @AfterEach
    public void tearDown() {
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        log("Browser closed and test finished.");
    }
}
