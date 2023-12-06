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

package com.github.one.testng.api.postman;

import static com.github.one.manager.ParallelSession.createSession;

import com.github.one.actions.api.ApiActions;
import com.github.one.builders.ApiRequest;
import com.github.one.enums.ContentType;
import com.github.one.enums.PlatformType;
import com.github.one.enums.RequestMethod;
import com.github.one.manager.ParallelSession;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Test class to test Postman related samples.
 *
 * @author Rahul Rana
 * @since 09-Mar-2023
 */
public class ApiPostmanTest {
    /**
     * Setup API Test.
     */
    @BeforeClass (description = "Setup test class", alwaysRun = true)
    public void setupTestClass () {
        ParallelSession.createSession (PlatformType.API, "test_postman");
    }

    /**
     * Clean up Test class.
     */
    @AfterClass (description = "Tear down test class", alwaysRun = true)
    public void tearDownTestClass () {
        ParallelSession.clearSession ();
    }

    /**
     * Test form data request body related API request.
     */
    @Test (description = "Test Form body POST request")
    public void testFormBodyRequest () {
        final var request = ApiRequest.createRequest ()
            .contentType (ContentType.FORM_URLENCODED)
            .formBody ("strange", "boom")
            .formBody ("test", "abc")
            .method (RequestMethod.POST)
            .path ("/post")
            .create ();

        final var response = ApiActions.withRequest (request)
            .execute ();
        response.verifyStatusCode ()
            .isEqualTo (200);
    }
}
