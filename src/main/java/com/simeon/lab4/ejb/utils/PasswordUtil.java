package com.simeon.lab4.ejb.utils;

import com.simeon.lab4.entities.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class PasswordUtil {
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Base64.Decoder decoder = Base64.getDecoder();

    private PasswordUtil() {}

    public static void setPassword(User user, String password) {
        byte[] salt = generateSalt();
        String passwordHash = calculateHash(password, salt);

        user.setSalt(encoder.encodeToString(salt));
        user.setPassword(passwordHash);
    }

    public static boolean validatePassword(User user, String password) {
        String passwordHash = calculateHash(password, decoder.decode(user.getSalt()));
        return passwordHash != null && passwordHash.equals(user.getPassword());
    }

    private static String calculateHash(String password, byte[] salt) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return encoder.encodeToString(hash);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return null;
        }
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

}
