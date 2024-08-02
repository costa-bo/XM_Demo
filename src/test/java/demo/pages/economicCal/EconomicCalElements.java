package demo.pages.economicCal;

import org.openqa.selenium.By;

public class EconomicCalElements {
    public static final By SLIDER_THUMB = By.xpath("//div[@class='mat-slider-thumb']");
    public static final By IFRAME_CAL = By.id("iFrameResizer0");
    public static final By CALENDAR_ACTIVE_DAY = By.xpath("//button[contains(@class,'mat-calendar-body-active')]");
    public static final By CURRENT_SLIDER_POSITION = By.xpath("//span[contains(@class,'tc-finalval-tmz')]/child::div");
    public static final By RANGE_START_DATE = By.xpath("//button[contains(@class, 'mat-calendar-body-range-start')]");
    public static final By RANGE_END_DATE = By.xpath("//button[contains(@class, 'mat-calendar-body-range-end')]");
}
