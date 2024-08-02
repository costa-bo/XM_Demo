package demo.utils.browsers;

import org.openqa.selenium.PageLoadStrategy;
import demo.utils.config.Resources;
import demo.utils.config.SerenityConfig;
import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class Chrome implements DriverSource {

    @Override
    public WebDriver newDriver() {
        if (null != SerenityConfig.getConfiguration("webdriver.remote.url")) {
            try {
                if (SerenityConfig.getConfiguration("webdriver.remote.url").isEmpty()) {
                    return null;
                } else {
                    RemoteWebDriver driver = new RemoteWebDriver(
                            new URL(SerenityConfig.getConfiguration("webdriver.remote.url")), getChromeOptions());
                    driver.setFileDetector(new LocalFileDetector());
                    return driver;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return new ChromeDriver(getChromeOptions());
        }
    }

    @Override
    public boolean takesScreenshots() {
        return true;
    }

    private static ChromeOptions getChromeOptions() {
        //WebDriverManager.chromedriver().setup();

        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        if (null != SerenityConfig.getConfiguration("download.default_directory")) {
            if (SerenityConfig.getConfiguration("download.default_directory").equals("")) {
                chromePrefs.put("download.default_directory", Resources.getDownloadPath());
            } else {
                chromePrefs.put("download.default_directory",
                        SerenityConfig.getConfiguration("download.default_directory"));
            }
        } else {
            chromePrefs.put("download.default_directory", Resources.getDownloadPath());
        }
        chromePrefs.put("safebrowsing.enabled", "true");
        chromePrefs.put("download.directory_upgrade", "true");
        chromePrefs.put("download.prompt_for_download", "false");

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("lang=en");
        options.addArguments("disable-infobars"); // disabling infobars
        //options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--ignore-ssl-errors=yes");
        options.addArguments("--ignore-certificate-errors");
        if (null != SerenityConfig.getConfiguration("headless.mode")) {
            if (SerenityConfig.getConfiguration("headless.mode").equals("true")) {
                options.addArguments("headless");
            }
        }
        //options.addArguments("--disable-popup-blocking");
        options.addArguments("--incognito"); // Start Chrome in incognito mode
        options.addArguments("--disable-default-apps"); // Disable default apps
        options.addArguments("chrome.switches", "--disable-extensions");
        options.addArguments("--disable-first-run-ui");
        options.addArguments("--disable-javascript");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments("--disable-search-engine-choice-screen");
        options.setCapability(ChromeOptions.CAPABILITY, options);
        //options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        options.setExperimentalOption("excludeSwitches", new String[] {"disable-popup-blocking"});
        //options.setCapability("chrome.switches", Arrays.asList("--incognito"));
        return options;
    }
}
