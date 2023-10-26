package org.one.drivers;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import lombok.Builder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.one.configs.DeviceConfig;
import org.one.data.IOSDeviceModel;
import org.openqa.selenium.Capabilities;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;

import static java.lang.System.getProperty;
import static java.text.MessageFormat.format;

@Builder (builderMethodName = "createDriver", buildMethodName = "create")
public class IOSDriverManager extends DeviceConfig implements IDriverManager<IOSDriver> {
    private static final Logger LOGGER = LogManager.getLogger(IOSDriverManager.class);
    private static final String USER_DIR = getProperty ("user.dir");
    private static final long TIME_OUT = 120;

    private static  IOSDriver                   driver;
    private static  IOSDeviceModel              device = null;
    private         boolean                     isHeadless;
    private AppiumDriverLocalService service;
    private         String                      model;

    /**
     * This method used to terminate the iOS application.
     */
    @Override
    public IOSDriver getDriver() {
        driver = new IOSDriver (this.service.getUrl (), buildCapabilities (model));
        return driver;
    }

    private Capabilities buildCapabilities (final String model) {
        try {
            device = readIOSDeviceConfig().getIOSDeviceByName(model);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final var options = new XCUITestOptions ();
        options.setPlatformName (device.getPlatformName())
                .setPlatformVersion (device.getPlatformVersion())
                .setDeviceName (device.getDeviceName())
                .setApp (Path.of (USER_DIR, format ("builds/{0}.zip", device.getApp()))
                        .toString ())
                .setAutoAcceptAlerts (true)
                .setFullReset (false)
                .setConnectHardwareKeyboard(true)
                .setNewCommandTimeout (Duration.ofSeconds (TIME_OUT))
                .setIsHeadless (this.isHeadless);
        return options;
    }

    /**
     * This method used to terminate the iOS application.
     */
    public static void terminateApplication() {
        driver.terminateApp(device.getBundleId());
    }

    /**
     * This method used to invoke the iOS application.
     */
    public static void invokeApplication() {
        driver.activateApp(device.getBundleId());
    }
}
