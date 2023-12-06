package com.github.one.config.ui.mobile.device;

import lombok.Data;

/**
 * Swipe related settings
 *
 * @author Rahul Rana
 * @since 02-Jan-2023
 */
@Data
public class SwipeSetting {
    private int distance           = 75;
    private int maxSwipeUntilFound = 5;
}
