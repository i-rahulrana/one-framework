package org.one.drivers;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import lombok.Builder;
import org.one.enums.PlatformName;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

@Builder(builderMethodName = "createDriver", buildMethodName = "create")
public class DriverManager {
    private static final long IMPLICIT_TIMEOUT = 10;

    private AppiumDriverLocalService    service;
    private String                      model;
    private String                      platformType;
    private boolean                     isHeadless;


    /**
     * This method gives appium driver instance.
     *
     * @return driver
     */
    public RemoteWebDriver getMobileDriver() {
        RemoteWebDriver driver = null;
        if (System.getProperty("platformName").equalsIgnoreCase(PlatformName.ANDROID.name()))
            driver = AndroidDriverManager.createDriver()
                    .service(this.service)
                    .model(model)
                    .isHeadless(isHeadless)
                    .create()
                    .getDriver();
        else if (System.getProperty("platformName").equalsIgnoreCase(PlatformName.IOS.name())) {
            driver = IOSDriverManager.createDriver()
                    .service(this.service)
                    .model(model)
                    .isHeadless(isHeadless)
                    .create()
                    .getDriver();
        } else if (System.getProperty("platformName").equalsIgnoreCase(PlatformName.CHROME.name())) {
            driver = WebDriverBuilder.createDriver ()
                    .platformName (platformType)
                    .isHeadless (isHeadless)
                    .create ()
                    .getDriver ();
        } else if (System.getProperty("platformName").equalsIgnoreCase(PlatformName.FIREFOX.name())) {
            driver = WebDriverBuilder.createDriver ()
                    .platformName (platformType)
                    .isHeadless (isHeadless)
                    .create ()
                    .getDriver ();
        }
        assert driver != null;
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_TIMEOUT));
        return driver;
    }

    /**
     * This method gives explicit wait duration.
     *
     * @return timeout
     */
    public static long getExplicitWaitDuration() {
        return IMPLICIT_TIMEOUT;
    }
}
