package com.jarvis.app.helpers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashHelper {
    public static String getSHA256(final String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte message_digest[] = digest.digest(value.getBytes(StandardCharsets.US_ASCII));
            StringBuilder hex_string = new StringBuilder();
            for(byte message_digest_byte : message_digest) {
                String h = Integer.toHexString(0xFF & message_digest_byte);
                while (h.length() < 2)
                    h = "0" + h;
                hex_string.append(h);
            }
            return hex_string.toString();
        }catch(NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
