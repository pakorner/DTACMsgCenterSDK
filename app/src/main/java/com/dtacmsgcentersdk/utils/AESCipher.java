package com.dtacmsgcentersdk.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCipher {

//    private static final String algorithm = "AES/CBC/PKCS7Padding";
    private static final String algorithm = "AES/CBC/NoPadding";

//    private static final byte[] keyValue = new byte[] { 'd', 't', 'a', 'c', 'a', 'p', 'p', 'l', 'i', 'c', 'a', 't', 'i', 'o', 'n', 'd' };

    private static final byte[] keyValue = "dtacapplicationd".getBytes();
//    private static final byte[] ivValue = new byte[] { 'f', 'e', 'd', 'c', 'b', 'a', '9', '8', '7', '6', '5', '4', '3', '2', '1', '0' };

    private static final byte[] ivValue = "62v01fVsCWHfRcWQ".getBytes();

    private static final IvParameterSpec ivspec = new IvParameterSpec(ivValue);
    private static final SecretKeySpec keyspec = new SecretKeySpec(keyValue, "AES");

    public static String encrypt(String data) throws Exception {
        data = padString(data);

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);

        byte [] encVal = cipher.doFinal(data.getBytes());

        return Base64.encodeToString(encVal, Base64.DEFAULT).trim();
    }

    public static String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

        byte[] decordedValue = Base64.decode(encryptedData, Base64.DEFAULT);
        byte[] decValue = cipher.doFinal(decordedValue);

        return new String(decValue).trim();
    }

    private static String padString(String source) {
        char paddingChar = ' ';
        int size = 16;
        int x = source.length() % size;
        int padLength = size - x;

        for (int i = 0; i < padLength; i++) {
            source += paddingChar;
        }
        return source;
    }

}