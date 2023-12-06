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

import static java.text.MessageFormat.format;
import static org.openqa.selenium.By.linkText;

import com.github.one.builders.Locator;
import lombok.Getter;

/**
 * Home page.
 *
 * @author Rahul Rana
 * @since 13-Jul-2022
 */
@Getter
public class HomePage {
    private static final HomePage HOME_PAGE = new HomePage ();

    /**
     * Home page instance.
     *
     * @return {@link HomePage}
     */
    public static HomePage homePage () {
        return HOME_PAGE;
    }

    /**
     * Gets link locator based on the link name.
     *
     * @param linkText link name
     *
     * @return link locator
     */
    public Locator link (final String linkText) {
        return Locator.buildLocator ().web (linkText (linkText))
            .name (format ("Link [{0}]", linkText))
            .build ();
    }
}
