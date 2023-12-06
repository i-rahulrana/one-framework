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

import static com.github.one.actions.drivers.AlertActions.onAlert;
import static com.github.one.manager.ParallelSession.createSession;
import static com.github.one.testng.ui.theinternet.pages.AlertPage.alertPage;
import static com.github.one.testng.ui.theinternet.pages.HomePage.homePage;

import com.github.one.actions.drivers.NavigateActions;
import com.github.one.actions.drivers.WindowActions;
import com.github.one.actions.elements.ClickableActions;
import com.github.one.actions.elements.ElementActions;
import com.github.one.enums.PlatformType;
import com.github.one.manager.ParallelSession;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * This will test all Web related actions.
 *
 * @author Rahul Rana
 * @since 12-Jul-2022
 */
public class AlertsTest {
    private static final String URL = "https://the-internet.herokuapp.com/";

    /**
     * Setup test class by initialising driver.
     *
     * @param platformType Application type
     * @param driverKey Driver config key
     */
    @BeforeClass (description = "Setup test class")
    @Parameters ({ "platformType", "driverKey" })
    public void setupClass (final PlatformType platformType, final String driverKey) {
        ParallelSession.createSession ("AlertsTest", platformType, driverKey);
        WindowActions.onWindow ().fullScreen ();
        NavigateActions.navigate ().to (URL);
        ClickableActions.withMouse (homePage ().link ("JavaScript Alerts")).click ();
    }

    /**
     * Tear down test class by closing driver.
     */
    @AfterClass (description = "Tear down test class")
    public void tearDownClass () {
        NavigateActions.navigate ().back ();
        NavigateActions.navigate ().verifyUrl ()
            .isEqualTo (URL);
        ParallelSession.clearSession ();
    }

    /**
     * This will test Accept alert button action.
     */
    @Test (description = "Tests Accept alert")
    public void testAcceptAlert () {
        ClickableActions.withMouse (alertPage ().getAlertButton ()).click ();
        onAlert ().verifyAccept ()
            .isEqualTo ("I am a JS Alert");
        ElementActions.onElement (alertPage ().getResult ()).verifyText ()
            .isEqualTo ("You successfully clicked an alert");
    }

    /**
     * This will test accept confirm button action.
     */
    @Test (description = "Tests Accept confirm alert")
    public void testAcceptConfirmAlert () {
        ClickableActions.withMouse (alertPage ().getConfirmButton ()).click ();
        onAlert ().verifyAccept ()
            .isEqualTo ("I am a JS Confirm");
        ElementActions.onElement (alertPage ().getResult ()).verifyText ()
            .isEqualTo ("You clicked: Ok");
    }

    /**
     * This will test dismiss confirm button action.
     */
    @Test (description = "Tests Dismiss confirm alert")
    public void testDismissConfirmAlert () {
        ClickableActions.withMouse (alertPage ().getConfirmButton ()).click ();
        onAlert ().verifyDismiss ()
            .isEqualTo ("I am a JS Confirm");
        ElementActions.onElement (alertPage ().getResult ()).verifyText ()
            .isEqualTo ("You clicked: Cancel");
    }

    /**
     * This will test dismiss confirm button action.
     */
    @Test (description = "Tests Dismiss prompt alert")
    public void testDismissPromptAlert () {
        ClickableActions.withMouse (alertPage ().getPromptButton ()).click ();
        onAlert ().verifyAccept ("Wasiq")
            .isEqualTo ("I am a JS prompt");
        ElementActions.onElement (alertPage ().getResult ()).verifyText ()
            .isEqualTo ("You entered: Wasiq");
    }
}
