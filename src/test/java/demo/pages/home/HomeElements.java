package demo.pages.home;

import net.serenitybdd.core.pages.ResolvableElement;
import org.openqa.selenium.By;

public class HomeElements {
    public static final By TITLE = By.xpath("//title[contains(text(), 'XM')]");
    public static final By ACCEPT_COOKIE_POLICY = By.xpath("//button[contains(text(), 'ACCEPT ALL') and contains(@class, 'Cookie')]");
    public static final By RESEARCH_AND_EDUCATION = By.cssSelector("#main-nav > li.main_nav_research");
    public static final By ECONOMIC_CALENDAR = By.xpath("//a[contains(text(), 'Economic Calendar')]");
    public static final By EDUCATIONAL_VIDEOS = By.xpath("//a[contains(text(), 'Educational Videos')]");
}
