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
import static com.github.one.testng.ui.theinternet.pages.DoubleClickPage.doubleClickPage;

import com.github.one.actions.drivers.NavigateActions;
import com.github.one.actions.elements.ClickableActions;
import com.github.one.actions.elements.ElementActions;
import com.github.one.enums.PlatformType;
import com.github.one.manager.ParallelSession;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Drag and drop test.
 *
 * @author Rahul Rana
 * @since 26-Jul-2022
 */
public class DoubleClickTest {
    private static final String URL = "https://webdriveruniversity.com/Actions/index.html";

    /**
     * Setup test class by initialising driver.
     *
     * @param platformType Application type
     * @param driverKey Driver config key
     */
    @BeforeClass (description = "Setup test class")
    @Parameters ({ "platformType", "driverKey" })
    public void setupClass (final PlatformType platformType, final String driverKey) {
        ParallelSession.createSession ("DoubleClickTest", platformType, driverKey);
        NavigateActions.navigate ().to (URL);
    }

    /**
     * Tear down test class by closing driver.
     */
    @AfterClass (description = "Tear down test class")
    public void tearDownClass () {
        ParallelSession.clearSession ();
    }

    /**
     * Test click and hold method.
     */
    @Test (description = "Verify Click and Hold method")
    public void testClickAndHold () {
        ClickableActions.withMouse (doubleClickPage ().getClickHold ()).clickAndHold ();
        ElementActions.onElement (doubleClickPage ().getClickHold ()).verifyText ()
            .isEqualTo ("Well done! keep holding that click now.....");
    }

    /**
     * Double click test.
     */
    @Test (description = "Double click test")
    public void testDoubleClick () {
        ClickableActions.withMouse (doubleClickPage ().getDoubleClick ()).doubleClick ();
        ElementActions.onElement (doubleClickPage ().getDoubleClick ()).verifyStyle ("background-color")
            .isEqualTo ("rgba(147, 203, 90, 1)");
    }

    /**
     * Test hover and click method.
     */
    @Test (description = "Verify Click and Hold method")
    public void testHoverAndClick () {
        ClickableActions.withMouse (doubleClickPage ().getHoverButton ()).hover ();
        ClickableActions.withMouse (doubleClickPage ().getHoverMenu ()).click ();
        onAlert ().verifyAccept ()
            .isEqualTo ("Well done you clicked on the link!");
    }
}
