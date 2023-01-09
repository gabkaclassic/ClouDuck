package org.gab.ClouDuck.crypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;

@Component
public class Cryptographer {

    private static Cipher cipher;
    private static SecretKey key;

    @Autowired
    public void setup(
            @Value("${encryption.algorithm.key}") String algorithmKey,
            @Value("${encryption.algorithm.cipher}") String algorithmCipher,
            @Value("${encryption.key}") String key
    ) throws Exception {

        Cryptographer.key = new SecretKeySpec(key.getBytes(), algorithmKey);
        Cryptographer.cipher = Cipher.getInstance(algorithmCipher);
    }

    private static byte[] processString(byte[] input, int mode) {

        try {
            cipher.init(mode, key);

            return cipher.doFinal(input);
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] encrypt(byte[] input) { return processString(input, Cipher.ENCRYPT_MODE); }
    public byte[] decrypt(byte[] input) { return processString(input, Cipher.DECRYPT_MODE); }
}
