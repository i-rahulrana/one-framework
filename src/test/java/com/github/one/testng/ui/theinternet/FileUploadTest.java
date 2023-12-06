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
import static com.github.one.testng.ui.theinternet.pages.FileUploadPage.fileUploadPage;
import static java.lang.System.getProperty;

import java.nio.file.Path;

import com.github.one.actions.drivers.NavigateActions;
import com.github.one.actions.elements.ClickableActions;
import com.github.one.actions.elements.ElementActions;
import com.github.one.actions.elements.TextBoxActions;
import com.github.one.enums.PlatformType;
import com.github.one.manager.ParallelSession;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Test File Upload feature.
 *
 * @author Rahul Rana
 * @since 14-Mar-2023
 */
public class FileUploadTest {
    private static final String URL = "https://the-internet.herokuapp.com/upload";

    /**
     * Setup test class by initialising driver.
     *
     * @param platformType Application type
     * @param driverKey Driver config key
     */
    @BeforeClass (description = "Setup test class")
    @Parameters ({ "platformType", "driverKey" })
    public void setupClass (final PlatformType platformType, final String driverKey) {
        ParallelSession.createSession ("FileUploadTest", platformType, driverKey);
        NavigateActions.navigate ().to (URL);
    }

    /**
     * Tear down test class by closing driver.
     */
    @AfterClass (description = "Tear down test class")
    public void tearDownClass () {
        ParallelSession.clearSession ();
    }

    @Test
    public void testFileUpload () {
        final var filePath = Path.of (getProperty ("user.dir"), "src/test/resources/test-file.txt")
            .toFile ();
        TextBoxActions.onTextBox (fileUploadPage ().getFileUploadInput ()).enterText (filePath.getPath ());

        ClickableActions.withMouse (fileUploadPage ().getFileSubmit ()).click ();
        ElementActions.onElement (fileUploadPage ().getSuccessTitle ()).verifyText ()
            .isEqualTo ("File Uploaded!");
    }
}
