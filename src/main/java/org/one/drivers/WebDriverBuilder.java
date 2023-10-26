package org.one.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Builder;
import org.one.configs.DeviceConfig;
import org.one.enums.OperatingSystems;
import org.one.utils.DownloadManager;
import org.one.utils.PropertiesUtil;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.one.enums.PlatformName;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.getProperty;

@Builder (builderMethodName = "createDriver", buildMethodName = "create")
public class WebDriverBuilder extends DeviceConfig {
    private static final String USER_DIR = getProperty("user.dir");
    private static final int    IMPLICIT_TIMEOUT = 5;

    private static  RemoteWebDriver     driver;
    private         String              browserName;
    private         boolean             isHeadless;

    public RemoteWebDriver getDriver() {
        if (browserName.equalsIgnoreCase(PlatformName.CHROME.name())) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(buildCapabilities (isHeadless));
        } else if (browserName.equalsIgnoreCase(PlatformName.FIREFOX.name())) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver ();
        }
        setExecutionPlatform(browserName);
        return driver;
    }

    private ChromeOptions buildCapabilities(final boolean isHeadless) {
        final var options = new ChromeOptions ();
        options.setExperimentalOption ("prefs", buildChromePreference (options))
                .addArguments ("--no-sandbox")
                .addArguments ("--disable-dev-shm-usage");
        if (isHeadless)
            options.addArguments ("--headless");
        return options;
    }

    private Map<String, Object> buildChromePreference(ChromeOptions chromeOptions) {
        Map<String, Object> chromePrefs = new HashMap<>();
        Map<String, Object> chromeProfile = new HashMap<>();
        Map<String, Object> contentSettings = new HashMap<>();
        // Suppress the un-necessary logs
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
        // Set Chrome Content settings
        contentSettings.put("geolocation", 2);
        chromeProfile.put("managed_default_content_settings", contentSettings);
        // Set Chrome profile on the basis of flag
        String isSetProfile = PropertiesUtil.getGlobalPropValue ("isSetProfile");
        String profileName = PropertiesUtil.getGlobalPropValue("profileName");
        if (!isSetProfile.equalsIgnoreCase("false")) {
            // chrome://version/
            String pathToProfile = null;
            String userName = System.getProperty("user.name");
            String operatingSystem = PropertiesUtil.getGlobalPropValue("operatingSystem");
            if (operatingSystem.contains(OperatingSystems.Mac.name())) {
                pathToProfile = "/Users/"+userName+"/Library/Application Support/Google/Chrome/";
            }
            else if (operatingSystem.contains(OperatingSystems.Windows.name())) {
                pathToProfile = "C:\\Users\\"+userName+"\\AppData\\Local\\Google\\Chrome\\User Data";
            }
            else if (operatingSystem.contains(OperatingSystems.Linux.name())) {
                pathToProfile = "/home/"+userName+"/.config/google-chrome/";
            }
            chromeOptions.addArguments("chrome.switches", "--disable-extensions");
            chromeOptions.addArguments("--user-data-dir=" + pathToProfile);
            chromeOptions.addArguments("--profile-directory=" + profileName);
        }
        // Set Chrome download directory
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("download.default_directory", DownloadManager.getDownloadDirectory());
        // Set Chrome preferences
        // SET CHROME PREFERENCE
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        // Set profile to Chrome preference
        chromePrefs.put("profile", chromeProfile);
        return chromePrefs;
    }
}
