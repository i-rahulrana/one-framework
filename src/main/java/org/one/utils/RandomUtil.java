package org.one.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

/**
 * This class is for generating random strings, integers and mobile numbers.
 *
 * @author aman.singh
 * @since
 */
public class RandomUtil {

    public static enum Mode {
        ALPHA, ALPHANUMERIC, NUMERIC
    }

    /**
     * This method is used to generate random string of the given length and of
     * given type.
     *
     * @param length
     * @param mode
     * @return
     * @throws Exception
     */
    public static String generateRandomString(int length, Mode mode) {
        StringBuffer buffer = new StringBuffer();
        String characters = "";
        switch (mode) {
            case ALPHA:
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
                break;
            case ALPHANUMERIC:
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                break;
            case NUMERIC:
                characters = "1234567890";
                break;
        }
        int charactersLength = characters.length();
        for (int i = 0; i < length; i++) {
            double index = Math.random() * charactersLength;
            buffer.append(characters.charAt((int) index));
        }
        return buffer.toString();
    }

    /**
     * This method is used to generate the random mobile number
     *
     * @return
     */
    public static String generateMobileNumber() {
        String input = Integer.toString(1234567890);
        String mobilenumber = "(" + input.substring(0, 3) + ") " + input.substring(3, 6) + "-" + input.substring(6, 10);
        return mobilenumber;
    }

    /**
     * This method is used to generate random integers.
     *
     * @return
     */
    public static int generateInteger() {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(95);
        return randomInt;
    }

    /**
     * This method provides a random email of the given length.
     *
     * @param n
     * @return
     */
    public static String getRandomMail(int n) {
        String str = RandomStringUtils.randomAlphabetic(n);
        return str + "@gmail.com";
    }

    /**
     * This method provides random word letter of given length.
     *
     * @param n
     * @return
     */
    public static String getRandomWordLetter(int n) {
        String word = RandomStringUtils.randomAlphabetic(n);
        return word;
    }

    /**
     * This method provide random number of given length.
     *
     * @param n
     * @return
     */
    public static String getRandomNumber(int n) {
        String number = RandomStringUtils.randomNumeric(n);
        return number;
    }
}