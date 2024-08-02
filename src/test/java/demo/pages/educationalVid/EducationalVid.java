package demo.pages.educationalVid;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import demo.pages.economicCal.EconomicCalElements;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.manager.SeleniumManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.switchTo;

public class EducationalVid extends PageObject {
    public void playVideo() throws InterruptedException {
        $(EducationalVidElements.EXPAND_VIDEO_SERIES_INTO_MARKETS).waitUntilClickable();
        $(EducationalVidElements.EXPAND_VIDEO_SERIES_INTO_MARKETS).click();
        $(EducationalVidElements.LESSON_1_1).waitUntilClickable();
        $(EducationalVidElements.LESSON_1_1).click();
        switchTo().frame((WebElement) $(EducationalVidElements.IFRAME_VIDEO));
        $(EducationalVidElements.PLAY_VIDEO).waitUntilClickable();
        Thread.sleep(1000);
        $(EducationalVidElements.PLAY_VIDEO).click();

        LocalTime videoPlayTime = LocalTime.of(0,0,0);
        long startTime = System.currentTimeMillis();
        do {
            if (!$(EducationalVidElements.PLAY_PROGRESS_TIME).getText().isEmpty())
                videoPlayTime = LocalTime.parse($(EducationalVidElements.PLAY_PROGRESS_TIME).getText());

            if ((System.currentTimeMillis()-startTime > 30000))
                Assert.fail("video cannot play for 5 seconds in time span of 30 seconds");

        } while (videoPlayTime.isBefore(LocalTime.of(0,0,5)));


        //WebDriverWait wait = new WebDriverWait(getDriver(), 40);
        //wait.pollingEvery(Duration.ofMillis(500));
        //wait.until(ExpectedConditions.elementToBeClickable($(EducationalVidElements.PLAY_PROGRESS_TIME).getText() == "00:05"));
        //wait.until($(EducationalVidElements.PLAY_PROGRESS_TIME).getText() == ("00:05"));
    }
}
