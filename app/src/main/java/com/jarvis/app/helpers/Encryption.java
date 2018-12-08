package com.jarvis.app.helpers;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

public class Encryption {
    private static final String ENCRYPTION_ALGORITHM = "DESede/ECB/PKCS5Padding";

    public static String generateDlkCode() {
        return "MDKL_" + getTimeStamp() + "_" + getRandomNumber(10000000, 80000000);
    }

    public static int getRandomNumber(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }

    public static String getTimeStamp(){
        return  (System.currentTimeMillis() / 1000) + "";
    }

    /**
     * Triple des encryption
     * @param secretKey Dealoka secret key
     * @param message JSON string to be encrypting
     * @return An ecrypted JSON string
     * @throws Exception
     */
    public static String encrypt( String secretKey, String message) throws Exception {
        byte[] digestOfPassword = secretKey.getBytes();
        SecretKey key = new SecretKeySpec(digestOfPassword, "DESede");
        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] plainTextBytes = message.getBytes();
        byte[] buf = cipher.doFinal(plainTextBytes);
        byte[] base64Bytes = Base64.encode(buf, Base64.DEFAULT);

        String base64EncryptedString = new String(base64Bytes);
        return base64EncryptedString;
    }

    private static String base64(String authData) throws UnsupportedEncodingException {
        byte[] data = authData.getBytes("UTF-8");
        return Base64.encodeToString(data, Base64.DEFAULT);
    }
}
