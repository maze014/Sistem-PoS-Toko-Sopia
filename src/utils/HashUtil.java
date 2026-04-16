package utils;
import java.security.MessageDigest;
import java.util.Base64;

public class HashUtil {
    public static String hashSHA256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hash); // Ubah byte ke String
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}