package com.qa.opencart.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.nio.file.Paths;

public class loginRecording_Traces {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setViewportSize(800, 600));

            // Start tracing before creating / navigating a page.
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));


            Page page = context.newPage();
            page.navigate("https://naveenautomationlabs.com/opencart/");

            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Tablets")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Software").setExact(true)).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Phones & PDAs").setExact(true)).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Components").setExact(true)).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Show All Components")).click();
            page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Components")).click();

            page.locator("#content div").first().click();
            page.locator("#content div").first().click();

            assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("naveenopencart"))).isVisible();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(" 0 item(s) - $")).click();

            assertThat(page.locator("#cart")).containsText("Your shopping cart is empty!");
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Mice and Trackballs (0)").setExact(true)).click();


            // Stop tracing and export it into a zip archive.
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("trace.zip")));


            browser.close();

        }
    }
}