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

package com.github.one.actions.interfaces.elements;

/**
 * All Clickable actions
 *
 * @author Rahul Rana
 * @since 15-Feb-2023
 */
public interface IClickableActions extends IFingersActions {
    /**
     * Click on element
     */
    void click ();

    /**
     * LongPress on element
     */
    void clickAndHold ();

    /**
     * DoubleClick on element
     */
    void doubleClick ();

    /**
     * Hover on element
     */
    void hover ();

    /**
     * RightClick on element
     */
    void rightClick ();

    /**
     * Submit the element.
     */
    void submit ();
}
