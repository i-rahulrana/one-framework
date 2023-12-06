package com.github.one.testng.ui.ecco.tests;

import com.github.one.actions.device.DeviceActions;
import com.github.one.actions.drivers.DriverActions;
import com.github.one.actions.drivers.WindowActions;
import com.github.one.enums.PlatformType;
import com.github.one.manager.ParallelSession;
import com.github.one.testng.ui.ecco.actions.LoginActions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import static java.text.MessageFormat.format;

public class LoginTests {
    private LoginActions login;

    /**
     * Setup test method to take screenshot after each test method.
     */
    @AfterMethod (alwaysRun = true)
    public void afterMethod (final ITestResult result) {
        if (!result.isSuccess ()) {
            WindowActions.onWindow ().takeScreenshot ();
        }
    }

    /**
     * Setup test class by initializing driver.
     *
     * @param platformType Application type
     * @param driverKey Driver config key
     */
    @BeforeClass (description = "Setup test class", alwaysRun = true)
    @Parameters ({ "platformType", "driverKey" })
    public void setupTestClass (final PlatformType platformType, final String driverKey) {
        ParallelSession.createSession (format ("SauceDemoTest-{0}", platformType), platformType, driverKey);
        DeviceActions.onDevice ().startRecording ();
        this.login = new LoginActions ();
    }

    /**
     * Tear down test class by closing driver.
     */
    @AfterClass (description = "Tear down test class", alwaysRun = true)
    public void tearDownTestClass () {
        DeviceActions.onDevice ().stopRecording ();
        DriverActions.withDriver ().saveLogs ();
        ParallelSession.clearSession ();
    }

    /**
     * Test login to app functionality.
     */
    @Test(description = "Test adding a product to cart")
    public void testLoginToApp() {

    }
}
