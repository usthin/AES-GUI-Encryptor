package com.aesapp;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class AESUtil {

    public static SecretKeySpec getKey(String userKey, int keySize) throws Exception {

        MessageDigest sha = MessageDigest.getInstance("SHA-256");

        byte[] key = sha.digest(userKey.getBytes("UTF-8"));

        key = Arrays.copyOf(key, keySize / 8);

        return new SecretKeySpec(key, "AES");
    }

    public static String encrypt(String plaintext, String key, String mode, int keySize) throws Exception {

        SecretKeySpec secretKey = getKey(key, keySize);

        Cipher cipher = Cipher.getInstance("AES/" + mode + "/PKCS5Padding");

        byte[] iv = new byte[16];

        if(!mode.equals("ECB")) {

            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

        } else {

            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        }

        byte[] encrypted = cipher.doFinal(plaintext.getBytes());

        if(!mode.equals("ECB")) {

            byte[] combined = new byte[iv.length + encrypted.length];

            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

            return Base64.getEncoder().encodeToString(combined);

        } else {

            return Base64.getEncoder().encodeToString(encrypted);
        }
    }

    public static String decrypt(String ciphertext, String key, String mode, int keySize) throws Exception {

        SecretKeySpec secretKey = getKey(key, keySize);

        Cipher cipher = Cipher.getInstance("AES/" + mode + "/PKCS5Padding");

        byte[] decoded = Base64.getDecoder().decode(ciphertext);

        byte[] iv = new byte[16];

        byte[] encrypted;

        if(!mode.equals("ECB")) {

            System.arraycopy(decoded, 0, iv, 0, 16);

            encrypted = new byte[decoded.length - 16];
            System.arraycopy(decoded, 16, encrypted, 0, encrypted.length);

            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

        } else {

            encrypted = decoded;
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
        }

        byte[] decrypted = cipher.doFinal(encrypted);

        return new String(decrypted);
    }
}