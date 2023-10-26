package org.one.drivers;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import lombok.Builder;
import org.one.configs.DeviceConfig;
import org.one.data.AndroidDeviceModel;
import org.openqa.selenium.Capabilities;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;

import static java.lang.System.getProperty;
import static java.text.MessageFormat.format;

@Builder (builderMethodName = "createDriver", buildMethodName = "create")
public class AndroidDriverManager extends DeviceConfig implements IDriverManager<AndroidDriver> {
    private static final String USER_DIR = getProperty("user.dir");
    private static final long TIME_OUT = 120;

    private static  AndroidDriver               driver;
    private AppiumDriverLocalService service;
    private static AndroidDeviceModel device;
    private         String                      model;
    private         boolean                     isHeadless;

    @Override
    public AndroidDriver getDriver() {
        driver = new AndroidDriver(this.service.getUrl(), buildCapabilities(model));
        return driver;
    }

    private Capabilities buildCapabilities(final String model) {
        try {
            device = readAndroidDeviceConfig().getAndroidDeviceByName(model);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final var options = new UiAutomator2Options ();
        options.setPlatformName("Android")
                .setPlatformVersion(device.getPlatformVersion())
                .setDeviceName(device.getDeviceName())
                .setAvd(device.getDeviceName())
                .setApp(Path.of(USER_DIR, format("builds/{0}.apk", device.getApp()))
                        .toString())
                .setAppWaitActivity(device.getActivity())
                .setAppPackage(device.getPackageName())
                .setAutoGrantPermissions(true)
                .setFullReset(false)
                .setIsHeadless(this.isHeadless)
                .setNewCommandTimeout (Duration.ofSeconds (TIME_OUT))
                .setCapability("appium:settings[ignoreUnimportantViews]", true);
        return options;
    }

    public static void terminateApplication() {
        driver.terminateApp(device.getPackageName());
    }

    /**
     * This method used to invoke the android application.
     */
    public static void invokeApplication() {
        driver.activateApp(device.getPackageName());
    }
}
