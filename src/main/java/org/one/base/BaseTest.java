package org.one.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.one.configs.FilepathConfig;
import org.one.drivers.AndroidDriverManager;
import org.one.drivers.AppiumServiceManager;
import org.one.drivers.DriverManager;
import org.one.drivers.IOSDriverManager;
import org.one.enums.PlatformName;
import org.one.utils.FingerGestureUtils;
import org.one.utils.PropertiesUtil;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

public class BaseTest {
    private static final Logger log = LogManager.getLogger (BaseTest.class);

    @Getter
    private static  FingerGestureUtils<AndroidDriver> fingerGesture;
    private         AppiumDriverLocalService    service;
    @Getter
    private static  RemoteWebDriver             driver;
    private static  String                      model;

    @BeforeSuite
    public void beforeSuite () {
        // Init connection with Global properties file.
        log.info ("Initializing resource path config file.");
        PropertiesUtil.initGlobalProps (FilepathConfig.GLOBAL_PROPERTIES);
        // Init platform type
        String platformType = PropertiesUtil.getGlobalPropValue ("platformType");
        System.setProperty ("platformType", platformType);
        // Init property name and model
        String platformName = PropertiesUtil.getGlobalPropValue ("platformName");
        System.setProperty ("platformName", platformName);
        // Init model
        model = PropertiesUtil.getGlobalPropValue ("model");
        System.setProperty ("model", model);
    }

    @BeforeTest (alwaysRun = true)
    @Parameters ("platform")
    public void beforeTest (String platform) {
        if (platform.equalsIgnoreCase (PlatformName.IOS.name ()) || platform.equalsIgnoreCase (PlatformName
                .ANDROID.name ())) {
            this.service = AppiumServiceManager.composeService ()
                    .composed ()
                    .buildService ();
            this.service.start ();
        }
    }

    @BeforeClass
    @Parameters ("platform")
    public void beforeClass (String platform) {
        // Init driver
        driver = DriverManager.createDriver ()
                .service (this.service)
                .model (model)
                .isHeadless (false)
                .platformType (platform)
                .create ()
                .getMobileDriver ();
    }

    @BeforeMethod
    public void beforeMethod () {
        if (System.getProperty ("platformName").equalsIgnoreCase ("Android")) {
            AndroidDriverManager.invokeApplication ();
        } else if (System.getProperty ("platformName").equalsIgnoreCase ("iOS")) {
            IOSDriverManager.invokeApplication ();
        }
    }

    @AfterMethod
    public void afterMethod () {
        if (System.getProperty ("platformName").equalsIgnoreCase ("Android")) {
            AndroidDriverManager.terminateApplication ();
        } else if (System.getProperty ("platformName").equalsIgnoreCase ("iOS")) {
            IOSDriverManager.terminateApplication ();
        }
    }

    @AfterTest (alwaysRun = true)
    @Parameters ("platform")
    public void afterTest (String platform) {
        driver.quit ();
        if (platform.equalsIgnoreCase (PlatformName.IOS.name ()) || platform.equalsIgnoreCase (PlatformName
                .ANDROID.name ())) {
            this.service.stop ();
        }
    }

    @AfterSuite
    public void afterSuite () {
        log.info ("After Suite");
    }
}
