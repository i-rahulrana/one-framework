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

package com.github.one.actions.device;

import static java.time.Duration.ofSeconds;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.apache.logging.log4j.LogManager.getLogger;

import com.github.one.actions.CommonActions;
import com.github.one.actions.interfaces.device.IDeviceActions;
import com.github.one.actions.interfaces.listeners.device.IDeviceActionsListener;
import com.github.one.config.ui.mobile.device.VideoSetting;
import com.github.one.enums.DeviceType;
import com.github.one.enums.ListenerType;
import com.github.one.enums.Message;
import com.github.one.enums.PlatformType;
import com.github.one.manager.ParallelSession;
import com.github.one.utils.ErrorHandler;
import com.github.one.utils.VideoUtil;
import io.appium.java_client.HasOnScreenKeyboard;
import io.appium.java_client.HidesKeyboard;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.android.AndroidStopScreenRecordingOptions;
import io.appium.java_client.ios.IOSStartScreenRecordingOptions;
import io.appium.java_client.ios.IOSStopScreenRecordingOptions;
import io.appium.java_client.screenrecording.CanRecordScreen;
import org.apache.logging.log4j.Logger;

/**
 * Handle all device specific actions.
 *
 * @author Rahul Rana
 * @since 31-May-2023
 */
public class DeviceActions implements IDeviceActions {
    private static final IDeviceActions DEVICE_ACTIONS = new DeviceActions ();
    private static final Logger         LOGGER         = getLogger ();

    /**
     * Handles all device specific actions.
     *
     * @return {@link IDeviceActions} instance object
     */
    public static IDeviceActions onDevice () {
        return DEVICE_ACTIONS;
    }

    private final IDeviceActionsListener listener;

    protected DeviceActions () {
        this.listener = ParallelSession.getSession ().getListener (ListenerType.DEVICE_ACTION);
    }

    @Override
    public void hideKeyboard () {
        LOGGER.info ("Hiding the visible keyboard...");
        ofNullable (this.listener).ifPresent (IDeviceActionsListener::onHideKeyboard);
        checkKeyboardSupported ();
        if (isKeyboardVisible ()) {
            CommonActions.performDriverAction (HidesKeyboard::hideKeyboard);
        }
    }

    @Override
    public boolean isKeyboardVisible () {
        LOGGER.info ("Checking if keyboard is visible...");
        ofNullable (this.listener).ifPresent (IDeviceActionsListener::onIsKeyboardVisible);
        checkKeyboardSupported ();
        return CommonActions.getDriverAttribute (HasOnScreenKeyboard::isKeyboardShown, false);
    }

    @Override
    public void startRecording () {
        final var platform = ParallelSession.getSession ().getPlatformType ();
        if (platform == PlatformType.ANDROID || platform == PlatformType.IOS) {
            final var mobileSetting = ParallelSession.getSession ().getMobileSetting ()
                .getDevice ();
            final var setting = mobileSetting.getVideo ();
            if (mobileSetting.getType () != DeviceType.CLOUD && setting.isEnabled ()) {
                final var screen = (CanRecordScreen) ParallelSession.getSession ().getDriver ();
                startRecording (screen, setting, platform);
            }
        }
    }

    @Override
    public void stopRecording () {
        final var platform = ParallelSession.getSession ().getPlatformType ();
        if (platform == PlatformType.ANDROID || platform == PlatformType.IOS) {
            final var mobileSetting = ParallelSession.getSession ().getMobileSetting ()
                .getDevice ();
            final var setting = mobileSetting.getVideo ();
            if (mobileSetting.getType () != DeviceType.CLOUD && setting.isEnabled ()) {
                final var screen = (CanRecordScreen) ParallelSession.getSession ().getDriver ();
                final var content = stopRecording (screen, platform);
                VideoUtil.saveVideo (content);
            }
        }
    }

    private void checkKeyboardSupported () {
        final var platform = ParallelSession.getSession ().getPlatformType ();
        if (platform == PlatformType.WEB) {
            ErrorHandler.throwError (Message.NO_KEYBOARD_ERROR);
        }
    }

    private AndroidStartScreenRecordingOptions getAndroidRecordOptions (final VideoSetting setting) {
        final var androidSetting = setting.getAndroid ();
        final var options = AndroidStartScreenRecordingOptions.startScreenRecordingOptions ();
        if (!isNull (androidSetting)) {
            options.withBitRate (androidSetting.getBitRate () * 100000);
        }
        options.withVideoSize (setting.getSize ())
            .withTimeLimit (ofSeconds (setting.getTimeLimit ()));
        return options;
    }

    private IOSStartScreenRecordingOptions getIOSRecordOptions (final VideoSetting setting) {
        final var iosSetting = setting.getIos ();
        final var options = IOSStartScreenRecordingOptions.startScreenRecordingOptions ();
        if (!isNull (iosSetting)) {
            options.withFps (iosSetting.getFps ())
                .withVideoQuality (iosSetting.getQuality ())
                .withVideoType (iosSetting.getCodec ());
        }
        options.withTimeLimit (ofSeconds (setting.getTimeLimit ()));
        if (!isNull (setting.getSize ())) {
            options.withVideoScale (setting.getSize ());
        }
        return options;
    }

    private void startRecording (final CanRecordScreen screen, final VideoSetting setting,
        final PlatformType platform) {
        if (platform == PlatformType.ANDROID) {
            final var options = getAndroidRecordOptions (setting);
            screen.startRecordingScreen (options);
        } else {
            final var options = getIOSRecordOptions (setting);
            screen.startRecordingScreen (options);
        }
    }

    private String stopRecording (final CanRecordScreen screen, final PlatformType platform) {
        final String content;
        if (platform == PlatformType.ANDROID) {
            final var options = AndroidStopScreenRecordingOptions.stopScreenRecordingOptions ();
            content = screen.stopRecordingScreen (options);
        } else {
            final var options = IOSStopScreenRecordingOptions.stopScreenRecordingOptions ();
            content = screen.stopRecordingScreen (options);
        }
        return content;
    }
}
