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

import com.github.one.actions.api.ApiActions;
import com.github.one.builders.ApiRequest;
import com.github.one.enums.PlatformType;
import com.github.one.enums.RequestMethod;
import com.github.one.exception.FrameworkError;
import com.github.one.manager.ParallelSession;
import org.testng.annotations.Test;

/**
 * Other API related tests
 *
 * @author Rahul Rana
 * @since 27-Aug-2022
 */
public class ApiTests {
    /**
     * Tests invalid config key.
     */
    @Test (description = "Test Invalid API config key", expectedExceptions = FrameworkError.class)
    public void testInvalidApiConfigKey () {
        ParallelSession.createSession (PlatformType.API, "test_req");
        final var userRequest = ApiRequest.createRequest ().method (RequestMethod.GET)
            .path ("/users/${userId}")
            .pathParam ("userId", "2")
            .create ();
        ApiActions.withRequest (userRequest).execute ();
        ParallelSession.clearSession ();
    }
}
