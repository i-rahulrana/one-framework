package org.one.utils;

import sew.ai.helpers.exceptions.MalformedURLException;

public class JavaURL {
    private JavaURL() {}

    /**
     *
     * @param url String the url as a string to convert.
     * @return java.net.URL a URL object that can be used by methods that require that argument.
     * @throws MalformedURLException if the string passed is not a valid URL
     */
    public static java.net.URL create(String url) {
        try {
            return new java.net.URL(url);
        } catch (java.net.MalformedURLException e) {
            throw new MalformedURLException(e);
        }
    }
}
