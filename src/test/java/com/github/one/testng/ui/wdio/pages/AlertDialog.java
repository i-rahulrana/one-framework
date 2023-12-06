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

package com.github.one.testng.ui.wdio.pages;

import static com.github.one.actions.drivers.AlertActions.onAlert;
import static com.github.one.actions.elements.FingerActions.withFinger;
import static io.appium.java_client.AppiumBy.id;

import com.github.one.actions.elements.ElementActions;
import com.github.one.actions.elements.FingerActions;
import com.github.one.builders.Locator;
import com.github.one.enums.PlatformType;
import com.github.one.manager.ParallelSession;
import lombok.Getter;

/**
 * Alert dialog pop-up.
 *
 * @author Rahul Rana
 * @since 21-Oct-2023
 */
@Getter
public class AlertDialog {
    private static final AlertDialog ALERT_DIALOG = new AlertDialog ();

    /**
     * Alert dialog pop-up instance.
     *
     * @return {@link AlertDialog}
     */
    public static AlertDialog alertDialog () {
        return ALERT_DIALOG;
    }

    private final Locator button1 = Locator.buildLocator ()
        .name ("Button 1")
        .android (id ("android:id/button1"))
        .build ();
    private final Locator message = Locator.buildLocator ()
        .name ("Message")
        .android (id ("android:id/message"))
        .build ();
    private final Locator title   = Locator.buildLocator ()
        .name ("Title")
        .android (id ("android:id/alertTitle"))
        .build ();

    private AlertDialog () {
        // Page class.
    }

    /**
     * Accepts pop-up and verify message.
     */
    public void verifyMessage (final String expectedMessage) {
        if (ParallelSession.getSession ().getPlatformType () == PlatformType.ANDROID) {
            try {
                ElementActions.onElement (getMessage ()).verifyText ()
                    .isEqualTo (expectedMessage);
            } finally {
                FingerActions.withFinger (getButton1 ()).tap ();
            }
        } else {
            onAlert ().verifyAccept (expectedMessage);
        }
    }
}
