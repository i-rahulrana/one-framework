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

package com.github.one.testng.ui.theinternet;

import static com.github.one.manager.ParallelSession.createSession;
import static com.github.one.testng.ui.theinternet.pages.LoginPage.loginPage;

import com.github.one.actions.device.DeviceActions;
import com.github.one.actions.drivers.NavigateActions;
import com.github.one.actions.drivers.WindowActions;
import com.github.one.actions.elements.ClickableActions;
import com.github.one.actions.elements.ElementActions;
import com.github.one.actions.elements.TextBoxActions;
import com.github.one.enums.PlatformType;
import com.github.one.manager.ParallelSession;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Login Test class.
 *
 * @author Rahul Rana
 * @since 16-Sept-2023
 */
public class LoginTest {
    private static final String URL = "https://the-internet.herokuapp.com/login";

    private PlatformType platformType;

    /**
     * Setup test method to take screenshot after each test method.
     */
    @AfterMethod (alwaysRun = true)
    public void afterMethod (final ITestResult result) {
        if (!result.isSuccess ()) {
            WindowActions.onWindow ().takeScreenshot ();
        }
    }

    /**
     * Setup test class by initialising driver.
     *
     * @param platformType Application type
     * @param driverKey Driver config key
     */
    @BeforeClass (description = "Setup test class")
    @Parameters ({ "platformType", "driverKey" })
    public void setupClass (final PlatformType platformType, final String driverKey) {
        ParallelSession.createSession ("LoginTest", platformType, driverKey);
        this.platformType = platformType;
        DeviceActions.onDevice ().startRecording ();
        NavigateActions.navigate ().to (URL);
    }

    /**
     * Tear down test class by closing driver.
     */
    @AfterClass (description = "Tear down test class")
    public void tearDownClass () {
        DeviceActions.onDevice ().stopRecording ();
        ParallelSession.clearSession ();
    }

    @Test (description = "Test Login Flow")
    public void testLogin () {
        TextBoxActions.onTextBox (loginPage ().getUserName ()).enterText ("tomsmith");
        TextBoxActions.onTextBox (loginPage ().getPassword ()).enterText ("SuperSecretPassword!");
        ClickableActions.withMouse (loginPage ().getLogin ()).click ();
        ElementActions.onElement (loginPage ().getMessage ()).verifyText ()
            .contains ("You logged into a secure area!");
        ClickableActions.withMouse (loginPage ().getLogout ()).click ();
        ElementActions.onElement (loginPage ().getMessage ()).verifyText ()
            .contains ("You logged out of the secure area!");
    }
}
