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

package com.github.one.manager;

import static java.lang.System.getProperty;
import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.github.one.actions.drivers.NavigateActions;
import com.github.one.config.ui.mobile.device.ApplicationSetting;
import com.github.one.enums.ApplicationType;
import com.github.one.enums.TargetProviders;
import com.github.one.utils.StringUtils;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.remote.options.SupportsAppOption;
import org.openqa.selenium.MutableCapabilities;

interface IDriverManager {
    default void navigateToBaseUrl (final ApplicationSetting application) {
        if (application.getType () == ApplicationType.WEB && isNotEmpty (application.getBaseUrl ())) {
            NavigateActions.navigate ().to (application.getBaseUrl ());
        }
    }

    default <E extends BaseOptions<E>, T extends SupportsAppOption<E>> void setupApplicationOptions (
        final ApplicationSetting application, final T options) {
        if (isNotEmpty (application.getPath ())) {
            if (!application.isExternal ()) {
                options.setApp (Path.of (getProperty ("user.dir"), "/src/test/resources", application.getPath ())
                    .toString ());
            } else {
                options.setApp (StringUtils.interpolate (application.getPath ()));
            }
        }
    }

    default <E extends MutableCapabilities> void setupCloudDriverOptions (final E options,
        final Map<String, Object> capabilities, final TargetProviders targetProviders) {
        if (capabilities != null && targetProviders != TargetProviders.LOCAL) {
            final var optionCapabilities = new HashMap<String, Object> ();
            capabilities.forEach ((k, v) -> {
                if (v instanceof String) {
                    optionCapabilities.put (k, StringUtils.interpolate (v.toString ()));
                } else {
                    optionCapabilities.put (k, v);
                }
            });
            options.setCapability (format ("{0}:options", targetProviders.getPrefix ()), optionCapabilities);
        }
    }

    void setupDriver ();
}
