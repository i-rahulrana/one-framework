package org.one.utils;

import io.appium.java_client.AppiumDriver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Arrays;

import static java.text.MessageFormat.format;
import static java.time.Duration.ZERO;
import static java.time.Duration.ofMillis;
import static java.util.Collections.singletonList;
import static org.openqa.selenium.interactions.PointerInput.Kind.TOUCH;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;

@AllArgsConstructor
public class FingerGestureUtils<D extends AppiumDriver> {
    @Getter
    @AllArgsConstructor
    public enum Direction {
        // Represents the direction to move left in the x-axis. It has x = -1 and y = 0.
        LEFT(-1, 0),
        // Represents the direction to move right in the x-axis. It has x = 1 and y = 0.
        RIGHT(1, 0),
        // Represents the direction to move up in the y-axis. It has x = 0 and y = -1.
        UP(0, -1),
        // Represents the direction to move down in the y-axis. It has x = 0 and y = 1.
        DOWN(0, 1);

        private final int x;
        private final int y;
    }

    private static final Logger LOGGER = LogManager.getLogger(FingerGestureUtils.class);
    private static final String FINGER_1 = "Finger 1";
    private static final String FINGER_2 = "Finger 2";
    private static final int EXPLICIT_WAIT = 500;

    private final D driver;

    /**
     * Drag and drop element from given source to target
     *
     * @param source
     * @param target
     */
    public void dragTo(final WebElement source, final WebElement target) {
        final var start = getElementCenter(source);
        final var end = getElementCenter(target);
        LOGGER.info("Drag Drop...");
        printPoint("Start", start);
        printPoint("End", end);
        // 0 represents An integer representing the initial length of the sequence.
        final var sequence = singleFingerSwipe(FINGER_1, 0, start, end);
        this.driver.perform(singletonList(sequence));
    }

    public void swipe(final Direction direction, final int distance) {
        try {
            Thread.sleep (500);
        } catch (InterruptedException e) {
            throw new RuntimeException (e);
        }
        swipe(direction, null, distance);
    }

    public void swipe(final Direction direction, final WebElement element, final int distance) {
        final var start = getSwipeStartPosition(element);
        final var end = getSwipeEndPosition(direction, element, distance);
        LOGGER.info("Swipe...");
        if (element != null) {
            printDimension("Element Size", element.getSize());
            printPoint("Element location", element.getLocation());
        }
        printPoint("Start", start);
        printPoint("End", end);
        // 0 represents An integer representing the initial length of the sequence.
        final var sequence = singleFingerSwipe(FINGER_1, 0, start, end);
        this.driver.perform(singletonList(sequence));
    }

    /**
     * Tap the given element
     *
     * @param element
     */
    public void tap(final WebElement element) {
        final var start = getElementCenter(element);
        // 0 represents An integer representing the initial length of the sequence.
        final var sequence = singleFingerSwipe(FINGER_1, 0, start, null);
        this.driver.perform(singletonList(sequence));
    }

