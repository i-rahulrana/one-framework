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

package com.github.one.actions.elements;

import static com.github.one.actions.CommonActions.getDriverAttribute;
import static com.github.one.actions.CommonActions.performMobileGestures;
import static com.github.one.enums.ListenerType.FINGERS_ACTION;
import static com.github.one.enums.SwipeDirection.LEFT;
import static com.github.one.enums.SwipeDirection.RIGHT;
import static com.github.one.manager.ParallelSession.getSession;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static org.apache.logging.log4j.LogManager.getLogger;

import com.github.one.actions.interfaces.elements.IFingersActions;
import com.github.one.actions.interfaces.listeners.elements.IFingersActionsListener;
import com.github.one.builders.Locator;
import org.apache.logging.log4j.Logger;

/**
 * Handles all multi-fingers related actions
 *
 * @author Rahul Rana
 * @since 15-Feb-2023
 */
public class FingersActions extends FingerActions implements IFingersActions {
    private static final Logger LOGGER = getLogger ();

    /**
     * Handles all multi-fingers related actions
     *
     * @param locator Locator of the element
     *
     * @return {@link IFingersActions} instance object
     */
    public static IFingersActions withFingers (final Locator locator) {
        return new FingersActions (locator);
    }

    private final IFingersActionsListener listener;

    FingersActions (final Locator locator) {
        super (locator);
        this.listener = getSession ().getListener (FINGERS_ACTION);
    }

    @Override
    public void zoomIn () {
        LOGGER.traceEntry ();
        LOGGER.info ("Zooming in on the element [{}].", this.locator.getName ());
        ofNullable (this.listener).ifPresent (l -> l.onZoomIn (this.locator));
        final var finger1 = getDriverAttribute (driver -> FingerGestureBuilder.init ()
            .direction (LEFT)
            .name ("Finger 1")
            .sourceElement (this.locator)
            .offset (10)
            .build ()
            .swipe (), null);
        final var finger2 = getDriverAttribute (driver -> FingerGestureBuilder.init ()
            .direction (RIGHT)
            .name ("Finger 2")
            .sourceElement (this.locator)
            .offset (10)
            .build ()
            .swipe (), null);
        performMobileGestures (asList (finger1, finger2));
        LOGGER.traceExit ();
    }

    @Override
    public void zoomOut () {
        LOGGER.traceEntry ();
        LOGGER.info ("Zooming out on the element [{}].", this.locator.getName ());
        ofNullable (this.listener).ifPresent (l -> l.onZoomOut (this.locator));
        final var finger1 = getDriverAttribute (driver -> FingerGestureBuilder.init ()
            .direction (LEFT)
            .name ("Finger 1")
            .sourceElement (this.locator)
            .reverse (true)
            .offset (10)
            .build ()
            .swipe (), null);
        final var finger2 = getDriverAttribute (driver -> FingerGestureBuilder.init ()
            .direction (RIGHT)
            .name ("Finger 2")
            .sourceElement (this.locator)
            .reverse (true)
            .offset (10)
            .build ()
            .swipe (), null);
        performMobileGestures (asList (finger1, finger2));
        LOGGER.traceExit ();
    }
}
