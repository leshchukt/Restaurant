package controller.command.util;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordSecurity {
    private static final Logger LOGGER = Logger.getLogger(PasswordSecurity.class);

    public static String getSecurePassword(String password){
        String secretKey = Configuration.getInstance().getSecretKey();
        return get_SHA_512_SecurePassword(password, secretKey.getBytes());
    }

    private static String get_SHA_512_SecurePassword(String password, byte[] salt){

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder generatedPassword = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                generatedPassword.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return generatedPassword.toString();
        }
        catch (NoSuchAlgorithmException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }

    }
}
