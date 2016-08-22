package com.jackfangqi.commonutil.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Compiler: Android Studio
 * Project: CommonUtils
 * Author: Jack Fang
 * Email: jackfangqi1314@gmail.com
 * Date: 2016/8/19 8:47
 */
public final class DigestUtil {
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};

    private DigestUtil() {
        throw new AssertionError();
    }

    public static String md5(String s) {
        if (s == null)
            return null;

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            return new String(encodeHex(digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return out;
    }
}
