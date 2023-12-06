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

import static java.util.Optional.ofNullable;
import static org.apache.logging.log4j.LogManager.getLogger;

import com.github.one.actions.CommonActions;
import com.github.one.actions.interfaces.device.IAndroidDeviceActions;
import com.github.one.actions.interfaces.listeners.device.IAndroidDeviceActionsListener;
import com.github.one.enums.ListenerType;
import com.github.one.enums.Message;
import com.github.one.enums.PlatformType;
import com.github.one.manager.ParallelSession;
import com.github.one.utils.ErrorHandler;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.apache.logging.log4j.Logger;

/**
 * Handles all Android device specific actions.
 *
 * @author Rahul Rana
 * @since 06-Jun-2023
 */
public class AndroidDeviceActions extends DeviceActions implements IAndroidDeviceActions {
    private static final IAndroidDeviceActions DEVICE_ACTIONS = new AndroidDeviceActions ();
    private static final Logger                LOGGER         = getLogger ();

    /**
     * Handles Android device specific actions.
     *
     * @return {@link IAndroidDeviceActions} instance
     */
    public static IAndroidDeviceActions onAndroidDevice () {
        return DEVICE_ACTIONS;
    }

    private final IAndroidDeviceActionsListener listener;

    private AndroidDeviceActions () {
        this.listener = ParallelSession.getSession ().getListener (ListenerType.ANDROID_DEVICE_ACTION);
    }

    @Override
    public void pressKey (final AndroidKey key) {
        LOGGER.traceEntry ();
        final var platform = ParallelSession.getSession ().getPlatformType ();
        if (platform != PlatformType.ANDROID) {
            ErrorHandler.throwError (Message.ACTION_NOT_SUPPORTED_ON_PLATFORM, "Press key", platform);
        }
        LOGGER.info ("Pressing key [{}] on Android device...", key);
        ofNullable (this.listener).ifPresent (l -> l.onPressKey (key));
        CommonActions.performDriverAction ((AndroidDriver d) -> d.pressKey (new KeyEvent ().withKey (key)));
        LOGGER.traceExit ();
    }
}
