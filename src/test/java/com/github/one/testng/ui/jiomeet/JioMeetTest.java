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

package com.github.one.testng.ui.jiomeet;

import static com.github.one.manager.ParallelSession.createSession;
import static com.github.one.testng.ui.jiomeet.pages.GuestJoinPage.guestJoinPage;
import static com.github.one.testng.ui.jiomeet.pages.HomePage.homePage;
import static com.github.one.testng.ui.jiomeet.pages.SignInPage.signInPage;
import static com.github.one.testng.ui.jiomeet.pages.StartMeetingPage.startMeetingPage;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;

import com.github.one.actions.drivers.DriverActions;
import com.github.one.actions.drivers.NavigateActions;
import com.github.one.actions.drivers.WindowActions;
import com.github.one.actions.elements.ClickableActions;
import com.github.one.actions.elements.ElementActions;
import com.github.one.actions.elements.TextBoxActions;
import com.github.one.enums.PlatformType;
import com.github.one.manager.ParallelSession;
import com.github.one.testng.ui.jiomeet.pages.MeetingPage;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class JioMeetTest {
    private static final String GUEST_PERSONA = "User 2";
    private static final String HOST_NAME     = "hostName";
    private static final String HOST_PERSONA  = "User 1";
    private static final String MEETING_URL   = "meetingUrl";

    @AfterMethod (alwaysRun = true)
    public void afterMethod (final ITestResult result) {
        if (!result.isSuccess ()) {
            WindowActions.onWindow ().takeScreenshot ();
        }
    }

    @BeforeClass (alwaysRun = true)
    @Parameters ({ "platformType", "driverKey" })
    public void setupTestClass (final PlatformType platformType, final String chromeConfig) {
        ParallelSession.createSession (HOST_PERSONA, platformType, chromeConfig);
        ParallelSession.createSession (GUEST_PERSONA, platformType, chromeConfig);
    }

    @AfterClass (alwaysRun = true)
    public void tearDownClass () {
        ParallelSession.clearAllSessions ();
    }

    @Test (dependsOnMethods = "testHostParticipantName")
    public void testEndMeeting () {
        ParallelSession.switchPersona (GUEST_PERSONA);
        ClickableActions.withMouse (MeetingPage.meetingPage ().getEndCallButton ()).click ();

        ParallelSession.switchPersona (HOST_PERSONA);
        ClickableActions.withMouse (MeetingPage.meetingPage ().getEndCallButton ()).click ();
        ClickableActions.withMouse (MeetingPage.meetingPage ().getEndForAll ()).click ();

        if (ElementActions.onElement (MeetingPage.meetingPage ().getSkipButton ()).isDisplayed ()) {
            ClickableActions.withMouse (MeetingPage.meetingPage ().getSkipButton ()).click ();
        }
    }

    @Test (dependsOnMethods = "testGuestStartMeeting")
    public void testGuestParticipantName () {
        ClickableActions.withMouse (MeetingPage.meetingPage ().getParticipants ()).click ();
        ElementActions.onElement (MeetingPage.meetingPage ().getCurrentParticipantName (GUEST_PERSONA)).verifyText ()
            .endsWith ("(You)");
    }

    @Test (dependsOnMethods = "testStartHostMeeting")
    public void testGuestStartMeeting () {
        final var meetingUrl = ParallelSession.getSession ().getSharedData (MEETING_URL)
            .toString ();

        ParallelSession.switchPersona (GUEST_PERSONA);
        NavigateActions.navigate ().to (meetingUrl);

        TextBoxActions.onTextBox (guestJoinPage ().getGuestName ()).enterText (GUEST_PERSONA);
        ClickableActions.withMouse (guestJoinPage ().getJoinButton ()).click ();

        DriverActions.withDriver ().waitUntil (invisibilityOfElementLocated (homePage ().getLoader ()
            .getLocator ()));
    }

    @Test (dependsOnMethods = "testGuestParticipantName")
    public void testHostParticipantName () {
        ParallelSession.switchPersona (HOST_PERSONA);
        ClickableActions.withMouse (MeetingPage.meetingPage ().getParticipants ()).click ();
        ElementActions.onElement (MeetingPage.meetingPage ().getCurrentParticipantName (ParallelSession.getSession ().getSharedData (HOST_NAME))).verifyText ()
            .endsWith ("(You)");
    }

    @Test
    public void testHostSignIn () {
        ParallelSession.switchPersona (HOST_PERSONA);
        NavigateActions.navigate ().to ("https://jiomeetpro.jio.com/home");

        ClickableActions.withMouse (homePage ().getSignIn ()).click ();
        TextBoxActions.onTextBox (signInPage ().getEmail ()).enterText ("test-user1@mailinator.com");
        ClickableActions.withMouse (signInPage ().getProceedButton ()).click ();
        TextBoxActions.onTextBox (signInPage ().getPassword ()).enterText ("Admin@1234");
        ClickableActions.withMouse (signInPage ().getSignInButton ()).click ();
    }

    @Test (dependsOnMethods = "testHostSignIn")
    public void testStartHostMeeting () {
        ClickableActions.withMouse (homePage ().getStartMeeting ()).click ();
        ClickableActions.withMouse (startMeetingPage ().getStart ()).click ();

        DriverActions.withDriver ().waitUntil (invisibilityOfElementLocated (homePage ().getLoader ()
            .getLocator ()));

        ParallelSession.getSession ().setSharedData (HOST_NAME, "Test User");
        ParallelSession.getSession ().setSharedData (MEETING_URL, ElementActions.onElement (MeetingPage.meetingPage ().getMeetingLink ()).getText ());
    }
}
