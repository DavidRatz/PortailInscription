package com.portailinscription.util;

import org.apache.commons.io.Charsets;

import java.util.Base64;

import javax.crypto.Cipher;  
import javax.crypto.spec.SecretKeySpec;


//https://javacube.fr/chiffrer-dechiffrer-simplement-avec-aes-en-java/
public class CipherUtilSecret {

    public static final String CIPHER_ALGORITHM = "AES";
    public static final String KEY_ALGORITHM = "AES";
    public static final byte[] SECRET_KEY = "DMOMVPRT5SJ7RANE" .getBytes(Charsets.UTF_8);

    public String decrypt(String encryptedInput) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(SECRET_KEY, KEY_ALGORITHM));
            return new String(cipher.doFinal(Base64.getUrlDecoder().decode(encryptedInput)),Charsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String encrypt(String str) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(SECRET_KEY, KEY_ALGORITHM));
            return Base64.getUrlEncoder().encodeToString(cipher.doFinal(str.getBytes(Charsets.UTF_8)));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
