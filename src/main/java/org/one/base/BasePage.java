package org.one.base;

import com.aventstack.extentreports.Status;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.one.drivers.DriverManager;
import org.one.utils.FingerGestureUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;

public class BasePage {
    public static Logger LOG = LogManager.getLogger (BasePage.class);
    // Declare appium driver
    public RemoteWebDriver driver;

    public BasePage () {
        this.driver = BaseTest.getDriver ();
    }

    /**
     * @return - WebElement
     * @Method - findElement
     * @Description - find element By locator
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public WebElement findElement (By locator) {
        return driver.findElement (locator);
    }

    /**
     * @return - List<WebElement>
     * @Method - findElements
     * @Description - find list of elements By locator
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public List<WebElement> findElements (By locator) {
        return driver.findElements (locator);
    }

    /**
     * @return - void
     * @Method - waitForElementToClickable
     * @Description - wait for findBy element to clickable
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void waitForVisibility (WebElement element) {
        WebDriverWait wait = new WebDriverWait (driver, Duration.ofSeconds (DriverManager.getExplicitWaitDuration ()));
        wait.until (ExpectedConditions.visibilityOf (element));
    }

    /**
     * @return - void
     * @Method - waitForElementToClickable
     * @Description - wait for findBy element to clickable
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void waitForVisibility (By locator) {
        WebDriverWait wait = new WebDriverWait (driver, Duration.ofSeconds (DriverManager.getExplicitWaitDuration ()));
        wait.until (ExpectedConditions.visibilityOf (findElement (locator)));
    }

    /**
     * @return - void
     * @Method - waitForElementToClickable
     * @Description - wait for findBy element to clickable
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void waitForElementToClickable (WebElement element) {
        WebDriverWait wait = new WebDriverWait (driver, Duration.ofSeconds (DriverManager.getExplicitWaitDuration ()));
        wait.until (ExpectedConditions.elementToBeClickable (element));
    }

    /**
     * @return - void
     * @Method - waitForElementToClickable
     * @Description - wait for By locator to clickable
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void waitForElementToClickable (By locator) {
        WebDriverWait wait = new WebDriverWait (driver, Duration.ofSeconds (DriverManager.getExplicitWaitDuration ()));
        wait.until (ExpectedConditions.elementToBeClickable (locator));
    }

    /**
     * @return - void
     * @Method - waitForInVisibility
     * @Description - wait element visibility
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void waitForInVisibility (WebElement element) {
        WebDriverWait wait = new WebDriverWait (driver, Duration.ofSeconds (DriverManager.getExplicitWaitDuration ()));
        wait.until (ExpectedConditions.invisibilityOf (element));
    }

    /**
     * @return - void
     * @Method - clear
     * @Description - wait for element visibility and clear field
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void clear (WebElement element) {
        waitForVisibility (element);
        element.clear ();
    }

    /**
     * @return - void
     * @Method - click
     * @Description - wait for element visibility and click find by element
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void click (WebElement element) {
        LOG.info ("Clicking on the element : " + element);
        waitForElementToClickable (element);
        element.click ();
        LOG.info (element + " Clicked successfully.");
    }

    /**
     * @return - void
     * @Method - click
     * @Description - wait for element visibility and click find by element
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void clickWithoutWait (WebElement element) {
        LOG.info ("Clicking on the element : " + element);
        element.click ();
        LOG.info (element + " Clicked successfully.");
    }

    /**
     * @return - void
     * @Method - clickUsingJSExecutor
     * @Description - click Using JSExecutor
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void clickUsingJSExecutor (WebElement element) {
        JavascriptExecutor jsx = driver;
        jsx.executeScript ("arguments[0].click();", element);
    }

    /**
     * @Method - clickUsingAction
     * @Description - click using touch actions
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void clickUsingAction (WebElement element) {
        new TouchAction ((PerformsTouchActions) driver).
                press ((PointOption) element).waitAction (waitOptions (ofMillis (70))).release ().perform ();
    }

    /**
     * This method used to click on the element.
     *
     * @Method - click
     * @Description - wait for element visibility and click on element
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void click (WebElement element, String msg) {
        try {
            waitForVisibility (element);
            LOG.info (msg);
            element.click ();
        } catch (StaleElementReferenceException e) {
            waitForVisibility (element);
            LOG.info (msg);
            element.click ();
        }
    }

    /**
     * This method used to click on the by type element
     *
     * @param locator by type locator
     */
    public void click (By locator) {
        waitForVisibility (locator);
        waitForElementToClickable (locator);
        findElement (locator).click ();
        LOG.info ("Element with the locator " + locator + " got clicked.");
    }

