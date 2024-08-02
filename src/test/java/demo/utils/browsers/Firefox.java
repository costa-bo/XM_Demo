package demo.utils.browsers;

import demo.utils.config.Resources;
import demo.utils.config.SerenityConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.thucydides.core.webdriver.DriverSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class Firefox implements DriverSource {

    @Override
    public WebDriver newDriver() {
        if (null != SerenityConfig.getConfiguration("webdriver.remote.url")) {
            try {
                if (SerenityConfig.getConfiguration("webdriver.remote.url").isEmpty()) {
                    return null;
                } else {
                    RemoteWebDriver driver = new RemoteWebDriver(
                            new URL(System.getProperty("webdriver.remote.url")), getFirefoxOptions());
                    driver.setFileDetector(new LocalFileDetector());
                    return driver;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return new FirefoxDriver(getFirefoxOptions());
        }
    }

    @Override
    public boolean takesScreenshots() {
        return true;
    }

    private FirefoxOptions getFirefoxOptions() {
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(getFirefoxProfile());
        //options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

        if (null != SerenityConfig.getConfiguration("headless.mode")) {
            if (SerenityConfig.getConfiguration("headless.mode").equals("true")) {
               // options.setHeadless(true);
            }
        }

        options.setBinary("C:\\Users\\vpetrou\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
        return options;
    }

    private FirefoxProfile getFirefoxProfile() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        if (null != SerenityConfig.getConfiguration("download.default_directory") ||
                !"".equals(SerenityConfig.getConfiguration("download.default_directory"))) {
            profile.setPreference("browser.download.dir", Resources.getDownloadPath());
        } else {
            profile.setPreference("browser.download.dir", SerenityConfig.getConfiguration("download.default_directory"));
        }
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                "application/zip," +
                "text/csv," +
                "text/plain," +
                "image/jpeg," +
                "application/octet-stream," +
                "application/xml,text/xml," +
                "application/pdf," +
                "application/json," +
                "application/x-pdf," +
                "application/vnd.ms-excel," +
                "application/msword," +
                "application/vnd.ms-powerpoint," +
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet," +
                "application/vnd.openxmlformats-officedocument.presentationml.presentation," +
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
        );
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);
        profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
        profile.setPreference("browser.download.manager.focusWhenStarting", false);
        profile.setPreference("browser.download.manager.useWindow", false);
        profile.setPreference("browser.download.manager.showAlertOnComplete", false);
        profile.setPreference("browser.download.manager.closeWhenDone", false);

        profile.setPreference("pdfjs.disabled", true);
        profile.setPreference("plugin.disable_full_page_plugin_for_types",
                "application/pdf,application/vnd.adobe.xfdf,application/vnd.fdf,application/vnd.adobe.xdp+xml");
        return profile;
    }

}
