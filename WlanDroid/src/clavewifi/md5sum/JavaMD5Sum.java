package clavewifi.md5sum;

/**
 * Copyright (c) 2008 Mark S. Kolich
 * http://mark.koli.ch
 * Modified by Juan Nicolás Patarino
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class JavaMD5Sum {

    public static final String MD5_ALGORITHM = "MD5";

    /**
     * Uses Java to compute the MD5 sum of a given input String.
     * @param input
     * @return
     */
    public static String computeSum(String input) throws NoSuchAlgorithmException {
        if (input == null) {
            throw new IllegalArgumentException("No puedes pasarme una cadena igual a null!!!");
        }
        StringBuilder sbuf = new StringBuilder();
        MessageDigest md = MessageDigest.getInstance(MD5_ALGORITHM);
        byte[] raw = md.digest(input.getBytes());
        for (int i = 0; i < raw.length; i++) {
            int c = (int) raw[i];
            if (c < 0) {
                c = (Math.abs(c) - 1) ^ 255;
            }
            String block = toHex(c >>> 4) + toHex(c & 15);
            sbuf.append(block);
        }
        return sbuf.toString();
    }

    private static String toHex(int s) {
        if (s < 10) {
            return new StringBuffer().append((char) ('0' + s)).toString();
        } else {
            return new StringBuffer().append((char) ('A' + (s - 10))).toString();
        }
    }
}
