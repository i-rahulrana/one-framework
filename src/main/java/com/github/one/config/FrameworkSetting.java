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

package com.github.one.config;

import static java.lang.String.join;
import static org.apache.logging.log4j.LogManager.getLogger;

import java.util.Map;

import com.github.one.enums.Message;
import com.github.one.utils.ErrorHandler;
import com.github.one.utils.Validator;
import com.github.one.config.api.ApiSetting;
import com.github.one.config.ui.UISetting;
import lombok.Data;
import org.apache.logging.log4j.Logger;

/**
 * Framework setting.
 *
 * @author Rahul Rana
 * @since 17-Feb-2022
 */
@Data
public class FrameworkSetting {
    private static final Logger LOGGER = getLogger ();

    private Map<String, ApiSetting> api;
    private String                  listenersPackage;
    private UISetting               ui;

    /**
     * Get API setting.
     *
     * @param key API config key
     *
     * @return {@link ApiSetting} instance
     */
    public ApiSetting getApiSetting (final String key) {
        LOGGER.traceEntry ("Key: {}", key);
        if (!this.api.containsKey (key)) {
            final var keys = join (", ", this.api.keySet ());
            ErrorHandler.throwError (Message.CONFIG_KEY_NOT_FOUND, key, keys);
        }
        return LOGGER.traceExit (Validator.requireNonNull (this.api.get (key), Message.NO_API_SETTINGS_FOUND, key));
    }
}
