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

package com.github.one.testng.ui.theinternet.pages;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.id;

import com.github.one.builders.Locator;
import lombok.Getter;

/**
 * Alert page object.
 *
 * @author Rahul Rana
 * @since 14-Jul-2022
 */
@Getter
public class AlertPage {
    private static final AlertPage ALERT_PAGE = new AlertPage ();

    /**
     * Alert page instance.
     *
     * @return {@link AlertPage}
     */
    public static AlertPage alertPage () {
        return ALERT_PAGE;
    }

    private final Locator alertButton   = Locator.buildLocator ().web (cssSelector ("ul > li > button"))
        .name ("Alert button")
        .index (0)
        .build ();
    private final Locator confirmButton = Locator.buildLocator ().web (cssSelector ("ul > li > button"))
        .name ("Confirm Button")
        .index (1)
        .build ();
    private final Locator promptButton  = Locator.buildLocator ().web (cssSelector ("ul > li > button"))
        .name ("Prompt Button")
        .index (2)
        .build ();
    private final Locator result        = Locator.buildLocator ().web (id ("result"))
        .name ("Result")
        .build ();
}
