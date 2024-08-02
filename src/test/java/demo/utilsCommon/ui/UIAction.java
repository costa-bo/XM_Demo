package demo.utilsCommon.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.$;

public class UIAction {

    /**
     * Method to be used for the click action on a webelement (button/link)
     * @param by element locator
     */
    public static void click(By by) {
        WebElement buttonOrLink = $(by);
        buttonOrLink.click();
    }

    /**
     * Method to be used for the clearing and fulfilling of an input field
     * @param by element locator
     * @param value data to be inserted in input field
     */
    public static void input(By by, String value) {
        WebElement input = $(by);
        if (value != null && !value.isEmpty()) {
            input.clear();
            input.sendKeys(value);
        }
    }
}
