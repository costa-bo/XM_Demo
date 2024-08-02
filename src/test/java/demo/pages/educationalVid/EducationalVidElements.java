package demo.pages.educationalVid;

import org.openqa.selenium.By;

public class EducationalVidElements {
    public static final By LESSON_1_1 = By.xpath("//a[contains(text(),'Lesson 1.1')]");
    public static final By PLAY_VIDEO = By.xpath("//div[@aria-label='Play Video']");
    public static final By PLAY_PROGRESS_TIME = By.xpath("//div[@class='player-progress-time']");
    public static final By EXPAND_VIDEO_SERIES_INTO_MARKETS = By.xpath("//button[(text()='Intro to the Markets')]");
    public static final By IFRAME_VIDEO = By.xpath("//iframe[@frameborder='0']");
}
