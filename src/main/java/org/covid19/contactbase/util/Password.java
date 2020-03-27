package org.covid19.contactbase.util;

import org.mindrot.jbcrypt.BCrypt;

public class Password {

    public static String encrypt(String password) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(password, salt);
    }

    public static boolean verify(String password, String encryptedPassword) {
        return BCrypt.checkpw(password, encryptedPassword);
    }
}