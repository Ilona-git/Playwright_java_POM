package com.qa.opencart.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.MouseButton;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class loginRecording2 {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            page.navigate("https://demo.playwright.dev/todomvc/");
            page.navigate("https://demo.playwright.dev/todomvc/#/");
            page.getByPlaceholder("What needs to be done?").click();
            page.getByPlaceholder("What needs to be done?").click();
            page.pause();
            page.getByPlaceholder("What needs to be done?").click(new Locator.ClickOptions()
                    .setButton(MouseButton.RIGHT));
            page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("todos")).click();
            assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("todos"))).isVisible();
            assertThat(page.locator("body")).containsText("This is just a demo of TodoMVC for testing, not the real");
            assertThat(page.getByPlaceholder("What needs to be done?")).isEmpty();
        }
    }
}

