package vn.edu.eaut.lab5.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Tien ich bam mat khau bang SHA-256.
 * Luu y: day la giai phap don gian cho muc dich hoc tap (bai lab).
 * Trong thuc te nen dung BCrypt/Argon2 va them "salt" rieng cho tung tai khoan.
 */
public class PasswordUtil {

    private PasswordUtil() {}

    public static String hash(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Khong the bam mat khau", e);
        }
    }

    public static boolean matches(String rawPassword, String hashedPassword) {
        if (rawPassword == null || hashedPassword == null) {
            return false;
        }
        return hash(rawPassword).equalsIgnoreCase(hashedPassword);
    }
}
