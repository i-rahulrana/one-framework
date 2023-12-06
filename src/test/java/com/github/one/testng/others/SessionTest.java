/*
 * MIT License
 *
 * Copyright (c) 2022 Rahul Rana
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

package com.github.one.testng.others;

import static com.github.one.manager.ParallelSession.createSession;

import com.github.one.enums.PlatformType;
import com.github.one.exception.FrameworkError;
import com.github.one.manager.ParallelSession;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 * Test class for testing Parallel Session class.
 *
 * @author Rahul Rana
 * @since 18-Oct-2023
 */
public class SessionTest {
    private static final String PERSONA = "SessionTest";

    /**
     * Clear any open sessions.
     */
    @AfterMethod
    public void teardownMethod () {
        ParallelSession.clearAllSessions ();
    }

    /**
     * Test duplicate clear session.
     */
    @Test (description = "Test duplicate clear session", expectedExceptions = FrameworkError.class)
    public void testDuplicateClearSession () {
        ParallelSession.createSession (PERSONA, PlatformType.WEB, "test_local_chrome");
        ParallelSession.clearSession ();
        ParallelSession.clearSession ();
    }

    /**
     * Test Duplicate Session creation.
     */
    @Ignore
    @Test (description = "Test Duplicate Session creation", expectedExceptions = FrameworkError.class, expectedExceptionsMessageRegExp = "Session is already created for .SessionTest. persona...")
    public void testDuplicateSessionCreation () {
        ParallelSession.createSession (PERSONA, PlatformType.WEB, "test_local_chrome");
        ParallelSession.createSession (PERSONA, PlatformType.WEB, "test_local_chrome");
        ParallelSession.clearSession ();
    }

    /**
     * Test get session without session creation.
     */
    @Test (description = "Test get session without session creation", expectedExceptions = FrameworkError.class, expectedExceptionsMessageRegExp = "Session has not been created...")
    public void testGetSessionWithoutSessionCreated () {
        ParallelSession.getSession ();
    }

    /**
     * Test session creation with Null persona.
     */
    @Test (description = "Test session creation with Null persona", expectedExceptions = FrameworkError.class, expectedExceptionsMessageRegExp = "Session Persona cannot be empty or null...")
    public void testSessionCreationWithNullPersona () {
        ParallelSession.createSession (null, PlatformType.WEB, "test_local_chrome");
    }
}
