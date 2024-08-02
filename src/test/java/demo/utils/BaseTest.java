package demo.utils;

import com.codeborne.selenide.WebDriverRunner;
import demo.utils.config.AppConfig;
import demo.utils.config.SerenityConfig;
import demo.utils.data.TestDataHelper;
import net.serenitybdd.core.pages.PageObject;
//import net.thucydides.core.annotations.Managed;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.WithTag;
import net.serenitybdd.annotations.WithTags;
//import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import demo.utils.hooks.BeforeTest;

import java.lang.reflect.Method;
import java.util.Properties;

public class BaseTest extends PageObject {

    @Managed
    public WebDriver driver;

    @Rule
    public TestName testName = new TestName();

    private Page page;

    private BeforeTest before;
    TestDataHelper testDataHelper = new TestDataHelper();

    private EnvironmentVariables environmentVariables;

    public int testStep;
    public String stepDescription;

    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @Before
    public void beforeTest() {
        logger.info(">>>>>>>>>>>>>>>>>>> EXECUTE TEST: " + testName.getMethodName());
        SerenityConfig.setConfiguration(environmentVariables);

        // Get the annotation of Test Type (UI, WebService, MIX etc)
        Method[] methods = getClass().getMethods();
        Method method = null;
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].toString().contains(testName.getMethodName() + "()")) {
                method = methods[i];
            }
        }
        WithTag a = null;
        try {
            a = this.getClass().getMethod(method.getName()).getAnnotation(WithTag.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        String type = "";
        if (null != a) {
            type = a.type();
        }
        if (!"WebService".equals(type)) {
            open();
            WebDriverRunner.setWebDriver(driver);
            if (AppConfig.winResolutionHeight == 0 || AppConfig.winResolutionWidth == 0)
                driver.manage().window().maximize();
            else
                driver.manage().window().setSize(
                        new Dimension(AppConfig.winResolutionWidth, AppConfig.winResolutionHeight));
        }
    }

    public boolean firstTC() {
        return hook("setup()");
    }

    private boolean hook(String hookMethodName) {
        Method[] methods = getClass().getMethods();
        Method method = null;
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].toString().contains(hookMethodName)) {
                method = methods[i];
                break;
            }
        }

        WithTags withTags = null;
        try {
            withTags = this.getClass().getMethod(method.getName()).getAnnotation(WithTags.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (withTags != null) {
            WithTag[] tags = withTags.value();
            boolean found = false;
            for (int j = 0; j < tags.length; j++) {
                if (tags[j].name().equals(testName.getMethodName())) {
                    found = true;
                    break;
                }
            }
            return found;
        } else {
            WithTag a = null;
            try {
                a = this.getClass().getMethod(method.getName()).getAnnotation(WithTag.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            String name = "";
            if (null != a) {
                name = a.name();
            }
            if (name.equals(testName.getMethodName())) {
                return true;
            } else {
                return false;
            }
        }
    }

    public BeforeTest before(String shortDescription) {
        logger.info(">>>>> BEFORE TEST: " + shortDescription);
        if (before == null) {
            before = new BeforeTest();
        }
        return before;
    }


    public Properties getTestData(int step) {
        return testDataHelper.getTestData(step);
    }

    @Steps
    private TestStep steps;

    public Page step(int stepNumber, String shortDescription) {
        testStep = stepNumber;
        stepDescription = shortDescription;

        if (page == null) {
            page = new Page();
        }
        return page;
    }

    public void end() {
        steps.generateSerenityStep(stepDescription);
    }
}