    /**
     * Tap the screen as per given coordinates.
     *
     * @param x
     * @param y
     */
    public void tapUsingCoordinates(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        // 1 represents An integer representing the initial length of the sequence.
        Sequence tap = new Sequence(finger, 1);
        // O represents the duration of milli seconds
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tap));
    }

    /**
     * This method is to zoom in to the given element by the given distance.
     *
     * @param element
     * @param distance
     */
    public void zoomIn(final WebElement element, final int distance) {
        LOGGER.info("Zoom In...");
        printDimension("Screen Size", getScreenSize());
        printDimension("Element Size", element.getSize());
        printPoint("Element location", element.getLocation());
        final var start = getSwipeStartPosition(element);
        // Two additional points, start1 and start2, are created by adjusting the start position
        // to create a pinch motion. The start1 is set to the left of the start position by
        // 50 pixels, and start2 is set to the right of the start position by 50 pixels.
        final var start1 = new Point(start.getX() - 50, start.getY());
        final var start2 = new Point(start.getX() + 50, start.getY());
        // The end1 and end2 positions are calculated by calling the getSwipeEndPosition method twice,
        // once for Direction.LEFT and once for Direction.RIGHT, with the provided distance value.
        // These represent the end positions for the pinch zoom-out gesture.
        final var end1 = getSwipeEndPosition(Direction.LEFT, element, distance);
        final var end2 = getSwipeEndPosition(Direction.RIGHT, element, distance);
        // Todo These print statements is only for debugging purposes will remove later.
        printPoint("Start 1", start1);
        printPoint("Start 2", start2);
        printPoint("End 1", end1);
        printPoint("End 2", end2);
        // The method then creates two sequences of actions representing the two fingers' movements.
        // Each sequence is created using the singleFingerSwipe method, which is not shown in the
        // provided code snippet. The singleFingerSwipe method is used to create a sequence of
        // touch actions (swipe) for a single finger.
        final var sequence1 = singleFingerSwipe(FINGER_1, 0, start1, end1);
        final var sequence2 = singleFingerSwipe(FINGER_2, 0, start2, end2);
        this.driver.perform(Arrays.asList(sequence1, sequence2));
    }

    /**
     * This method is to zoom out from the given element by the given distance.
     *
     * @param element
     * @param distance
     */
    public void zoomOut(final WebElement element, final int distance) {
        LOGGER.info("Zoom Out...");
        printDimension("Screen Size", getScreenSize());
        printDimension("Element Size", element.getSize());
        printPoint("Element location", element.getLocation());
        final var start = getSwipeStartPosition(element);
        // Two additional points, start1 and start2, are created by adjusting the start position
        // to create a pinch motion. The start1 is set to the left of the start position by
        // 50 pixels, and start2 is set to the right of the start position by 50 pixels.
        final var start1 = new Point(start.getX() - 50, start.getY());
        final var start2 = new Point(start.getX() + 50, start.getY());
        // The end1 and end2 positions are calculated by calling the getSwipeEndPosition method twice,
        // once for Direction.LEFT and once for Direction.RIGHT, with the provided distance value.
        // These represent the end positions for the pinch zoom-out gesture.
        final var end1 = getSwipeEndPosition(Direction.LEFT, element, distance);
        final var end2 = getSwipeEndPosition(Direction.RIGHT, element, distance);
        // Todo These print statements is only for debugging purposes will remove later.
        printPoint("Start 1", start1);
        printPoint("Start 2", start2);
        printPoint("End 1", end1);
        printPoint("End 2", end2);
        // The method then creates two sequences of actions representing the two fingers' movements.
        // Each sequence is created using the singleFingerSwipe method, which is not shown in the
        // provided code snippet. The singleFingerSwipe method is used to create a sequence of
        // touch actions (swipe) for a single finger.
        final var sequence1 = singleFingerSwipe(FINGER_1, 0, end1, start1);
        final var sequence2 = singleFingerSwipe(FINGER_2, 0, end2, start2);
        this.driver.perform(Arrays.asList(sequence1, sequence2));
    }

    /**
     * The method calculates and returns corrected coordinates to ensure that the point lies within
     * the screen bounds or the bounds of the provided element. If the initial point lies outside
     * the screen or element boundaries, it is adjusted to be within the allowed range.
     *
     * @param element A WebElement representing the target element for which corrected coordinates are needed.
     * @param point A Point object representing the initial coordinates.
     * @return the corrected coordinates as a new Point object.
     */
    private Point getCorrectedCoordinates(final WebElement element, final Point point) {
        final var screenSize = getScreenSize();
        var x = point.getX();
        var y = point.getY();
        var w = screenSize.getWidth();
        var h = screenSize.getHeight();
        if (element != null) {
            final var size = element.getSize();
            final var location = element.getLocation();
            w = size.getWidth() + location.getX();
            h = size.getHeight() + location.getY();
        }
        if (x >= w) {
            // To keep the point inside the right boundary(subtracting 5 to avoid being too close to the edge)
            x = w - 5;
        }
        if (y >= h) {
            //  To keep the point inside the bottom boundary(subtracting 5 to avoid being too close to the edge).
            y = h - 5;
        }
        if (x < 0) {
            // Set x to 5 to keep the point inside the left boundary(adding 5 to avoid being too close to the edge)
            x = 5;
        }
        if (y < 0) {
            // Set y to 5 to keep the point inside the top boundary(adding 5 to avoid being too close to the edge).
            y = 5;
        }
        return new Point(x, y);
    }

    /**
     * Get the center of the given element.
     *
     * @param element
     * @return
     */
    private Point getElementCenter(final WebElement element) {
        final var location = element.getLocation();
        final var size = element.getSize();
        // Calculate the x-coordinate of the center by adding half of the element's width
        final var x = (size.getWidth() / 2) + location.getX();
        // Calculate the y-coordinate of the center by adding half of the element's height
        final var y = (size.getHeight() / 2) + location.getY();
        return getCorrectedCoordinates(element, new Point(x, y));
    }

    /**
     * This method returns the screen size.
     *
     * @return
     */
    private Dimension getScreenSize() {
        return this.driver.manage()
                .window()
                .getSize();
    }

    /**
     * This method returns the swipe end position.
     *
     * @param direction
     * @param element
     * @param distance
     * @return
     */
    private Point getSwipeEndPosition(final Direction direction, final WebElement element, final int distance) {
        verifyDistance(distance);
        final var start = getSwipeStartPosition(element);
        // The result is calculated as the x-coordinate of the start position, modified by the direction.getX() value
        final var x = start.getX() + ((start.getX() * direction.getX() * distance) / 100);
        // The result is calculated as the y-coordinate of the start position, modified by the direction.getY() value
        final var y = start.getY() + ((start.getY() * direction.getY() * distance) / 100);
        return getCorrectedCoordinates(element, new Point(x, y));
    }

    /**
     * Get the swipe start position.
     *
     * @param element
     * @return
     */
    private Point getSwipeStartPosition(final WebElement element) {
        final var screenSize = getScreenSize();
        // To calculate the half of the screen on the x-axis
        var x = screenSize.getWidth() / 2;
        // To calculate the half of the screen on the y-axis
        var y = screenSize.getHeight() / 2;
        if (element != null) {
            final var point = getElementCenter(element);
            x = point.getX();
            y = point.getY();
        }
        return new Point(x, y);
    }

    /**
     * This method print the dimension of the screen.
     *
     * @param type
     * @param dimension
     */
    private void printDimension(final String type, final Dimension dimension) {
        LOGGER.info(format("{0}: [w: {1}, h: {2}]", type, dimension.getWidth(), dimension.getHeight()));
    }

    /**
     * This method is to print the point.
     *
     * @param type
     * @param point
     */
    private void printPoint(final String type, final Point point) {
        LOGGER.info(format("{0}: [x: {1}, y: {2}]", type, point.getX(), point.getY()));
    }

    /**
     * This method simulates single finger swipe from start point to end point.
     *
     * @param fingerName
     * @param index
     * @param start
     * @param end
     * @return
     */
    private Sequence singleFingerSwipe(final String fingerName, final int index, final Point start, final Point end) {
        final var finger = new PointerInput(TOUCH, fingerName);
        final var sequence = new Sequence(finger, index);
        sequence.addAction(finger.createPointerMove(ZERO, viewport(), start.getX(), start.getY()));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        if (end != null) {
            sequence.addAction(new Pause(finger, ofMillis(EXPLICIT_WAIT)));
            sequence.addAction(finger.createPointerMove(ofMillis(EXPLICIT_WAIT), viewport(), end.getX(), end.getY()));
        }
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        return sequence;
    }

    /**
     * This method is to verify the distance.
     *
     * @param distance
     */
    private void verifyDistance(final int distance) {
        // The method takes a single parameter: distance, an integer representing the distance or percentage value.
        if (distance <= 0 || distance >= 100) {
            throw new IllegalArgumentException("Distance should be between 0 and 100 exclusive...");
        }
    }
}
