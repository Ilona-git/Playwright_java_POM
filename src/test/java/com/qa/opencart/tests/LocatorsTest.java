package com.qa.opencart.tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.nio.file.Paths;
import java.util.*;

public class LocatorsTest extends BaseTest {

    @Test
    public void Locators() {

        page.navigate("https://naveenautomationlabs.com/opencart/");
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Tablets")).click();


        //TEXT locators
        Locator el = page.locator("text='Currency'");

        //get text
        String textContent = page.getByLabel("Password").textContent(); // Retrieves the raw text content of an element, any extra whitespace, including hidden elements, line breaks, and even invisible elements.

        List<String> textContents = page.locator("ul").allTextContents();
        for (String text : textContents) {
            System.out.println(text);
            // "Item 1"
            // "Item 2"
            // "Item 3"
        } //Returns an array of text contents from the element and its children, Useful when you need to capture and inspect text from multiple child elements separately.

        String innerText = page.getByLabel("Password").innerText();

        //get by text
        Locator button = page.getByText("'Submit'");

        //has-text
        page.locator("form input:has-text('Login')").click();


        //CSS Selectors:
        Locator el1 = page.locator(".class-name");
        Locator el2 = page.locator("#id-name");
        Locator el3 = page.locator("div > p");

        //xpath:
        Locator xpath = page.locator("//button[@id='login']");

        //ID Selector:
        Locator testId = page.getByTestId("common-home");

        //Placeholder Selector:
        Locator byPlaceholder = page.getByPlaceholder("Search");

        //Label Selector:
        Locator byLabel = page.getByLabel("Password");

        //Alt Text Selector:
        Locator byAltText = page.getByAltText("Company Logo");

        //Title Selector:
        Locator byTitle = page.getByTitle("Close");

        //Combination Selectors:
        Locator combined = page.locator("div#content >> text='Sign up'");

        //Chained Selectors:
        Locator chained = page.locator("div.container").locator("span.title");

        //nth-child Selector:
        //Select elements based on their position within a list or group.
        Locator nth = page.locator("ul li:nth-child(2)");

        //multiple
        Locator first = page.getByRole(AriaRole.LISTITEM).first();
        Locator last = page.getByRole(AriaRole.LISTITEM).last();


        //Relative Selectors:
        Locator parent = page.locator("div.parent");
        Locator child = parent.locator("span.child");

        //Proximity Selectors:
        //Use `near`, `above`, `below`, `left of`, and `right of` to locate elements based on their spatial relationship to others.
        Locator right = page.locator("text='Submit'").locator("right of=button");

        //Selectors with Regular Expressions:
        Locator regExpression = page.locator("text=^Sign.*"); // matches 'Sign up', 'Sign in'

        //Matching only visible elements
        page.locator("button").locator("visible=true").click();
        page.locator("button >> visible=true").click();
        page.locator("button:visible").click();

    }


}
