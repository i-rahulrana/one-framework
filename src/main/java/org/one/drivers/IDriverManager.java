package org.one.drivers;

import io.appium.java_client.AppiumDriver;

public interface IDriverManager<D extends AppiumDriver> {
    D getDriver ();
}
