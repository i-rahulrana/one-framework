package com.github.one.testng.ui.wdio;

import static com.github.one.actions.device.DeviceActions.onDevice;
import static com.github.one.actions.elements.FingerActions.withFinger;
import static com.github.one.manager.ParallelSession.createSession;
import static com.github.one.testng.ui.wdio.pages.AlertDialog.alertDialog;
import static com.github.one.testng.ui.wdio.pages.DragDropPage.dragDropPage;
import static com.github.one.testng.ui.wdio.pages.SignUpPage.signUpPage;
import static com.github.one.testng.ui.wdio.pages.WDIOHomePage.wdioHomePage;
import static com.github.one.testng.ui.wdio.pages.WebViewPage.webViewPage;
import static java.text.MessageFormat.format;

import com.github.one.actions.drivers.ContextActions;
import com.github.one.actions.drivers.DriverActions;
import com.github.one.actions.drivers.WindowActions;
import com.github.one.actions.elements.ClickableActions;
import com.github.one.actions.elements.ElementActions;
import com.github.one.actions.elements.FingerActions;
import com.github.one.actions.elements.TextBoxActions;
import com.github.one.enums.PlatformType;
import com.github.one.manager.ParallelSession;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Test class for testing different Mobile gestures.
 *
 * @author Rahul Rana
 * @since 24-Dec-2022
 */
public class WdioDemoTest {
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
        ParallelSession.createSession (format ("WdioDemoTest-{0}", platformType), platformType, driverKey);
        onDevice ().startRecording ();
    }

    /**
     * Tear down test class by closing driver.
     */
    @AfterClass (description = "Tear down test class", alwaysRun = true)
    public void tearDownTestClass () {
        onDevice ().stopRecording ();
        DriverActions.withDriver ().saveLogs ();
        ParallelSession.clearSession ();
    }

    /**
     * Test Drag drop
     */
    @Test
    public void testDragDrop () {
        FingerActions.withFinger (wdioHomePage ().getDragTab ()).tap ();
        for (var index = 1; index <= 3; index++) {
            FingerActions.withFinger (dragDropPage ().leftSourceTile (index)).dragTo (dragDropPage ().leftTargetTile (index));
            FingerActions.withFinger (dragDropPage ().centerSourceTile (index)).dragTo (dragDropPage ().centerTargetTile (index));
            FingerActions.withFinger (dragDropPage ().rightSourceTile (index)).dragTo (dragDropPage ().rightTargetTile (index));
        }
        ElementActions.onElement (dragDropPage ().getTitle ()).verifyText ()
            .isEqualTo ("Congratulations");
        ElementActions.onElement (dragDropPage ().getDescription ()).verifyText ()
            .isEqualTo ("You made it, click retry if you want to try it again.");
    }

    /**
     * Sign Up test.
     */
    @Test
    public void testSignUp () {
        final var userName = "admin@gmail.com";
        final var password = "Admin@1234";
        FingerActions.withFinger (wdioHomePage ().getLoginTab ()).tap ();

        FingerActions.withFinger (signUpPage ().getSignUpTab ()).tap ();
        TextBoxActions.onTextBox (signUpPage ().getEmail ()).enterText (userName);
        TextBoxActions.onTextBox (signUpPage ().getPassword ()).enterText (password);
        TextBoxActions.onTextBox (signUpPage ().getConfirmPassword ()).enterText (password);

        if (ParallelSession.getSession ().getPlatformType () == PlatformType.ANDROID) {
            onDevice ().hideKeyboard ();
        }

        FingerActions.withFinger (signUpPage ().getSignUp ()).tap ();

        alertDialog ().verifyMessage ("You successfully signed up!");
    }

    /**
     * Test web view screen.
     */
    @Test (description = "Test WebView screen")
    public void testWebView () {
        ClickableActions.withMouse (wdioHomePage ().getWebViewTab ()).click ();

        verifyWebView ();
    }

    private void verifyWebView () {
        ContextActions.withContext ().switchToWebView ();

        ElementActions.onElement (webViewPage ().getPageTitle ()).verifyText ()
            .isEqualTo ("Next-gen browser and mobile automation test framework for Node.js");

        ContextActions.withContext ().switchToNative ();

        ElementActions.onElement (wdioHomePage ().getWebViewTab ()).verifyIsDisplayed ()
            .isTrue ();
    }
}
