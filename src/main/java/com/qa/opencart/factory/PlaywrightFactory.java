package com.qa.opencart.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/*
Utility class that handles browser initialization, login state management, and common utilities like screenshots.
 */

public class PlaywrightFactory {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private static Page page;

    // Initialize the Playwright browser and load the login state if available.
    public Page initBrowser(Properties prop) {
        String browserName = prop.getProperty("browser").trim();
        String scoreUrl = prop.getProperty("scoreUrl").trim();
        Path storageStatePath = Paths.get("applogin.json");

        // Create a Playwright instance and set up the browser
        playwright = Playwright.create();
        setupBrowser(browserName);

        // Log in once and save authentication state for reuse in all tests (bypass repetitive log-ins)
        browserContext = (Files.exists(storageStatePath)) ? loadExistingState(storageStatePath) : createNewContext(prop, storageStatePath);

        // Navigate to the URL only if no new page was opened (i.e., using an existing login state)
        if (page == null) {
            page = browserContext.newPage();  // Only create a new page if it doesn't exist
            page.navigate(scoreUrl);
        }

        return page;
    }

    // Load existing login state from a file if it exists.
    private BrowserContext loadExistingState(Path storageStatePath) {
        System.out.println("Loaded saved login state from " + storageStatePath);
        return browser.newContext(new Browser.NewContextOptions().setStorageStatePath(storageStatePath));
    }

    // Create a new browser context, log in, and save the session state.
    private BrowserContext createNewContext(Properties prop, Path storageStatePath) {
        BrowserContext context = browser.newContext(); // Create new browser profile
        page = context.newPage(); // Open a new page
        page.navigate(prop.getProperty("scoreUrl").trim()); // Navigate to the URL

        // Perform login action
        loginToPage("admin@srh.care", "rareunicorn");

        // Save login state after successful login
        context.storageState(new BrowserContext.StorageStateOptions().setPath(storageStatePath));
        System.out.println("Login state saved to: " + storageStatePath.toAbsolutePath());
        return context;
    }

    // Set up the browser instance based on the given browser name.
    private void setupBrowser(String browserName) {
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(false);

        switch (browserName.toLowerCase()) {
            case "chromium":
                browser = playwright.chromium().launch(options);
                break;
            case "firefox":
                browser = playwright.firefox().launch(options);
                break;
            case "chrome":
                browser = playwright.chromium().launch(options.setChannel("chrome"));
                break;
            case "edge":
                browser = playwright.chromium().launch(options.setChannel("msedge"));
                break;
            default:
                throw new IllegalArgumentException("Invalid browser name: " + browserName);
        }
    }

    // Perform login on the application using the provided credentials.
    private void loginToPage(String email, String password) {
        page.fill("#email", email);
        page.fill("#password", password);
        page.click("button:has-text('Sign In')");

        // Wait for an element that only appears after successful login
        page.waitForSelector("//div[text()='Active Surveys']");
        System.out.println("Successfully logged in.");
    }

    // Initialize the properties from the config file.
    public Properties init_prop() {
        Properties prop = new Properties();
        try (FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties")) {
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    // Take a screenshot and return its Base64-encoded string
    public static String takeScreenshot() {
        String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
        byte[] buffer = page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
        return Base64.getEncoder().encodeToString(buffer);
    }
}


