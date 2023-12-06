/*
 * MIT License
 *
 * Copyright (c) 2023, Rahul Rana
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 */

package com.github.one.manager;

import static java.time.Duration.ofSeconds;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import com.github.one.config.ui.mobile.MobileSetting;
import com.github.one.config.ui.mobile.device.VirtualDeviceSetting;
import com.github.one.enums.ApplicationType;
import com.github.one.enums.DeviceType;
import com.github.one.enums.Message;
import com.github.one.utils.ErrorHandler;
import com.github.one.utils.Validator;
import com.github.one.config.ui.mobile.device.ApplicationSetting;
import com.github.one.config.ui.mobile.device.DeviceSetting;
import com.github.one.config.ui.mobile.device.WDASetting;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.ios.options.wda.XcodeCertificate;
import org.openqa.selenium.SessionNotCreatedException;

class IOSManager implements IDriverManager {
    private final MobileSetting mobileSetting;
    private final DeviceSetting settings;

    IOSManager () {
        this.mobileSetting = ParallelSession.getSession ().getMobileSetting ();
        this.settings = this.mobileSetting.getDevice ();
    }

    @Override
    public void setupDriver () {
        final var options = new XCUITestOptions ();
        setCommonCapabilities (options);
        if (this.settings.getType () == DeviceType.CLOUD) {
            setupCloudDriverOptions (options, this.settings.getCapabilities (), this.mobileSetting.getServer ()
                .getTarget ());
        } else {
            setupLocalSimulatorOptions (options);
        }
        try {
            ParallelSession.setDriver (new IOSDriver (ParallelSession.getSession ().getServiceManager ()
                .getServiceUrl (), options));
        } catch (final SessionNotCreatedException e) {
            ErrorHandler.handleAndThrow (Message.SESSION_NOT_STARTED, e);
        }
        navigateToBaseUrl (this.settings.getApplication ());
    }

    private void setApplicationCapabilities (final XCUITestOptions options, final ApplicationSetting application) {
        if (application.getType () == ApplicationType.WEB) {
            options.withBrowserName (application.getBrowser ()
                .name ());
        } else {
            setupApplicationOptions (application, options);
            options.setBundleId (application.getBundleId ());
        }
    }

    private void setCommonCapabilities (final XCUITestOptions options) {
        options.setAutomationName (this.mobileSetting.getServer ()
            .getDriver ()
            .getName ());
        options.setPlatformName (this.settings.getOs ()
            .name ());
        options.setPlatformVersion (this.settings.getVersion ());
        options.setDeviceName (this.settings.getName ());
        options.setUdid (this.settings.getUniqueId ());
        setApplicationCapabilities (options, this.settings.getApplication ());
    }

    private void setWdaOptions (final WDASetting wda, final XCUITestOptions options) {
        if (wda != null) {
            Validator.setOptionIfPresent (wda.getLocalPort (), options::setWdaLocalPort);
            options.setUseNewWDA (wda.isUseNew ());
            Validator.setOptionIfPresent (wda.getLaunchTimeout (), v -> options.setWdaLaunchTimeout (ofSeconds (v)));
            Validator.setOptionIfPresent (wda.getStartupRetries (), options::setWdaStartupRetries);
            Validator.setOptionIfPresent (wda.getConnectionTimeout (), i -> options.setWdaConnectionTimeout (ofSeconds (i)));
            Validator.setOptionIfPresent (wda.getStartupRetryInterval (),
                i -> options.setWdaStartupRetryInterval (ofSeconds (i)));
            options.setUsePrebuiltWda (wda.isUsePrebuilt ());
            Validator.setOptionIfPresent (wda.getUpdateBundleId (), options::setUpdatedWdaBundleId);

            if (isNotEmpty (wda.getTeamId ())) {
                final var certificate = new XcodeCertificate (wda.getTeamId (), wda.getSigningId ());
                options.setXcodeCertificate (certificate);
            }
        }
    }

    private void setupLocalSimulatorOptions (final XCUITestOptions options) {
        options.setAutoAcceptAlerts (this.settings.isAcceptAlerts ());
        options.setAutoDismissAlerts (!this.settings.isAcceptAlerts ());
        setupApplicationOptions (this.settings.getApplication (), options);
        setupVirtualDeviceSetting (this.settings.getType (), this.settings.getVirtualDevice (), options);
        options.setBundleId (this.settings.getApplication ()
            .getBundleId ());
        options.setClearSystemFiles (this.settings.isClearFiles ());
        options.setNoReset (this.settings.isNoReset ());
        options.setFullReset (this.settings.isFullReset ());
        options.setMaxTypingFrequency (this.settings.getTypingSpeed ());
        setWdaOptions (this.settings.getWda (), options);
    }

    private void setupVirtualDeviceSetting (final DeviceType deviceType, final VirtualDeviceSetting virtualDevice,
        final XCUITestOptions options) {
        if (deviceType == DeviceType.VIRTUAL) {
            options.setConnectHardwareKeyboard (virtualDevice.isConnectKeyboard ());
            options.setSimulatorStartupTimeout (ofSeconds (virtualDevice.getLaunchTimeout ()));
            options.setIsHeadless (virtualDevice.isHeadless ());
        }
    }
}
