package com.mms.mms_api.util;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomTokenHelper {
    private static final SecureRandom secureRandom = new SecureRandom();

    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    private RandomTokenHelper() {
    }

    public static String generateToken(int byteLength) {

        byte[] randomBytes = new byte[byteLength];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public static String generateToken() {
        return generateToken(32);
    }
}
