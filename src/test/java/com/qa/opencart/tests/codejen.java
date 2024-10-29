package com.qa.opencart.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.MouseButton;
import com.qa.opencart.base.BaseTest;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class codejen extends BaseTest {

    @Test
    public void test() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://proxy.happygrass-a98828c8.eastus2.azurecontainerapps.io/manager/sign-in");

            page.getByPlaceholder("Email").click();
            page.getByPlaceholder("Email").fill("admin@srh.care");
            page.getByPlaceholder("Password").click();
            page.getByPlaceholder("Password").fill("rareunicorn");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign In")).click();

            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("add_box Create Survey")).click();
            page.locator("[data-test-id=\"survey-form-system\"]").click();
            page.locator("[data-test-id=\"survey-form-system\"]").fill("system");
            page.getByText("FACILITY", new Page.GetByTextOptions().setExact(true)).click();
            page.locator("[data-test-id=\"survey-form-facility\"]").click();
            page.locator("[data-test-id=\"survey-form-facility\"]").fill("facility");
            page.locator("app-input").filter(new Locator.FilterOptions().setHasText("FACILITY")).click();
            page.locator("[data-test-id=\"survey-form-key\"]").click();
            page.locator("[data-test-id=\"survey-form-key\"]").fill("344f");

            page.locator("[data-test-id=\"survey-form-type\"]").getByRole(AriaRole.COMBOBOX).click();
            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("SCORE").setExact(true)).click();

            page.locator("[data-test-id=\"survey-form-template\"]").getByRole(AriaRole.COMBOBOX).click();
            page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("2022 AdventHealth SCOR").setExact(true)).click();

            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save")).click();
            page.getByText("Survey created").click(new Locator.ClickOptions()
                    .setButton(MouseButton.RIGHT));

            page.locator("[data-test-id=\"active-surveys-table-row-0\"]").getByText("system - facility").dblclick();
            page.locator("[data-test-id=\"active-surveys-table-row-0\"]").getByText("system - facility").click(new Locator.ClickOptions()
                    .setButton(MouseButton.RIGHT));
            assertThat(page.locator("[data-test-id=\"active-surveys-table-row-0\"]")).containsText("system - facility");
            page.getByText("Active Surveys").click();
            assertThat(page.getByText("Active Surveys")).isVisible();
            assertThat(page.locator("[data-test-id=\"filter-bar-searchInput\"]")).isEmpty();

            page.locator("[data-test-id=\"filter-bar-searchInput\"]").click();
            page.locator("[data-test-id=\"filter-bar-searchInput\"]").fill("test");
            assertThat(page.locator("[data-test-id=\"filter-bar-searchButton\"]").getByRole(AriaRole.BUTTON)).containsText("search");
        }
    }
}
