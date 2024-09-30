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
BaseTest is a base class that defines the setup and teardown methods for test execution. It ensures that the browser is launched with the correct configuration before each test, and it takes care of browser shutdown after tests are executed. Other test classes that extend BaseTest inherit this setup and teardown functionality.
The Page Object Model (POM) pattern is a design pattern where each web page is represented by a class. This class contains all the methods that interact with elements on that page.

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
	@Parameters({ "browser" })
	@BeforeTest
	public void setup(@Optional("chrome") String browserName) {
		//The PlaywrightFactory is responsible for initializing Playwright, browsers, and properties.
		pf = new PlaywrightFactory();
		//reads the config.properties file (e.g., browser type, URL).
		prop = pf.init_prop();

		//If a browserName is passed via TestNG's @Parameters, it overrides the value in the properties file.
		// This allows dynamic selection of the browser at runtime, useful for cross-browser testing.
		if (browserName != null) {
			prop.setProperty("browser", browserName);
		}
		//Initialize the browser and load the page:
		//initBrowser() method of PlaywrightFactory is called, passing in the Properties object.
		// This initializes the Playwright browser based on the provided or default browser type.
		page = pf.initBrowser(prop);
		//A HomePage object is instantiated, passing the Page object to represent the homepage of the application.
		//This enables the test to perform actions specific to the homepage of the application (e.g., clicking elements, verifying content).
		homePage = new HomePage(page);

	}

	@AfterTest
	public void tearDown() {
		page.context().browser().close();
	}

}
