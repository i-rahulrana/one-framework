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

package com.github.one.utils;

import static com.github.one.utils.ErrorHandler.throwError;
import static java.lang.String.join;
import static java.lang.System.getProperty;
import static java.lang.System.getenv;
import static java.util.Optional.ofNullable;
import static org.apache.logging.log4j.LogManager.getLogger;

import java.nio.file.Path;
import java.util.Map;

import com.github.one.enums.Message;
import com.github.one.config.FrameworkSetting;
import org.apache.logging.log4j.Logger;

/**
 * Utility class to ready config JSON file.
 *
 * @author Rahul Rana
 * @since 17-Feb-2022
 */
public final class SettingUtils {
    private static final Logger LOGGER = getLogger ();

    private static FrameworkSetting frameworkSetting;

    /**
     * Gets the settings object from Map.
     *
     * @param settings Settings Map
     * @param key Setting key
     * @param <T> Setting object type
     *
     * @return Setting object
     */
    public static <T> T getSetting (final Map<String, T> settings, final String key) {
        LOGGER.traceEntry ("Key: {}", key);
        if (!settings.containsKey (key)) {
            final var keys = join (", ", settings.keySet ());
            throwError (Message.CONFIG_KEY_NOT_FOUND, key, keys);
        }
        return LOGGER.traceExit (settings.get (key));
    }

    /**
     * Loads the config JSON file only once.
     *
     * @return {@link FrameworkSetting}
     */
    public static FrameworkSetting loadSetting () {
        LOGGER.traceEntry ();
        if (frameworkSetting == null) {
            final var defaultPath = Path.of (getProperty ("user.dir"), "src/test/resources")
                .toString ();
            final var configDirectory = ofNullable (getenv ("ONE_CONFIG_PATH")).orElse (
                ofNullable (getProperty ("one.config.path")).orElse (defaultPath));
            final var configPath = Path.of (configDirectory, "one-config.json")
                .toString ();
            frameworkSetting = JsonUtil.fromFile (configPath, FrameworkSetting.class);
        }
        return LOGGER.traceExit (frameworkSetting);
    }

    private SettingUtils () {
        // Util class
    }
}
