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

package com.github.one.actions.drivers;

import static java.util.Optional.ofNullable;
import static org.apache.logging.log4j.LogManager.getLogger;

import com.github.one.actions.CommonActions;
import com.github.one.actions.interfaces.drivers.IFrameActions;
import com.github.one.actions.interfaces.listeners.drivers.IFrameActionsListener;
import com.github.one.enums.ListenerType;
import com.github.one.manager.ParallelSession;
import org.apache.logging.log4j.Logger;

/**
 * Handle all frame related actions
 *
 * @author Rahul Rana
 * @since 16-Feb-2023
 */
public class FrameActions implements IFrameActions {
    private static final IFrameActions FRAME_ACTIONS = new FrameActions ();
    private static final Logger        LOGGER        = getLogger ();

    /**
     * Handles all frames related actions
     *
     * @return {@link IFrameActions} instance object
     */
    public static IFrameActions onFrame () {
        return FRAME_ACTIONS;
    }

    private final IFrameActionsListener listener;

    private FrameActions () {
        this.listener = ParallelSession.getSession ().getListener (ListenerType.FRAME_ACTION);
    }

    @Override
    public void switchTo (final String frameName) {
        LOGGER.traceEntry ();
        LOGGER.info ("Switching to frame: {}", frameName);
        ofNullable (this.listener).ifPresent (l -> l.onSwitchTo (frameName));
        CommonActions.performDriverAction (driver -> driver.switchTo ()
            .frame (frameName));
        LOGGER.traceExit ();
    }

    @Override
    public void switchTo (final int index) {
        LOGGER.traceEntry ();
        LOGGER.info ("Switching to frame index: {}", index);
        ofNullable (this.listener).ifPresent (l -> l.onSwitchTo (index));
        CommonActions.performDriverAction (driver -> driver.switchTo ()
            .frame (index));
        LOGGER.traceExit ();
    }

    @Override
    public void switchToParent () {
        LOGGER.traceEntry ();
        LOGGER.info ("Switching to main frame...");
        ofNullable (this.listener).ifPresent (IFrameActionsListener::onSwitchToParent);
        CommonActions.performDriverAction (driver -> driver.switchTo ()
            .parentFrame ());
        LOGGER.traceExit ();
    }
}
