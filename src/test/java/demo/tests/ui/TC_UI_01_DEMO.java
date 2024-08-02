package demo.tests.ui;

import demo.EnumData;
import demo.utils.BaseTest;
import net.serenitybdd.annotations.WithTag;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.annotations.Title;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class TC_UI_01_DEMO extends BaseTest {
    @Before
    @WithTag(name = "TC_UI_01_DEMO_01_TEST")
    public void setup() {
        if (firstTC()) {
            before("Accept Cookies")
                    .home.acceptCookiePolicy();
        }
    }

    @Test
    @Title("TC_UI_01_DEMO_01_TEST: task1")
    public void TC_UI_01_DEMO_01_TEST() throws InterruptedException {
        step(1, "Verify Home Page Opens & navigate to economic Calendar")
                .home.validate.verifyHomePageOpens()
                .navigateToEconomicCalendar();
        end();
        step(2, "check Today slider")
                .economicCal.moveSliderTo(EnumData.SliderChronicleOptions.Today.label)
                .validations.checkCalendarTodayDate();
        end();
        step(3, "check Tomorrow slider")
                .economicCal.moveSliderTo(EnumData.SliderChronicleOptions.Tomorrow.label)
                .validations.checkCalendarTomorrowDate();
        end();
        step(4, "check Next Week slider")
                .economicCal.moveSliderTo(EnumData.SliderChronicleOptions.NextWeek.label)
                .validations.checkCalendarNextWeekDate();
        end();
        step(5, "Navigate to Educational Videos")
                .home.navigateToResearchAndEducation();
        end();
        step(5, "Play educational video 1.1")
                .educationalVid.playVideo();
        end();
    }
}