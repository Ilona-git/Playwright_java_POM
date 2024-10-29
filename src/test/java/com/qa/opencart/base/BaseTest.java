package com.qa.opencart.base;

import java.util.Properties;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.microsoft.playwright.Page;
import com.qa.opencart.factory.PlaywrightFactory;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;

/*
 Setup Process (setup()):
Loads configuration values from the config.properties file using PlaywrightFactory.
Optionally overrides the browser property if a browser is specified via TestNG parameters.
Initializes the Playwright browser based on the configuration.
Sets up page objects (HomePage, LoginPage) for interacting with the application.
 */
public class BaseTest {

    protected PlaywrightFactory pf;
    protected Page page;
    protected Properties prop;
    protected HomePage homePage;
    protected LoginPage loginPage;

    //The @Parameters annotation is from TestNG and is used to pass parameters from the test suite (like the browser name) to the setup() method.
    @Parameters({"browser"})
    @BeforeTest
    public void setup(@Optional("chrome") String browserName) {
        //The PlaywrightFactory is responsible for initializing Playwright, browsers, and properties.
        pf = new PlaywrightFactory();
        //reads the config.properties file (e.g., browser type, URL).
        prop = pf.init_prop();

        if (browserName != null) {
            prop.setProperty("browser", browserName);
        }

        //Initialize the browser and load the page:
        page = pf.initBrowser(prop);

        //A HomePage object is instantiated, passing the Page object to represent the homepage of the application.
        homePage = new HomePage(page);

    }

    @AfterTest
    public void tearDown() {
        page.context().browser().close();
    }

}
