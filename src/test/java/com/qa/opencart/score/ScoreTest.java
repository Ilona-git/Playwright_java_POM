package com.qa.opencart.score;
import com.microsoft.playwright.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.qa.opencart.constants.TestConstants.testTemplateWithUnit;

import com.qa.opencart.base.BaseTestScore;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Arrays;

//PlaywrightAssertions.assertThat has embedded retry for 5 sec
//will wait by default 30 sec for locator

public class ScoreTest extends BaseTestScore {


    @Test
    public void DetailTest() throws Exception {
        String surveySystemName = "system" + (int) (Math.random() * 100000000);
        System.out.println("Survey System Name: " + surveySystemName);

        String systemFacility = surveySystemName + " - facility";

        List<String> expectedActions = Arrays.asList("Manage Survey", "Edit Survey", "Create Dataset", "Close Survey", "Create Dashboard");
        List<String> expectedOptions = Arrays.asList("Survey Link", "Survey ID", "Survey Version", "Survey Template", "Synced", "Warnings", "Notes");

        scorePage.createSurveyWithoutDate(surveySystemName, testTemplateWithUnit);

        searchAndWaitSurveyByName(page, surveySystemName);

        page.pause();

        page.click("text=" + surveySystemName);
        assertThat(page.locator("dk")).isVisible();

        // Verify expected actions in the sidebar
        for (String action : expectedActions) {
            assertThat(page.locator("button:has-text('" + action + "')")).isVisible();
        }

        // Verify expected options in the sidebar
        for (String option : expectedOptions) {
            assertThat(page.locator("//div[text()='" + option + "']")).isVisible();
        }

        // Assert action header and its content
        assertThat(page.locator("//div[text()='Actions']")).isVisible();

        //closeSurvey(page, surveySystemName);
    }

    private void searchAndWaitSurveyByName(Page page, String surveySystemName) {
        page.fill("css=[id='search']", surveySystemName);
        page.waitForSelector("text=" + surveySystemName);
    }

    private void closeSurvey(Page page, String surveySystemName) {
        page.click("text=" + surveySystemName + " >> css=[data-testid='close-survey']");
    }

}
