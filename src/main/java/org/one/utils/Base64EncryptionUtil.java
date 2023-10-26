package org.one.utils;

import java.util.Base64;

public class Base64EncryptionUtil {

    public static String getBase64EncryptedString(String inputStr) {
        String encodedString = Base64.getEncoder().encodeToString(inputStr.getBytes());
        return encodedString;
    }

    public static String getBase64DecryptedString(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
}
