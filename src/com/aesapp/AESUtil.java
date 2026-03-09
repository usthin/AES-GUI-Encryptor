package com.aesapp;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class AESUtil {

    private static final byte[] iv = new byte[16];

    public static SecretKeySpec getKey(String userKey, int keySize) throws Exception {

        MessageDigest sha = MessageDigest.getInstance("SHA-256");

        byte[] key = sha.digest(userKey.getBytes("UTF-8"));

        key = Arrays.copyOf(key, keySize / 8);

        return new SecretKeySpec(key, "AES");
    }

    public static String encrypt(String plaintext, String key, String mode, int keySize) throws Exception {

        SecretKeySpec secretKey = getKey(key, keySize);

        Cipher cipher = Cipher.getInstance("AES/" + mode + "/PKCS5Padding");

        if(mode.equals("ECB"))
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        else
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

        byte[] encrypted = cipher.doFinal(plaintext.getBytes());

        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String ciphertext, String key, String mode, int keySize) throws Exception {

        SecretKeySpec secretKey = getKey(key, keySize);

        Cipher cipher = Cipher.getInstance("AES/" + mode + "/PKCS5Padding");

        if(mode.equals("ECB"))
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        else
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

        byte[] decoded = Base64.getDecoder().decode(ciphertext);

        return new String(cipher.doFinal(decoded));
    }
}