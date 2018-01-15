package controller.command.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordSecurity {
    public static String getSecurePassword(String password){
        String secretKey = Configuration.getInstance().getSecretKey();
        return get_SHA_512_SecurePassword(password, secretKey.getBytes());
    }

    private static String get_SHA_512_SecurePassword(String password, byte[] salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
