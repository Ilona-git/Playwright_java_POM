package com.qa.opencart.pages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;


public class ScorePage {

    public Page page;

    public ScorePage(Page page) {
        this.page = page;
    }

    // Method to create a survey without a date
    public void createSurveyWithoutDate(String system, String template) throws Exception {
        String key = generateUnique("key");

        // Click 'Create Survey' button
        clickCreateSurveyButton();

        // Enter survey details
        enterSystem(system);
        enterFacility("facility");
        enterKey(key);
        selectType("SCORE");
        selectSurveyTemplate(template);

        // Attempt to save, regenerate the key if necessary
        clickSaveWithoutWait();

        while (isKeyNotUniqueErrorDisplayed()) {
            key = generateUnique("key");
            enterKey(key);
            clickSaveWithoutWait();
        }

        // Wait for the survey dialog to disappear
        //waitForCreateSurveyDialogToDisappear(90);

        // Refresh the browser window
        //page.reload();
    }

    // Click the 'Create Survey' button
    public void clickCreateSurveyButton() {
        page.click("text='Create Survey'");
    }

    // Enter system field
    public void enterSystem(String system) {
        page.fill("#system", system);
    }

    // Enter facility field
    public void enterFacility(String facility) {
        page.fill("#facility", facility);
    }

    // Enter key field
    public void enterKey(String key) {
        page.fill("#key", key);
    }

    // Select survey template from a dropdown
    public void selectSurveyTemplate(String template) {
        page.click("(//div[@role='combobox'])[2]");
        page.click("span:has-text('" + template + "')");
    }

    // Click 'Save' without waiting for dialog to close
    public void clickSaveWithoutWait() {
        page.click("button:has-text(' Save')");
    }

    // Check if the key is not unique
    public boolean isKeyNotUniqueErrorDisplayed() {
        return page.isVisible("text='Key is not unique'"); // Adjust the selector to your error message
    }

    // Wait for the 'Create Survey' dialog to disappear
    public void waitForCreateSurveyDialogToDisappear(int timeoutSeconds) {
        page.waitForSelector("text='Create Survey'", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.DETACHED).setTimeout(timeoutSeconds * 1000));
    }

    // Helper method to generate a unique key (simplified)
    public String generateUnique(String prefix) {
        return prefix + System.currentTimeMillis();
    }

    public void selectType(String type) {
        // Click on the dropdown or combobox element
//        page.click("div[role='combobox']");
//        page.click("text='" + type + "'");

        page.locator("[data-test-id=\"survey-form-type\"]").getByRole(AriaRole.COMBOBOX).click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("SCORE").setExact(true)).click();
    }

}
