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
import static com.github.one.testng.ui.theinternet.pages.HomePage.homePage;
import static com.google.common.truth.Truth.assertThat;

import com.github.one.actions.drivers.CookieActions;
import com.github.one.actions.drivers.NavigateActions;
import com.github.one.actions.drivers.WindowActions;
import com.github.one.actions.elements.ClickableActions;
import com.github.one.enums.PlatformType;
import com.github.one.manager.ParallelSession;
import com.google.common.truth.Truth;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Cookies test.
 *
 * @author Rahul Rana
 * @since 15-Jul-2022
 */
public class CookiesTest {
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
        ParallelSession.createSession ("CookiesTest", platformType, driverKey);
        WindowActions.onWindow ().minimize ();
        NavigateActions.navigate ().to (URL);
        ClickableActions.withMouse (homePage ().link ("JavaScript Alerts")).click ();
    }

    /**
     * Tear down test class by closing driver.
     */
    @AfterClass (description = "Tear down test class")
    public void tearDownClass () {
        ParallelSession.clearSession ();
    }

    /**
     * Test delete all cookies.
     */
    @Test (description = "Verify delete all cookies", priority = 3)
    public void testDeleteAllCookies () {
        CookieActions.withCookies ().deleteAll ();
        Truth.assertThat (CookieActions.withCookies ().cookies ()
            .size ()).isEqualTo (0);
    }

    /**
     * Test delete a single cookie.
     */
    @Test (description = "Tests delete of single cookie", priority = 2)
    public void testDeleteSingleCookie () {
        final var cookies = CookieActions.withCookies ().cookies ();
        final var cookieCount = cookies.size ();
        CookieActions.withCookies ().delete (cookies.get (0));
        Truth.assertThat (CookieActions.withCookies ().cookies ()
            .size ()).isEqualTo (cookieCount - 1);
    }

    /**
     * Test get cookie.
     */
    @Test (description = "Test get cookie", priority = 1)
    public void testGetCookie () {
        final var cookie = CookieActions.withCookies ().cookies ()
            .get (0);
        Truth.assertThat (CookieActions.withCookies ().cookie (cookie)
            .getName ()).isEqualTo (cookie);
    }
}
