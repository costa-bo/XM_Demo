package demo.utils.browsers;

import demo.utils.config.Resources;
import demo.utils.config.SerenityConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class Edge implements DriverSource {

    @Override
    public WebDriver newDriver() {
        if (null != SerenityConfig.getConfiguration("webdriver.remote.url")) {
            try {
                if (SerenityConfig.getConfiguration("webdriver.remote.url").isEmpty()) {
                    return null;
                } else {
                    RemoteWebDriver driver = new RemoteWebDriver(
                            new URL(System.getProperty("webdriver.remote.url")), getEdgeOptions());
                    driver.setFileDetector(new LocalFileDetector());
                    return driver;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return new EdgeDriver(getEdgeOptions());
        }
    }

    @Override
    public boolean takesScreenshots() {
        return true;
    }

    private static EdgeOptions getEdgeOptions() {
        WebDriverManager.edgedriver().setup();

        EdgeOptions options = new EdgeOptions();
        options.setCapability("profile.default_content_settings.popups", 0);
        options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

        //Note that the setting of download path, cannot be implemented with Edge Browser, as it doesn't use profiles.
        //It is a limitation of the browser itself, not the Edge WebDriver.
        //As such, there is no way to automatically download files to a specified location with Edge Browser.
        //So, variable "download.default_directory" can be used only by setting the default download directory.
        if (null != SerenityConfig.getConfiguration("download.default_directory") ||
                !"".equals(SerenityConfig.getConfiguration("download.default_directory"))) {
            options.setCapability("download.default_directory", Resources.getDownloadPath());
        } else {
            options.setCapability("download.default_directory", SerenityConfig.getConfiguration("download.default_directory"));
        }

        options.setCapability("safebrowsing.enabled", "true");
        options.setCapability("download.directory_upgrade", "true");
        options.setCapability("download.prompt_for_download", "false");
        options.setCapability("requireWindowFocus", true);
        options.setCapability("enablePersistentHover", false);
        options.setCapability("ignoreProtectedModeSettings", true);
        //options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        options.setCapability("chrome.switches", Arrays.asList("--incognito"));
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        options.setCapability("requireWindowFocus", true);
        options.setCapability("enablePersistentHover", false);
        if (null != SerenityConfig.getConfiguration("headless.mode")) {
            if (SerenityConfig.getConfiguration("headless.mode").equals("true")) {
                options.setCapability("headless", true);
            }
        }
        //options.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        return options;
    }

}
