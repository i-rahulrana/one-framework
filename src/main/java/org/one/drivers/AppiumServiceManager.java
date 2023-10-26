package org.one.drivers;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import lombok.Builder;

import java.nio.file.Path;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.*;
import static java.lang.System.getProperty;
import static java.text.MessageFormat.format;

@Builder (builderMethodName = "composeService", buildMethodName = "composed")
public class AppiumServiceManager {
    private static final String USER_DIR = getProperty ("user.dir");

    @Builder.Default
    private String driverName = "uiautomator2";
    @Builder.Default
    private String host       = "127.0.0.1";
    @Builder.Default
    private int    port       = 4723;

    public AppiumDriverLocalService buildService () {
        final var logFile = Path.of (USER_DIR, "logs", format ("appium-{0}.log", this.driverName))
                .toFile ();
        final var builder = new AppiumServiceBuilder ();
        return builder.withIPAddress (this.host)
                .usingPort (this.port)
                .withLogFile (logFile)
                //.withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                //.usingDriverExecutable(new File("/usr/local/bin/node"))
                .withArgument (BASEPATH, "/wd/hub")
                .withArgument (LOG_LEVEL, "warn")
                .withArgument (USE_DRIVERS, this.driverName)
                .withArgument (SESSION_OVERRIDE)
                .withArgument (LOCAL_TIMEZONE)
                .build ();
    }
}

