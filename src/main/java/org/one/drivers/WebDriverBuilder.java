package org.one.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Builder;
import org.one.configs.DeviceConfig;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.one.enums.PlatformName;

import java.time.Duration;

import static java.lang.System.getProperty;

@Builder (builderMethodName = "createDriver", buildMethodName = "create")
public class WebDriverBuilder extends DeviceConfig {
    private static final String USER_DIR = getProperty("user.dir");
    private static final int    IMPLICIT_TIMEOUT = 5;

    private static  RemoteWebDriver     driver;
    private         String              platformName;
    private         boolean             isHeadless;

    public RemoteWebDriver getDriver() {
        if (platformName.equalsIgnoreCase(PlatformName.CHROME.name())) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(buildCapabilities (platformName));
        } else if (platformName.equalsIgnoreCase(PlatformName.FIREFOX.name())) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver ();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds (IMPLICIT_TIMEOUT));
        setExecutionPlatform(platformName);
        return driver;
    }

    private ChromeOptions buildCapabilities(final String platformName) {
        final var options = new ChromeOptions ();
        options.setExperimentalOption ("", true);
        return options;
    }
}
