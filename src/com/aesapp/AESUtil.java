package com.aesapp;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class AESUtil {

    public static SecretKeySpec getKey(String myKey, int keySize) throws Exception {

        byte[] key = myKey.getBytes("UTF-8");

        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        key = sha.digest(key);

        key = Arrays.copyOf(key, keySize / 8);

        return new SecretKeySpec(key, "AES");
    }

    public static String encrypt(String data, String key, String mode, int keySize) throws Exception {

        SecretKeySpec secretKey = getKey(key, keySize);

        Cipher cipher = Cipher.getInstance("AES/" + mode + "/PKCS5Padding");

        byte[] iv = new byte[16];
        IvParameterSpec ivspec = new IvParameterSpec(iv);

        if (!mode.equals("ECB")) {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        }

        byte[] encrypted = cipher.doFinal(data.getBytes());

        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String data, String key, String mode, int keySize) throws Exception {

        SecretKeySpec secretKey = getKey(key, keySize);

        Cipher cipher = Cipher.getInstance("AES/" + mode + "/PKCS5Padding");

        byte[] iv = new byte[16];
        IvParameterSpec ivspec = new IvParameterSpec(iv);

        if (!mode.equals("ECB")) {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        }

        byte[] decoded = Base64.getDecoder().decode(data);

        return new String(cipher.doFinal(decoded));
    }
}