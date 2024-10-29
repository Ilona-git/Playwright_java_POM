package com.qa.opencart.base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.qa.opencart.factory.PlaywrightFactory;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ScorePage;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.nio.file.Paths;
import java.util.Properties;

/*
This class defines the setup and teardown logic for tests.

Setup Process (setup()):
Loads configuration values from the config.properties file using PlaywrightFactory.
Optionally overrides the browser property if a browser is specified via TestNG parameters.
Initializes the Playwright browser based on the configuration.
Sets up page objects (HomePage, LoginPage) for interacting with the application.

Browser contexts. Playwright creates a browser context for each test. Browser context is equivalent to a brand new browser profile.
 */
public class BaseTestScore {

    public PlaywrightFactory pf;
    public Page page;
    public Properties prop;
    public ScorePage scorePage;
    public Browser browser;
    public BrowserContext browserContext;


    //You pass the browser parameter to allow cross-browser testing. If the browser is not specified, it defaults to Chrome (@Optional("chrome")).
    @Parameters({"browser"})
    @BeforeTest
    public void setup(@Optional("chrome") String browserName) {
        //The PlaywrightFactory is responsible for initializing Playwright, browsers, and properties.
        pf = new PlaywrightFactory();
        //reads the config.properties file (e.g., browser type, URL).
        prop = pf.init_prop();

        //If a browserName is passed via TestNG's @Parameters, it overrides the value in the properties file.
        if (browserName != null) {
            prop.setProperty("browser", browserName);
        }
        //Initialize the browser and load the page:
        page = pf.initBrowser(prop);

        browserContext = page.context();

        scorePage = new ScorePage(page);

    }


    @AfterTest
    public void tearDown() {
        page.context().browser().close();
    }

}
