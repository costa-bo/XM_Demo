package demo.utils.common;

import com.codeborne.selenide.WebDriverRunner;
import com.ibm.icu.impl.Assert;
import demo.utils.config.AppConfig;
import io.appium.java_client.functions.ExpectedCondition;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Wait {

    public static void forPageToLoad() {
        ExpectedCondition<Boolean> expectation =
                driver -> {
                    assert driver != null;
                    return ((JavascriptExecutor) driver)
                            .executeScript("return document.readyState").toString().equals("complete");
                };
        try {
            Thread.sleep(1200);
            //WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), AppConfig.timeoutInSeconds);
            WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(AppConfig.timeoutInSeconds));
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }
}
