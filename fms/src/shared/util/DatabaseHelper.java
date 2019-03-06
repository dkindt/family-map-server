package shared.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class DatabaseHelper {

    public static String hashPassword(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest salt = MessageDigest.getInstance("SHA-256");
        salt.update(input.getBytes("UTF-8"));
        salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
        return bytesToHex(salt.digest());

    }

    public static String generateUUID(String source) throws UnsupportedEncodingException {
        byte[] bytes = source.getBytes("UTF-8");
        UUID uuid = UUID.nameUUIDFromBytes(bytes);
        return uuid.toString();
    }

    private static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
