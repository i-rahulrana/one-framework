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
import static com.github.one.testng.ui.theinternet.pages.HomePage.homePage;
import static com.github.one.testng.ui.theinternet.pages.MultiWindowPage.multiWindowPage;
import static com.google.common.truth.Truth.assertThat;
import static java.text.MessageFormat.format;
import static org.openqa.selenium.WindowType.TAB;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlMatches;

import com.github.one.actions.drivers.DriverActions;
import com.github.one.actions.drivers.NavigateActions;
import com.github.one.actions.drivers.WindowActions;
import com.github.one.actions.elements.ClickableActions;
import com.github.one.actions.elements.ElementActions;
import com.github.one.enums.PlatformType;
import com.github.one.manager.ParallelSession;
import com.google.common.truth.Truth;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Browser window specific tests.
 *
 * @author Rahul Rana
 * @since 16-Jul-2022
 */
public class WindowTest {
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
        ParallelSession.createSession ("WindowTest", platformType, driverKey);
        WindowActions.onWindow ().maximize ();
        NavigateActions.navigate ().to (URL);
        ClickableActions.withMouse (homePage ().link ("Multiple Windows")).click ();
    }

    /**
     * Tear down test class by closing driver.
     */
    @AfterClass (description = "Tear down test class")
    public void tearDownClass () {
        ParallelSession.clearSession ();
    }

    /**
     * Test case to verify browser back navigation.
     */
    @Test (description = "Test browser back navigation")
    public void testBackNavigation () {
        NavigateActions.navigate ().back ();
        NavigateActions.navigate ().verifyUrl ()
            .isEqualTo (URL);
    }

    /**
     * Test case to verify execute script.
     */
    @Test (description = "Test execute script method")
    public void testExecuteScript () {
        final String script = "alert('Hello World');";
        DriverActions.withDriver ().executeScript (script);
        onAlert ().verifyAccept ()
            .isEqualTo ("Hello World");
    }

    /**
     * Test case to verify browser forward navigation.
     */
    @Test (description = "Test browser forward navigation")
    public void testForwardNavigation () {
        NavigateActions.navigate ().forward ();
        NavigateActions.navigate ().verifyUrl ()
            .isEqualTo (format ("{0}windows", URL));
    }

    /**
     * Test case to verify opening new tab window.
     */
    @Test (description = "Test to verify opening new tab")
    public void testOpenNewTab () {
        try {
            WindowActions.onWindow ().switchToNew (TAB);
            WindowActions.onWindow ().verifyTitle ()
                .isEmpty ();
            NavigateActions.navigate ().verifyUrl ()
                .isEqualTo ("about:blank");
        } finally {
            WindowActions.onWindow ().close ();
        }
    }

    /**
     * Test case to verify opening new window.
     */
    @Test
    public void testOpenWindow () {
        final var currentWindow = WindowActions.onWindow ().currentHandle ();
        ClickableActions.withMouse (multiWindowPage ().getClickHere ()).click ();
        final var newWindow = WindowActions.onWindow ().handles ()
            .stream ()
            .filter (handle -> !handle.equals (currentWindow))
            .findFirst ();
        Truth.assertThat (newWindow.isPresent ()).isTrue ();
        WindowActions.onWindow ().switchTo (newWindow.get ());
        DriverActions.withDriver ().waitUntil (urlMatches (format ("{0}windows/new", URL)));
        NavigateActions.navigate ().verifyUrl ()
            .isEqualTo (format ("{0}windows/new", URL));
        ElementActions.onElement (multiWindowPage ().getTitle ()).verifyText ()
            .isEqualTo ("New Window");
        WindowActions.onWindow ().close ();
        NavigateActions.navigate ().verifyUrl ()
            .isEqualTo (format ("{0}windows", URL));
    }

    /**
     * Test case to verify browser refresh.
     */
    @Test (description = "Test to verify page refresh")
    public void testRefreshPage () {
        NavigateActions.navigate ().refresh ();
        NavigateActions.navigate ().verifyUrl ()
            .isEqualTo (format ("{0}windows", URL));
    }
}