    /**
     * This method is to get the inner text of the web element
     *
     * @return - string inner text of the element.
     * @Method - getText
     * @Description - wait for element visibility and get the text
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public String getText (WebElement element) {
        waitForVisibility (element);
        String label = element.getText ().trim ();
        LOG.info ("Label on the element : " + label);
        return label;
    }

    /**
     * This method is to get the inner text of the web element
     *
     * @return - string inner text of the element.
     * @Method - getText
     * @Description - wait for element visibility and get the text
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public String getTextWithoutWait (WebElement element) {
        String label = element.getText ().trim ();
        LOG.info ("Label on the element : " + label);
        return label;
    }

    /**
     * This method is to get inner text of the by type element.
     *
     * @param locator by type locator
     * @return inner text of the element.
     */
    public String getText (By locator) {
        waitForVisibility (locator);
        String label = findElement (locator).getText ().trim ();
        LOG.info ("Label on the element : " + label);
        return label;
    }

    /**
     * @return - boolean status of the visibility of the given element
     * @Method - isDisplayed
     * @Description - check element visibility
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public boolean isDisplayed (WebElement element) {
        boolean status = false;
        waitForVisibility (element);
        try {
            status = element.isDisplayed ();
        } catch (WebDriverException e) {
            LOG.info (e);
        }
        return status;
    }

    /**
     * @return - boolean status of the visibility of the given element
     * @Method - isDisplayed
     * @Description - check element visibility
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public boolean isDisplayedWithoutWait (WebElement element) {
        boolean status = false;
        try {
            status = element.isDisplayed ();
        } catch (WebDriverException e) {
            LOG.info (e);
        }
        return status;
    }

    /**
     * @return - boolean status of the visibility of the given element
     * @Method - isDisplayed
     * @Description - check element visibility
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public boolean isDisplayed (By locator) {
        boolean status = false;
        try {
            waitForVisibility (locator);
            status = findElement (locator).isDisplayed ();
        } catch (WebDriverException e) {
            LOG.info (e);
        }
        return status;
    }

    /**
     * @return - boolean
     * @Method - isSelected
     * @Description - check element selection
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public boolean isSelected (WebElement element) {
        boolean status = false;
        try {
            // waitForVisibility(element);
            status = element.isSelected ();
        } catch (WebDriverException e) {
            LOG.info (e);
        }
        return status;
    }

    /**
     * @return - void
     * @Method - sendKeys
     * @Description - wait for element and sendKeys as generic
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void sendKeys (WebElement element, String txt) {
        waitForVisibility (element);
        element.sendKeys (txt);
    }

    /**
     * @return - void
     * @Method - clearTextField
     * @Description - clear text from field
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void clearTextField (WebElement element) {
        waitForVisibility (element);
        element.clear ();
    }

    /**
     * @return - string
     * @Method - getAttribute
     * @Description - get text using Attribute name
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public String getAttribute (WebElement element, String attribute) {
        waitForVisibility (element);
        return element.getAttribute (attribute);
    }

    /**
     * @return - string
     * @Method - getAttribute
     * @Description - get text using Attribute name
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public String getAttributeWithoutWait (WebElement element, String attribute) {
        return element.getAttribute (attribute);
    }

    /**
     * @return - void
     * @Method - softAssert
     * @Description - soft Assert for generic assertion
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void softAssert (WebElement element, Boolean value, String msg) {
        SoftAssert sa = new SoftAssert ();
        sa.assertEquals (element.isDisplayed (), value.booleanValue (), msg);
    }

    /**
     * @return - void
     * @Method - keyboardEnterKey
     * @Description - press enter key on keyboard and hide for ios and android platform
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void keyboardEnterKey () {
        String platform = System.getProperty ("platformName");
        if (platform.equalsIgnoreCase ("Android")) {
            pause (500);
            driver.navigate ().back ();
        } else {
            WebElement element = driver.findElement (By.id ("Done"));
            element.click ();
        }
    }

    /**
     * @return - void
     * @Method - pressKeyboardEnterKey
     * @Description - press enter key on keyboard and hide for android platform
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void pressKeyboardEnterKey () {
        ((AndroidDriver) driver).pressKey (new KeyEvent (AndroidKey.ENTER));
        sleepTime (1);
        ((AndroidDriver) driver).hideKeyboard ();
    }

    /**
     * @return - void
     * @Method - clickTickIconOnKeyboard
     * @Description - click Tick Icon On Keyboard javascript executor
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void clickTickIconOnKeyboard () {
        JavascriptExecutor executor = driver;
        executor.executeScript ("mobile: performEditorAction", ImmutableMap.of ("action", "Go"));
    }

    /**
     * @return - status
     * @Method - scrollToElement
     * @Description - Scroll Page to element
     * @author - vaibhav G
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public Status scrollToElement (WebElement element) {
        try {
            if (!element.isDisplayed ()) {
                // Scroll the page until the element is visible.
                ((JavascriptExecutor) driver).executeScript (
                        "arguments[0].scrollIntoView(true);", element);
            }
        } catch (Exception e) {
            LOG.info (e + "Fail");
            return Status.FAIL;
        }

        return null;
    }

    /**
     * @return - void
     * @Method - sleep time
     * @Description - Thread sleep for static wait
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void sleepTime (int seconds) {
        try {
            Thread.sleep (seconds * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            LOG.info (e);
        }
    }

    /**
     * @return - void
     * @Method - jsClick
     * @Description - click on element by java script
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void jsClick (WebElement element) {
        JavascriptExecutor js = driver;
        js.executeScript ("arguments[0].click()", element);
    }

    /**
     * @return - void
     * @Method - jsScrollByElement
     * @Description - Scroll element by java script
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void jsScrollByElement (WebElement element) {
        JavascriptExecutor js = driver;
        HashMap<String, String> scrollObject = new HashMap<> ();
        scrollObject.put ("direction", "down");
        js.executeScript ("mobile: scroll", scrollObject);
    }

    /**
     * @return - void
     * @Method - implicitlyWait
     * @Description - implicitly Wait for visibility
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void implicitlyWait () {
        driver.manage ().timeouts ().implicitlyWait (Duration.ofSeconds (10));
    }

    /**
     * @return - void
     * @Method - click actions
     * @Description - click using actions class
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */

