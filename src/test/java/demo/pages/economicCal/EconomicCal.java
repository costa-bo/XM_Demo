package demo.pages.economicCal;

import demo.EnumData;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.switchTo;

public class EconomicCal extends PageObject {

    public EconomicCalValidations validations;

    public EconomicCal(){validations = new EconomicCalValidations(this);}

    private void selectSliderThumb() {
        switchTo().frame((WebElement) $(EconomicCalElements.IFRAME_CAL));
        $(EconomicCalElements.SLIDER_THUMB).click();
    }
    private void returnSliderLeft() {
        while (!($(EconomicCalElements.CURRENT_SLIDER_POSITION).getText()).equals(
                EnumData.SliderChronicleOptions.RecentNext.label)) {
            withAction().sendKeys(Keys.ARROW_LEFT).build().perform();
        }
    }

    public EconomicCal moveSliderTo(String sliderStop){
        selectSliderThumb();
        returnSliderLeft();
        while (!($(EconomicCalElements.CURRENT_SLIDER_POSITION).getText()).equals(sliderStop)){
            withAction().sendKeys(Keys.ARROW_RIGHT).build().perform();
        }
        return this;
    }
}
