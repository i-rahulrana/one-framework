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

package com.github.one.testng.api.restful.requests;

import static java.text.MessageFormat.format;

import com.github.one.actions.api.ApiActions;
import com.github.one.builders.ApiRequest;
import com.github.one.enums.RequestMethod;

/**
 * Booking request class
 *
 * @author Rahul Rana
 * @since 28-Feb-2023
 */
public final class BookingRequest {
    public static ApiRequest createBooking (final Object requestBody) {
        return ApiRequest.createRequest ().method (RequestMethod.POST)
            .header ("Accept", "application/json")
            .path ("/booking")
            .bodyObject (requestBody)
            .create ();
    }

    public static ApiRequest deleteBooking (final String id) {
        return ApiRequest.createRequest ().method (RequestMethod.DELETE)
            .header ("Content-Type", "application/json")
            .header ("Cookie", format ("token={0}", generateToken ()))
            .path ("/booking/${id}")
            .pathParam ("id", id)
            .create ();
    }

    public static ApiRequest getBooking (final String id) {
        return ApiRequest.createRequest ().method (RequestMethod.GET)
            .header ("Accept", "application/json")
            .path ("/booking/${id}")
            .pathParam ("id", id)
            .create ();
    }

    public static ApiRequest updateBooking (final String id, final Object requestBody) {
        return ApiRequest.createRequest ().method (RequestMethod.PUT)
            .header ("Accept", "application/json")
            .header ("Cookie", format ("token={0}", generateToken ()))
            .path ("/booking/${id}")
            .bodyObject (requestBody)
            .pathParam ("id", id)
            .create ();
    }

    public static ApiRequest updatePartialBooking (final String id, final Object requestBody) {
        return ApiRequest.createRequest ().method (RequestMethod.PATCH)
            .header ("Accept", "application/json")
            .header ("Cookie", format ("token={0}", generateToken ()))
            .path ("/booking/${id}")
            .bodyObject (requestBody)
            .pathParam ("id", id)
            .create ();
    }

    private static String generateToken () {
        final TokenBuilder builder = new TokenBuilder ();
        final var generateTokenRequest = ApiRequest.createRequest ().header ("Accept", "application/json")
            .method (RequestMethod.POST)
            .path ("/auth")
            .bodyObject (builder.tokenBuilder ())
            .create ();

        final var response = ApiActions.withRequest (generateTokenRequest).execute ();
        return response.getResponseData ("token");
    }

    private BookingRequest () {
        // Utility class.
    }
}