    public void clickAction (WebElement element) {
        Actions actions = new Actions (driver);
        actions.moveToElement (element).click ().perform ();
    }

    public void scroll (FingerGestureUtils.Direction direction, int distance) {
        FingerGestureUtils<AppiumDriver> fingerGestureUtils = new FingerGestureUtils<AppiumDriver> ((AppiumDriver) driver);
        fingerGestureUtils.swipe (direction, distance);
        sleepTime (1);
    }

    /**
     * @Method - scroll By Element text Ios
     * @Description - scroll till element text view
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void scrollByElementIos (String accessibility) {
        WebElement element = driver.findElement (AppiumBy.accessibilityId (accessibility));
        final HashMap<String, Object> scrollObject = new HashMap<> ();
        scrollObject.put ("element", ((RemoteWebElement) element).getId ());
        scrollObject.put ("toVisible", true);
        //scrollObject.put("direction","down");
        driver.executeScript ("mobile: scroll", scrollObject);
    }

    /**
     * @return - void
     * @Method - capitalizeString
     * @Description - Capitalize string first latter
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public static String capitalizeString (String str) {
        String retStr = str;
        try { // We can face index out of bound exception if the string is null
            retStr = str.substring (0, 1).toUpperCase () + str.substring (1);
        } catch (Exception e) {
            LOG.info (e.toString ());
        }
        return retStr;
    }

    /**
     * @return - void
     * @Method - tapUsingCoordinates
     * @Description - tap on element using coordinate
     * @author -
     * @DateCreated - 03-07-2023
     * @DateModified - 0-0-202
     */
    public void tapUsingCoordinates (int x, int y) {
        PointerInput finger = new PointerInput (PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence (finger, 1);
        tap.addAction (finger.createPointerMove (Duration.ofMillis (0), PointerInput.Origin.viewport (), x, y));
        tap.addAction (finger.createPointerDown (PointerInput.MouseButton.LEFT.asArg ()));
        tap.addAction (finger.createPointerUp (PointerInput.MouseButton.LEFT.asArg ()));
        driver.perform (Arrays.asList (tap));
    }

    /**
     * @return - string
     * @Method - tapRemoveSpecialCharacters
     * @Description - to clear special characters and currency from string
     * @author -
     * @DateCreated - 05-09-2023
     * @DateModified - 0-0-202
     */
    public static String removeSpecialCharacters (String input) {
        // Define a regular expression pattern to match special characters, letters, and numbers
        Pattern pattern = Pattern.compile ("[^a-zA-Z0-9\\s]+");
        // Create a Matcher object for the input string
        Matcher matcher = pattern.matcher (input);
        // Replace matched characters with an empty string
        String cleanedString = matcher.replaceAll ("");
        return cleanedString;
    }

    /**
     * @return - string
     * @Method - removeNonNumericCharacters
     * @Description - method is to remove non-numerical characteristics from the string
     * @author -
     * @DateCreated - 05-09-2023
     * @DateModified - 0-0-202
     */
    public static String removeNonNumericCharacters (String input) {
        // Use a regular expression to match and replace all non-numeric characters
        String result = input.replaceAll ("[^0-9]", "");
        return result;
    }

    /**
     * @return - string
     * @Method - addRMPrefixAndDot
     * @Description - to add suffix and prefix to the currency string coming from API
     * @author -
     * @DateCreated - 05-09-2023
     * @DateModified - 0-0-202
     */
    public static String addRMPrefixAndDot (String input) {
        // Check if the input string is null or too short to process
        if (input == null || input.length () < 2) {
            throw new IllegalArgumentException ("Input string must be at least 2 characters long.");
        }
        // Get the last two characters of the input string
        String lastTwoDigits = input.substring (input.length () - 2);
        // Get the remaining characters of the input string except the last two
        String remainingCharacters = input.substring (0, input.length () - 2);
        // Add "RM" as a prefix and a dot (.) before the last two digits
        return "RM" + remainingCharacters + "." + lastTwoDigits;
    }

    /**
     * @return - string
     * @Method - addRMPrefixAndDot
     * @Description - to add suffix and prefix to the currency string coming from API
     * @author -
     * @DateCreated - 05-09-2023
     * @DateModified - 0-0-202
     */
    public String convertDateFormat (String inputDate) {
        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat ("yyyy-MM-dd");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat ("dd MMMM yyyy");
            Date date = inputDateFormat.parse (inputDate);
            String outputDate = outputDateFormat.format (date);
            return outputDate;
        } catch (ParseException e) {
            e.printStackTrace ();
            // Handle the parsing error as needed
            return null;
        }
    }
}
