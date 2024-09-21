package com.qa.opencart.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightFactory {

	//tl in variable names stands for ThreadLocal. This indicates that these variables are thread-local variables, meaning each thread has its own separate instance of these variables.
	//In multi-threaded environments (like parallel test executions), sharing global state between threads can lead to race conditions, where multiple threads modify the same resource simultaneously, causing unpredictable behavior.
	// By using ThreadLocal, you ensure that each thread has its own isolated instance of variables, avoiding interference between threads.
	Playwright playwright;
	Browser browser;
	BrowserContext browserContext;
	Page page;
	Properties prop;

	private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
	private static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
	private static ThreadLocal<Page> tlPage = new ThreadLocal<>();
	private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();

	public static Playwright getPlaywright() {
		return tlPlaywright.get();
	}

	public static Browser getBrowser() {
		return tlBrowser.get();
	}

	public static BrowserContext getBrowserContext() {
		return tlBrowserContext.get();
	}

	public static Page getPage() {
		return tlPage.get();
	}

	public Page initBrowser(Properties prop) {

		String browserName = prop.getProperty("browser").trim();
		System.out.println("browser name is : " + browserName);

		// playwright = Playwright.create();
		tlPlaywright.set(Playwright.create()); // This ensures each thread gets its own Playwright instance. avoiding conflicts in shared browser states.


		switch (browserName.toLowerCase()) {
//chromium useful for lightweight or simpler browser testing. It’s often used for automation purposes
		case "chromium":
			tlBrowser.set(getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
			break;
		case "firefox":
			tlBrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(false)));
			break;
		case "safari":
			tlBrowser.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(false)));
			break;
//Chrome is the full-featured browser from Google, used when you want to replicate real-world scenarios
		case "chrome":
			tlBrowser.set(
					getPlaywright().chromium().launch(new LaunchOptions().setChannel("chrome").setHeadless(false)));
			break;
		case "edge":
			tlBrowser.set(
					getPlaywright().chromium().launch(new LaunchOptions().setChannel("msedge").setHeadless(false)));
			break;	

		default:
			System.out.println("please pass the right browser name......");
			break;
		}
		//A new browser context is created, allowing isolated sessions with different states (cookies, storage).
		tlBrowserContext.set(getBrowser().newContext());
		//A new page is created from the context and assigned to a thread-local variable (tlPage).
		tlPage.set(getBrowserContext().newPage());
		getPage().navigate(prop.getProperty("url").trim());
		return getPage();

	}

	/**
	 * this method is used to initialize the properties from config file
	 * This Java method init_prop() is responsible for loading properties from a configuration file into a Properties object. The configuration file typically contains key-value pairs used for setting up environment variables, browser configurations, URLs, or other necessary information for testing.
	 */
	public Properties init_prop() {

		try {
			FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
			prop = new Properties();
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;

	}

	/**
	 * take screenshot
	 * 
	 */

	public static String takeScreenshot() {
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		//getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
		
		byte[] buffer = getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
		String base64Path = Base64.getEncoder().encodeToString(buffer);
		
		return base64Path;
	}

}
