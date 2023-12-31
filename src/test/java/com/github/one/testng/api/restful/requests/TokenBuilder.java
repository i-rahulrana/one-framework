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

/**
 * Token builder class
 *
 * @author Rahul Rana
 * @since 28-Feb-2023
 */
public class TokenBuilder {
    public TokenCredential tokenBuilder () {
        return TokenCredential.builder ()
            .username ("admin")
            .password ("password123")
            .build ();
    }
}
