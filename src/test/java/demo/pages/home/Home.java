package demo.pages.home;

import demo.utilsCommon.ui.UIAction;
import net.thucydides.core.pages.PageObject;

public class Home extends PageObject {

    public HomeValidations validate;

    public Home() {
        validate = new HomeValidations(this);
    }

    public void acceptCookiePolicy() {
        if ($(HomeElements.ACCEPT_COOKIE_POLICY).isVisible())
            $(HomeElements.ACCEPT_COOKIE_POLICY).click();
    }

    public Home navigateToEconomicCalendar() {
        UIAction.click(HomeElements.RESEARCH_AND_EDUCATION);
        UIAction.click(HomeElements.ECONOMIC_CALENDAR);

        //$(HomeElements.ECONOMIC_CALENDAR).click();
        return this;
    }

    public Home navigateToResearchAndEducation() {
        UIAction.click(HomeElements.RESEARCH_AND_EDUCATION);
        $(HomeElements.EDUCATIONAL_VIDEOS).click();
        return this;
    }
}
