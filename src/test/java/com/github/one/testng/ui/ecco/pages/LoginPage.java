package com.github.one.testng.ui.ecco.pages;

import com.github.one.builders.Locator;

import static io.appium.java_client.AppiumBy.accessibilityId;
import static org.openqa.selenium.By.id;

public class LoginPage {
    private static final LoginPage LOGIN_PAGE = new LoginPage ();

    /**
     * Login page object.
     *
     * @return {@link LoginPage}
     */
    public static LoginPage loginPage () {
        return LOGIN_PAGE;
    }

    private final Locator loginButton = Locator.buildLocator ()
            .web (id ("login-button"))
            .android (accessibilityId ("test-LOGIN"))
            .ios (accessibilityId ("test-LOGIN"))
            .name ("Login Button")
            .build ();
    private final Locator password    = Locator.buildLocator ().web (id ("password"))
            .android (accessibilityId ("test-Password"))
            .ios (accessibilityId ("test-Password"))
            .name ("Password")
            .build ();
    private final Locator username    = Locator.buildLocator ().web (id ("user-name"))
            .android (accessibilityId ("test-Username"))
            .ios (accessibilityId ("test-Username"))
            .name ("User Name")
            .build ();

    private LoginPage () {
        // Avoid explicit class initialisation.
    }
}
