package demo.pages.economicCal;

import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import org.junit.Assert;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class EconomicCalValidations {
    private final EconomicCal pageInstance;
    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E MMM dd yyyy", Locale.ENGLISH);

    public EconomicCalValidations(EconomicCal instance){
        pageInstance = instance;
    }
    private static Logger logger = LoggerFactory.getLogger(EconomicCalValidations.class);

    public void checkCalendarTodayDate() {
        LocalDate economicCalToday = LocalDate.parse(
                $(EconomicCalElements.CALENDAR_ACTIVE_DAY).getAttribute("aria-label"), dateTimeFormatter);
        //Assert.assertEquals(economicCalendarTodayDay, LocalDate.now().format(dateTimeFormatter));
        Assert.assertEquals(economicCalToday, LocalDate.now());
        switchTo().parentFrame(); //Todo needed an iframeMnger
    }

    public void checkCalendarTomorrowDate() {
        LocalDate economicCalToday = LocalDate.parse(
                $(EconomicCalElements.CALENDAR_ACTIVE_DAY).getAttribute("aria-label"), dateTimeFormatter);
        Assert.assertEquals(economicCalToday, LocalDate.now().plusDays(1));
        switchTo().parentFrame(); //Todo needed an iframeMnger
    }
    public void checkCalendarNextWeekDate() { //Next Week starts in 6 days?
        LocalDate sysStartDate = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate sysEndDate = sysStartDate.plusDays(6);

        LocalDate economicCalStartDay = LocalDate.parse(
                $(EconomicCalElements.RANGE_START_DATE).getAttribute("aria-label"), dateTimeFormatter);
        LocalDate economicCalEndDay = LocalDate.parse(
                $(EconomicCalElements.RANGE_END_DATE).getAttribute("aria-label"), dateTimeFormatter);

        Assert.assertEquals(sysStartDate, economicCalStartDay);
        Assert.assertEquals(sysEndDate, economicCalEndDay);
        switchTo().parentFrame(); //Todo needed an iframeMnger
    }
}
