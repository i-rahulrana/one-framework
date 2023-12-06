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

import java.io.IOException;

import com.github.one.enums.Message;
import com.github.one.exception.FrameworkError;
import com.github.one.utils.ErrorHandler;
import org.testng.annotations.Test;

/**
 * Error handling test.
 *
 * @author Rahul Rana
 * @since 28-Jul-2022
 */
public class ErrorHandlingTest {
    /**
     * Test error handling.
     */
    @Test (expectedExceptions = FrameworkError.class, expectedExceptionsMessageRegExp = "Test error...")
    public void testFrameworkError () {
        ErrorHandler.handleAndThrow (Message.TEST_ERROR, new IOException ("File not found"));
    }
}
