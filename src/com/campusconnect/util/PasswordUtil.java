package com.campusconnect.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Salted SHA-256 password hashing.
 *
 * This is fine for a student project / MVP demo. If this were going into
 * production, swap it for BCrypt or Argon2 (both add a proper external
 * library dependency, which is why this build keeps things dependency-free
 * with java.security instead).
 */
public class PasswordUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hash(String plainPassword, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(Base64.getDecoder().decode(salt));
            byte[] hashed = digest.digest(plainPassword.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hashed);
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("Password hashing failed", e);
        }
    }

    public static boolean matches(String plainPassword, String salt, String expectedHash) {
        String actualHash = hash(plainPassword, salt);
        return actualHash.equals(expectedHash);
    }
}
