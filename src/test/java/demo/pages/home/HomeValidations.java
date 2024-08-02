package demo.pages.home;

import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import org.junit.Assert;

import static com.codeborne.selenide.Selenide.$;

public class HomeValidations {

    private final Home pageInstance;

    public HomeValidations(Home instance) {
        pageInstance = instance;
    }

    private static Logger logger = LoggerFactory.getLogger(HomeValidations.class);

    public Home verifyHomePageOpens() {
        logger.info(() -> "  STEP: Verify that Home page opens");
        Assert.assertTrue($(HomeElements.TITLE).exists());
        return pageInstance;
    }
}