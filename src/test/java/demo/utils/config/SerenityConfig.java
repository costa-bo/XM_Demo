package demo.utils.config;

//import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
//import net.thucydides.core.util.EnvironmentVariables;
//import net.thucydides.core.util.SystemEnvironmentVariables;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

public class SerenityConfig {
    private EnvironmentVariables environmentVariables;

    public SerenityConfig(EnvironmentVariables environmentVariables){
        this.environmentVariables = environmentVariables;
    }

    public static void setConfiguration(EnvironmentVariables environmentVariables) {
        //AppConfig.restBaseURL = environmentVariables.getProperty("rest.base.url");
        AppConfig.restBaseURL = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("rest.base.url");
        //AppConfig.restBaseURL = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("rest.base.url");

        AppConfig.winResolutionWidth = Integer.parseInt(EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("browser.resolution.width"));
        AppConfig.winResolutionHeight = Integer.parseInt(EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("browser.resolution.height"));

        AppConfig.timeoutInSeconds = Integer.parseInt(EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("wait.timeoutInSeconds"));

        UserConfig.adminUsername = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("user.admin.username");
        UserConfig.adminPassword = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("user.admin.password");
    }

    public static String getConfiguration(String propertyName) {
        EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
        return variables.getProperty(propertyName);
    }

}
