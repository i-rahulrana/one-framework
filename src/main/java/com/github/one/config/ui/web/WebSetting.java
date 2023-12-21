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

package com.github.one.config.ui.web;

import java.util.List;
import java.util.Map;

import com.github.one.enums.Browser;
import com.github.one.enums.Protocol;
import com.github.one.enums.TargetProviders;
import com.github.one.enums.WindowResizeType;
import com.github.one.utils.StringUtils;
import lombok.Data;
import org.openqa.selenium.Dimension;

/**
 * Web settings.
 *
 * @author Rahul Rana
 * @since 24-Feb-2022
 */
@Data
public class WebSetting {
    private String              baseUrl;
    private Browser browser    = Browser.NONE;
    private List<String>        browserOptions;
    private Map<String, Object> capabilities;
    private Dimension           customSize = new Dimension (1920, 1080);
    private boolean             headless   = true;
    private boolean             highlight  = false;
    private String              host;
    private String              password;
    private String              platform;
    private int                 port;
    private Protocol protocol   = Protocol.HTTP;
    private WindowResizeType resize     = WindowResizeType.NORMAL;
    private TargetProviders target;
    private String              userName;

    /**
     * Gets cloud password.
     *
     * @return the cloud password
     */
    public String getPassword () {
        return StringUtils.interpolate (this.password);
    }

    /**
     * Gets cloud user name.
     *
     * @return the cloud username.
     */
    public String getUserName () {
        return StringUtils.interpolate (this.userName);
    }
}
